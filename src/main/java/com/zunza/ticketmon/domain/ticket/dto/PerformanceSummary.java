package com.zunza.ticketmon.domain.ticket.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.zunza.ticketmon.domain.performance.entity.Performance;
import com.zunza.ticketmon.domain.schedule.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceSummary {
	private Long id;
	private String title;
	private LocalDate date;
	private LocalTime time;

	public static PerformanceSummary of(Performance performance, Schedule schedule) {
		return new PerformanceSummary(
			performance.getId(),
			performance.getTitle(),
			schedule.getDate(),
			schedule.getTime()
		);
	}
}
