package com.zunza.ticketmon.domain.performance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeat, Long> {

	@Query("SELECT DISTINCT ps "
		+ "FROM PerformanceSeat ps "
		+ "JOIN FETCH ps.performancePrice pp "
		+ "WHERE ps.schedule.id=:scheduleId")
	List<PerformanceSeat> findByScheduleId(@Param("scheduleId") Long scheduleId);

	@Query("SELECT DISTINCT ps "
		+ "FROM PerformanceSeat ps "
		+ "JOIN FETCH ps.performance p "
		+ "JOIN FETCH ps.seat s "
		+ "JOIN FETCH ps.performancePrice pp "
		+ "JOIN FETCH ps.schedule sd "
		+ "WHERE ps.id=:performanceSeatId")
	Optional<PerformanceSeat> findByPerformanceSeatWithAll(@Param("performanceSeatId") Long performanceSeatId);
}
