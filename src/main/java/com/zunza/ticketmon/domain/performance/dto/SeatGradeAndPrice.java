package com.zunza.ticketmon.domain.performance.dto;

import java.math.BigDecimal;

import com.zunza.ticketmon.domain.performance.entity.PerformancePrice;
import com.zunza.ticketmon.domain.performance.entity.SeatGrade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SeatGradeAndPrice {
	private SeatGrade seatGrade;
	private BigDecimal price;

	public static SeatGradeAndPrice from(PerformancePrice performancePrice) {
		return new SeatGradeAndPrice(performancePrice.getSeatGrade(), performancePrice.getPrice());
	}
}
