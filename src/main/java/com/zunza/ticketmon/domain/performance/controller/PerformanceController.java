package com.zunza.ticketmon.domain.performance.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zunza.ticketmon.domain.performance.dto.PerformanceDetailResponseDto;
import com.zunza.ticketmon.domain.performance.service.PerformanceService;
import com.zunza.ticketmon.domain.performance.dto.PerformancesResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performances")
public class PerformanceController {

	private final PerformanceService performanceService;

	@GetMapping
	public ResponseEntity<Page<PerformancesResponseDto>> getPerformances(
		@RequestParam(defaultValue = "CONCERT") String category,
		@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		return ResponseEntity.ok(performanceService.getPerformanceList(category, page, size));
	}

	@GetMapping("/{performanceId}")
	public ResponseEntity<PerformanceDetailResponseDto> getPerformanceDetail(@PathVariable Long performanceId) {
		return ResponseEntity.ok(performanceService.getPerformanceDetail(performanceId));
	}
}
