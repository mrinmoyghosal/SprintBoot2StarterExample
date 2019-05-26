package org.codingtest.ratecrawler.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlerConfigRepository extends JpaRepository<CrawlerConfig, Long>{

}