package com.zunza.ticketmon.domain.ticket;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.zunza.ticketmon.domain.ticket.dto.PerformanceSeatResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.PerformanceSeatSummaryResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatsRequestDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TicketController {

	private final TicketService ticketService;

	@GetMapping("/api/performances/{performanceId}/schedules/{scheduleId}/seats")
	public ResponseEntity<List<PerformanceSeatResponseDto>> getPerformanceSeats(@PathVariable Long scheduleId) {
		return ResponseEntity.ok(ticketService.getPerformanceSeats(scheduleId));
	}

	@PostMapping("/api/seats/lock")
	public ResponseEntity<SelectSeatResponseDto> selectSeat(
		@RequestHeader("X-User-Id") Long userId,
		@RequestBody SelectSeatsRequestDto selectSeatsRequestDto) {
		return ResponseEntity.ok(ticketService.selectSeat(userId, selectSeatsRequestDto));
	}

	@GetMapping("/api/seats/summary")
	public ResponseEntity<PerformanceSeatSummaryResponseDto> getSummary(@RequestHeader("X-User-Id") Long userId) {
		return ResponseEntity.ok(ticketService.getSummary(userId));
	}
}
