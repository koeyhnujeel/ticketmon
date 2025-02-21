package com.zunza.ticketmon.domain.schedule;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zunza.ticketmon.domain.schedule.dto.PerformanceRoundResponseDto;
import com.zunza.ticketmon.domain.schedule.dto.PerformanceScheduleResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;

	@GetMapping("/api/performances/{performanceId}/schedules")
	public ResponseEntity<PerformanceScheduleResponseDto> getPerformanceDate(@PathVariable Long performanceId) {
		return ResponseEntity.ok(scheduleService.getPerformanceSchedule(performanceId));
	}

	@GetMapping("/api/performances/{performanceId}/schedules/round")
	public ResponseEntity<List<PerformanceRoundResponseDto>> getPerformanceRound(
		@PathVariable Long performanceId,
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

		return ResponseEntity.ok(scheduleService.getPerformanceRound(performanceId, date));
	}
}
