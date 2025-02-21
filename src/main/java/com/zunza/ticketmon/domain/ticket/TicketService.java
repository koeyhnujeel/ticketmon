package com.zunza.ticketmon.domain.ticket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;
import com.zunza.ticketmon.domain.performance.entity.ReservationStatus;
import com.zunza.ticketmon.domain.performance.exception.PerformanceSeatNotFoundException;
import com.zunza.ticketmon.domain.performance.repository.PerformanceSeatRepository;
import com.zunza.ticketmon.domain.ticket.dto.PerformanceSeatResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.PerformanceSeatSummaryResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatRequestDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatResponseDto;
import com.zunza.ticketmon.domain.ticket.exception.InvalidUserInfoException;
import com.zunza.ticketmon.domain.ticket.exception.SeatReservationInProgressException;
import com.zunza.ticketmon.global.common.RedisKeyPrefix;
import com.zunza.ticketmon.util.PerformanceSeatUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final PerformanceSeatRepository performanceSeatRepository;
	private final PerformanceSeatUtil performanceSeatUtil;
	private final RedisTemplate<String, Object> redisTemplate;

	public List<PerformanceSeatResponseDto> getPerformanceSeats(Long scheduleId) {
		List<PerformanceSeatResponseDto> dtoList = new ArrayList<>();
		List<PerformanceSeat> seats = performanceSeatRepository.findByScheduleId(scheduleId);

		for (PerformanceSeat seat : seats) {
			String key = RedisKeyPrefix.TEMPLOCKED.getPrefix() + seat.getId();
			Object lock = redisTemplate.opsForValue().get(key);

			ReservationStatus reservationStatus = lock == null ? seat.getReservationStatus() : ReservationStatus.RESERVED;
			dtoList.add(new PerformanceSeatResponseDto(seat.getId(), seat.getPerformancePrice().getSeatGrade(), reservationStatus));
		}
		return dtoList;
	}

	public SelectSeatResponseDto selectSeat(Long userId, SelectSeatRequestDto selectSeatRequestDto) {
		PerformanceSeat performanceSeat = performanceSeatRepository.findById(selectSeatRequestDto.getPerformanceSeatId())
			.orElseThrow(PerformanceSeatNotFoundException::new);

		Boolean result = performanceSeatUtil.tryLockSeat(userId, selectSeatRequestDto.getPerformanceSeatId(), performanceSeat.getReservationStatus());
		if (!result) {
			throw new SeatReservationInProgressException();
		}
		return new SelectSeatResponseDto(selectSeatRequestDto.getPerformanceSeatId());
	}

	public PerformanceSeatSummaryResponseDto getSummary(Long userId, Long performanceSeatId) {
		Long selector = performanceSeatUtil.getSeatSelector(performanceSeatId);
		if (userId != selector) {
			throw new InvalidUserInfoException();
		}

		PerformanceSeat performanceSeat = performanceSeatRepository.findByPerformanceSeatWithAll(performanceSeatId)
			.orElseThrow(PerformanceSeatNotFoundException::new);

		return PerformanceSeatSummaryResponseDto.of(
			performanceSeatId,
			performanceSeat.getPerformance(),
			performanceSeat.getSchedule(),
			performanceSeat.getSeat(),
			performanceSeat.getPerformancePrice()
		);
	}
}
