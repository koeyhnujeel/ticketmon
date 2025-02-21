package com.zunza.ticketmon.domain.schedule.dto;

import java.time.LocalDate;

import com.zunza.ticketmon.domain.performance.entity.Performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceScheduleResponseDto {
	private LocalDate startDate;
	private LocalDate endDate;

	public static PerformanceScheduleResponseDto from(Performance performance) {
		return new PerformanceScheduleResponseDto(performance.getStartDate(), performance.getEndDate());
	}
}
