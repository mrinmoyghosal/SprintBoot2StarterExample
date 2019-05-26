package org.codingtest.ratecrawler.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePayload {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	private LocalDateTime time;
	private String rate;

	public ResponsePayload() {
	}

	@JsonProperty("time")
	private void unpackNameFromNestedObject(Map<String, String> map) throws ParseException {

		this.time = LocalDateTime.ofInstant(format.parse(map.get("updatedISO")).toInstant(), ZoneId.systemDefault());
	}

	@SuppressWarnings("unchecked")
	@JsonProperty("bpi")
	private void unpackBpiFromNestedObject(Map<String, Object> map) {
		this.rate = ((Map<String, String>) map.get("USD")).get("rate");
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

}
