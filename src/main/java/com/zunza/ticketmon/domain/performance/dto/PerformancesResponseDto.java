package com.zunza.ticketmon.domain.performance.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformancesResponseDto {
	private Long id;
	private String title;
	private String venueName;
	private LocalDate startDate;
	private LocalDate endDate;
}
