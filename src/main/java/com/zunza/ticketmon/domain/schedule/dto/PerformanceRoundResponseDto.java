package com.zunza.ticketmon.domain.schedule.dto;

import java.time.LocalTime;

import com.zunza.ticketmon.domain.schedule.Schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceRoundResponseDto {
	private Long scheduleId;
	private LocalTime showTime;

	public static PerformanceRoundResponseDto from(Schedule schedule) {
		return new PerformanceRoundResponseDto(schedule.getId(), schedule.getTime());
	}
}
