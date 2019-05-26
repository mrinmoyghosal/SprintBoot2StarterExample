package org.codingtest.ratecrawler.api;

import java.util.List;

import org.codingtest.ratecrawler.exception.ResourceNotFoundException;
import org.codingtest.ratecrawler.model.CrawlerConfig;
import org.codingtest.ratecrawler.model.CrawlerConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Configure Crawler Configuration", description = "Set Refresh rate for crawlers")
public class CrawlerController {

	@Autowired
	private CrawlerConfigRepository crawlerConfigRepository;

	@ApiOperation(value = "View config for crawlers", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })

	@GetMapping("/crawler/configs")
	public List<CrawlerConfig> getCrawlerConfigs() {
		return crawlerConfigRepository.findAll();
	}

	@ApiOperation(value = "Set crawler config")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	@PostMapping("/crawler/configs/{id}/{rate}")
	public ResponseEntity<CrawlerConfig> setCrawlerConfigById(
			@ApiParam(value = "id from which CrawlerConfig object will retrieve", required = true) @PathVariable(value = "id") Long configId,
			@PathVariable(value = "rate") Integer rate) throws ResourceNotFoundException {
		CrawlerConfig config = crawlerConfigRepository.findById(configId)
				.orElseThrow(() -> new ResourceNotFoundException("CrawlerConfig not found for this id :: " + configId));
		config.setRefreshRate(rate);
		crawlerConfigRepository.save(config);
		return ResponseEntity.ok().body(config);
	}

}
