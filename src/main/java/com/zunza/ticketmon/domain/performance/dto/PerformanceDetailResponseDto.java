package com.zunza.ticketmon.domain.performance.dto;

import java.time.LocalDate;
import java.util.List;

import com.zunza.ticketmon.domain.performance.entity.Performance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceDetailResponseDto {
	private String title;
	private String venueName;
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private List<SeatGradeAndPrice> seatGradeAndPriceList;

	public static PerformanceDetailResponseDto of(Performance performance,
		List<SeatGradeAndPrice> seatGradeAndPriceList) {

		return new PerformanceDetailResponseDto(
			performance.getTitle(),
			performance.getVenue().getName(),
			performance.getDescription(),
			performance.getStartDate(),
			performance.getEndDate(),
			seatGradeAndPriceList
		);
	}
}
