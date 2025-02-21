package com.zunza.ticketmon.domain.ticket.dto;

import com.zunza.ticketmon.domain.performance.entity.Performance;
import com.zunza.ticketmon.domain.performance.entity.PerformancePrice;
import com.zunza.ticketmon.domain.schedule.Schedule;
import com.zunza.ticketmon.domain.venue.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceSeatSummaryResponseDto {
	private Long performanceSeatId;
	private PerformanceSummary performanceSummary;
	private SeatSummary seatSummary;

	public static PerformanceSeatSummaryResponseDto of(
		Long performanceSeatId, Performance performance,
		Schedule schedule, Seat seat, PerformancePrice performancePrice) {

		return new PerformanceSeatSummaryResponseDto(
			performanceSeatId,
			PerformanceSummary.of(performance, schedule),
			SeatSummary.of(seat, performancePrice)
		);
	}
}

