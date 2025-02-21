package com.zunza.ticketmon.domain.ticket;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zunza.ticketmon.domain.ticket.dto.PerformanceSeatResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.PerformanceSeatSummaryResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatRequestDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatResponseDto;

import lombok.Getter;
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
		@AuthenticationPrincipal Long userId,
		@RequestBody SelectSeatRequestDto selectSeatRequestDto) {
		return ResponseEntity.ok(ticketService.selectSeat(userId, selectSeatRequestDto));
	}

	@GetMapping("/api/seats/{performanceSeatId}/summary")
	public ResponseEntity<PerformanceSeatSummaryResponseDto> getSummary(
		@AuthenticationPrincipal Long userId,
		@PathVariable Long performanceSeatId) {
		return ResponseEntity.ok(ticketService.getSummary(userId, performanceSeatId));
	}
}
