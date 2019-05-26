package org.codingtest.ratecrawler.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Bitcoin2USDRateRepository extends JpaRepository<Bitcoin2USDRate, Long> {

	@Query("SELECT item FROM Bitcoin2USDRate item WHERE item.date >= :startDate AND item.date <= :endDate")
	public List<Bitcoin2USDRate> findByDates(@Param("startDate") LocalDateTime startDate,
			@Param("endDate") LocalDateTime endDate);

	@Query("SELECT item FROM Bitcoin2USDRate item WHERE item.date = :date AND item.rate <= :rate")
	public List<Bitcoin2USDRate> findByDateAndRate(@Param("date") LocalDateTime date, @Param("rate") String rate);

}