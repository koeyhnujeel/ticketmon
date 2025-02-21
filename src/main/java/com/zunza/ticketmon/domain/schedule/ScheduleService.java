package com.zunza.ticketmon.domain.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zunza.ticketmon.domain.performance.entity.Performance;
import com.zunza.ticketmon.domain.performance.exception.PerformanceNotFoundException;
import com.zunza.ticketmon.domain.performance.repository.PerformanceRepository;
import com.zunza.ticketmon.domain.schedule.dto.PerformanceRoundResponseDto;
import com.zunza.ticketmon.domain.schedule.dto.PerformanceScheduleResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final PerformanceRepository performanceRepository;

	public PerformanceScheduleResponseDto getPerformanceSchedule(Long performanceId) {
		Performance performance = performanceRepository.findById(performanceId)
			.orElseThrow(PerformanceNotFoundException::new);

		return PerformanceScheduleResponseDto.from(performance);
	}

	public List<PerformanceRoundResponseDto> getPerformanceRound(Long performanceId, LocalDate date) {
		List<Schedule> schedules = scheduleRepository.findByPerformanceIdAndDate(performanceId, date);
		return schedules.stream()
			.map(PerformanceRoundResponseDto::from)
			.toList();
	}
}
