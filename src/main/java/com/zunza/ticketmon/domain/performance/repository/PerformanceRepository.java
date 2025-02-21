package com.zunza.ticketmon.domain.performance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zunza.ticketmon.domain.performance.entity.Performance;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceCustomRepository {

	@Query("SELECT DISTINCT p "
		+ "FROM Performance p "
		+ "JOIN FETCH p.venue v "
		+ "JOIN FETCH p.performancePrices pp "
		+ "WHERE p.id=:performanceId")
	Optional<Performance> findByIdFetchJoinVenueAndPrice(@Param("performanceId") Long performanceId);
}
