package com.zunza.ticketmon.domain.ticket.dto;

import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;
import com.zunza.ticketmon.domain.performance.entity.ReservationStatus;
import com.zunza.ticketmon.domain.performance.entity.SeatGrade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceSeatResponseDto {
	private Long performanceSeatId;
	private SeatGrade seatGrade;
	private ReservationStatus reservationStatus;

	public static PerformanceSeatResponseDto from(PerformanceSeat performanceSeat) {
		return new PerformanceSeatResponseDto(performanceSeat.getId(), performanceSeat.getPerformancePrice()
			.getSeatGrade(), performanceSeat.getReservationStatus());
	}
}
