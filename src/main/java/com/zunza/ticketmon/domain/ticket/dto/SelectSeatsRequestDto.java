package com.zunza.ticketmon.domain.ticket.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SelectSeatsRequestDto {
	private List<Long> performanceSeatIds;

	public void setPerformanceSeatIds(List<Long> longs) {
		this.performanceSeatIds = longs;
	}
}
