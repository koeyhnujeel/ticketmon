package com.zunza.ticketmon.domain.ticket.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectSeatResponseDto {
	private List<Long> performanceSeatIds;
}
