package org.codingtest.ratecrawler.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "crawlerconfig")
@ApiModel(description = "Crawling Frequency in MS")
public class CrawlerConfig {

	@ApiModelProperty(notes = "The database generated ID")
	private long id;

	@ApiModelProperty(notes = "Name of the config")
	private String name;

	@ApiModelProperty(notes = "Refresh Rate in MS")
	private Integer refreshRate;

	@ApiModelProperty(notes = "Base URL")
	private String baseUrl;

	@ApiModelProperty(notes = "URI")
	private String uri;

	public CrawlerConfig() {

	}

	public CrawlerConfig(String name, Integer refreshRate, String baseUrl, String uri) {
		this.name = name;
		this.refreshRate = refreshRate;
		this.baseUrl = baseUrl;
		this.uri = uri;

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "refresh_rate", nullable = false)
	public Integer getRefreshRate() {
		return refreshRate;
	}

	public void setRefreshRate(Integer rate) {
		this.refreshRate = rate;
	}

	@Column(name = "base_url", nullable = false)
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String apiURL) {
		this.baseUrl = apiURL;
	}

	@Column(name = "uri", nullable = false)
	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "CrawlerConfig [id=" + id + ", name=" + name + ", Refresh rate in MS =" + refreshRate + "]";
	}

}
