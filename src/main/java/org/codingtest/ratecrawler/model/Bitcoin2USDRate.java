package org.codingtest.ratecrawler.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "bitcoin_to_usd_rates", uniqueConstraints = { @UniqueConstraint(columnNames = { "rate", "date" }) })
@ApiModel(description = "USD Rate for 1 Bitcoin ")
public class Bitcoin2USDRate {

	@ApiModelProperty(notes = "The database generated employee ID")
	private long id;

	@ApiModelProperty(notes = "Rate in USD")
	private String rate;

	@ApiModelProperty(notes = "Date of crawling")
	private LocalDateTime date;

	public Bitcoin2USDRate() {

	}

	public Bitcoin2USDRate(String rate, LocalDateTime date) {
		this.rate = rate;
		this.date = date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "rate", nullable = false, unique = true)
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(name = "date", nullable = false, unique = true)
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Bitcoin2USDRate [id=" + id + ", rate for 1 bitcoin =" + rate + ", date=" + date + "]";
	}

}
