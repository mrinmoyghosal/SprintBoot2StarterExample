package org.codingtest.ratecrawler.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.codingtest.ratecrawler.exception.ResourceNotFoundException;
import org.codingtest.ratecrawler.model.Bitcoin2USDRate;
import org.codingtest.ratecrawler.model.Bitcoin2USDRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Bitcoin to USD Rate Converter", description = "Get latest bitcoin to usd rate.")
public class Bitcoin2USDController {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	@Autowired
	private Bitcoin2USDRateRepository rateRepository;

	@ApiOperation(value = "View historical list of bitcoin to usd rates", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@GetMapping("/rates/historical")
	public ResponseEntity<List<Bitcoin2USDRate>> getHistoricalRate(@RequestParam String startDate,
			@RequestParam String endDate) {
		LocalDateTime startDateObj, endDateObj;
		try {
			startDateObj = LocalDateTime.ofInstant(format.parse(startDate).toInstant(), ZoneId.systemDefault());
			endDateObj = LocalDateTime.ofInstant(format.parse(endDate).toInstant(), ZoneId.systemDefault());
		} catch (ParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok().body(rateRepository.findByDates(startDateObj, endDateObj));
	}

	@ApiOperation(value = "Get current rate")
	@GetMapping("/rates/now")
	public ResponseEntity<Bitcoin2USDRate> getBitcoin2USDRateById() throws ResourceNotFoundException {
		Page<Bitcoin2USDRate> page = rateRepository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id")));

		if (!page.getContent().isEmpty())
			return ResponseEntity.ok().body(page.getContent().get(0));
		else
			throw new ResourceNotFoundException("No available rates in db");
	}

}
