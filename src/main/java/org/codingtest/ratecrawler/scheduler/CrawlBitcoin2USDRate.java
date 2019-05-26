package org.codingtest.ratecrawler.scheduler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.codingtest.ratecrawler.model.Bitcoin2USDRate;
import org.codingtest.ratecrawler.model.Bitcoin2USDRateRepository;
import org.codingtest.ratecrawler.model.CrawlerConfig;
import org.codingtest.ratecrawler.model.CrawlerConfigRepository;
import org.codingtest.ratecrawler.model.ResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
@Service
public class CrawlBitcoin2USDRate implements SchedulingConfigurer {

	private static final Logger log = LoggerFactory.getLogger(CrawlBitcoin2USDRate.class);

	@Autowired
	private CrawlerConfigRepository crawlerConfigRepository;

	@Autowired
	private Bitcoin2USDRateRepository rateRepository;

	public void fetchBitcoin2USDRate() {
		CrawlerConfig config = crawlerConfigRepository.findById((long) 1).get();
		RestTemplate restTemplate = new RestTemplate();
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
				MediaType.APPLICATION_OCTET_STREAM, new MediaType("application", "javascript")));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

		ResponsePayload payload = restTemplate.getForObject(config.getBaseUrl() + config.getURI(),
				ResponsePayload.class);
		Bitcoin2USDRate newRate = new Bitcoin2USDRate(payload.getRate(), payload.getTime());
		if (rateRepository.findByDateAndRate(newRate.getDate(), newRate.getRate()).isEmpty())
			rateRepository.save(newRate);
		log.info("The current rate is {} - Date {}", payload.getRate(), payload.getTime());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				fetchBitcoin2USDRate();
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				Calendar nextExecutionTime = new GregorianCalendar();
				Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
				nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
				Optional<CrawlerConfig> config = crawlerConfigRepository.findById((long) 1);
				nextExecutionTime.add(Calendar.MILLISECOND, config.get().getRefreshRate());
				return nextExecutionTime.getTime();
			}
		});
	}

}