package com.zunza.ticketmon.domain.ticket.dto;

import java.math.BigDecimal;

import com.zunza.ticketmon.domain.performance.entity.PerformancePrice;
import com.zunza.ticketmon.domain.performance.entity.SeatGrade;
import com.zunza.ticketmon.domain.venue.entity.Seat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatSummary {
	private String seatNumber;
	private SeatGrade seatGrade;
	private BigDecimal price;

	public static SeatSummary of(Seat seat, PerformancePrice performancePrice) {
		return new SeatSummary(seat.getSeatNumber(), performancePrice.getSeatGrade(), performancePrice.getPrice());
	}
}
