package com.zunza.ticketmon.domain.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
	List<Schedule> findByPerformanceIdAndDate(Long performanceId, LocalDate date);
}
