package com.zunza.ticketmon.domain.ticket;

import java.math.BigDecimal;
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
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatsRequestDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectSeatResponseDto;
import com.zunza.ticketmon.domain.ticket.dto.SelectedSeatDto;
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
			String key = RedisKeyPrefix.TEMP_LOCKED.getPrefix() + seat.getId();
			Object lock = redisTemplate.opsForValue().get(key);

			ReservationStatus reservationStatus = lock == null ? seat.getReservationStatus() : ReservationStatus.RESERVED;
			dtoList.add(new PerformanceSeatResponseDto(seat.getId(), seat.getPerformancePrice().getSeatGrade(), reservationStatus));
		}
		return dtoList;
	}

	public SelectSeatResponseDto selectSeat(Long userId, SelectSeatsRequestDto selectSeatsRequestDto) {
		List<Long> requestSeatIds = selectSeatsRequestDto.getPerformanceSeatIds();
		List<PerformanceSeat> performanceSeats = performanceSeatRepository.findAllById(requestSeatIds);

		if (requestSeatIds.size() != performanceSeats.size()) {
			throw new PerformanceSeatNotFoundException();
		}

		performanceSeatUtil.tryLockSeat(userId, performanceSeats);
		return new SelectSeatResponseDto(selectSeatsRequestDto.getPerformanceSeatIds());
	}

	public PerformanceSeatSummaryResponseDto getSummary(Long userId) {
		List<Long> performanceSeatIds = performanceSeatUtil.getSelectedSeats(userId);

		List<PerformanceSeat> performanceSeats = performanceSeatRepository.findByIdsWithAll(performanceSeatIds);
		if (performanceSeatIds.size() != performanceSeats.size()) {
			throw new PerformanceSeatNotFoundException();
		}

		List<SelectedSeatDto> selectedSeats = performanceSeats.stream()
			.map(PerformanceSeat -> SelectedSeatDto.of(PerformanceSeat.getId(), PerformanceSeat.getSeat(),
				PerformanceSeat.getPerformancePrice()))
			.toList();

		BigDecimal totalPrice = selectedSeats.stream()
			.map(SelectedSeatDto::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new PerformanceSeatSummaryResponseDto(
			performanceSeats.get(0).getSchedule().getPerformance().getTitle(),
			performanceSeats.get(0).getSchedule().getDate(),
			performanceSeats.get(0).getSchedule().getTime(),
			selectedSeats,
			totalPrice
		);
	}
}
