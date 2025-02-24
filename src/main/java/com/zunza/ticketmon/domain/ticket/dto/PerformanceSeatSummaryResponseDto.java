package com.zunza.ticketmon.domain.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceSeatSummaryResponseDto {
	private String performanceTitle;
	private LocalDate date;
	private LocalTime time;
	private List<SelectedSeatDto> selectedSeats;
	private BigDecimal totalPrice;
}

