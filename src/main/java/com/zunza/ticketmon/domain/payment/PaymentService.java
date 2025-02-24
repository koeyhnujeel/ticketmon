package com.zunza.ticketmon.domain.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zunza.ticketmon.domain.payment.dto.PaymentCheckoutResponseDto;
import com.zunza.ticketmon.domain.payment.dto.PaymentRequestDto;
import com.zunza.ticketmon.domain.payment.dto.PaymentResponseDto;
import com.zunza.ticketmon.domain.payment.exception.CardBalanceException;
import com.zunza.ticketmon.domain.payment.exception.CardInfoException;
import com.zunza.ticketmon.domain.payment.exception.CardLimitException;
import com.zunza.ticketmon.domain.payment.exception.NetworkException;
import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;
import com.zunza.ticketmon.domain.performance.exception.PerformanceSeatNotFoundException;
import com.zunza.ticketmon.domain.performance.repository.PerformanceSeatRepository;
import com.zunza.ticketmon.domain.ticket.Ticket;
import com.zunza.ticketmon.domain.ticket.TicketRepository;
import com.zunza.ticketmon.domain.ticket.dto.SelectedSeatDto;
import com.zunza.ticketmon.domain.user.User;
import com.zunza.ticketmon.domain.user.UserRepository;
import com.zunza.ticketmon.domain.user.exception.UserNotFoundException;
import com.zunza.ticketmon.util.PerformanceSeatUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final UserRepository userRepository;
	private final TicketRepository ticketRepository;
	private final PerformanceSeatUtil performanceSeatUtil;
	private final PerformanceSeatRepository performanceSeatRepository;

	public PaymentCheckoutResponseDto getCheckoutInfo(Long userId) {
		List<Long> performanceSeatIds = performanceSeatUtil.getSelectedSeats(userId);
		List<PerformanceSeat> performanceSeats = performanceSeatRepository.findByIdsWithSeatAndPrice(performanceSeatIds);
		if (performanceSeats.isEmpty()) {
			throw new PerformanceSeatNotFoundException();
		}

		List<SelectedSeatDto> selectedSeats = performanceSeats.stream()
			.map(PerformanceSeat -> SelectedSeatDto.of(PerformanceSeat.getId(), PerformanceSeat.getSeat(),
				PerformanceSeat.getPerformancePrice()))
			.toList();

		BigDecimal totalPrice = selectedSeats.stream()
			.map(SelectedSeatDto::getPrice)
			.reduce(BigDecimal.ZERO, BigDecimal::add);

		return new PaymentCheckoutResponseDto(selectedSeats, totalPrice);
	}

	@Retryable(
		value = {NetworkException.class},
		maxAttempts = 3,
		backoff = @Backoff(delay = 2000))
	@Transactional
	public PaymentResponseDto payment(Long userId, PaymentRequestDto paymentRequestDto) {
		List<Long> performanceSeatIds = paymentRequestDto.getPerformanceSeatIds();
		performanceSeatIds.stream()
			.filter(performanceSeatUtil::isExpired)
			.findAny()
			.ifPresent(id -> {
				throw new IllegalArgumentException("시간 초과");
			});

		List<Long> selectedSeats = performanceSeatUtil.getSelectedSeats(userId);
		if (!new HashSet<>(performanceSeatIds).equals(new HashSet<>(selectedSeats))) {
			throw new IllegalArgumentException("좌석 불일치");
		}

		PaymentResult value = getPaymentResult();
		return switch (value) {
			case SUCCESS -> {
				User user = userRepository.findById(userId)
					.orElseThrow(UserNotFoundException::new);

				List<PerformanceSeat> performanceSeats = performanceSeatRepository.findAllById(performanceSeatIds);
				if (performanceSeats.size() != performanceSeatIds.size()) {
					throw new PerformanceSeatNotFoundException();
				}

				Ticket ticket = ticketRepository.save(new Ticket(user, performanceSeats));
				performanceSeats.forEach(performanceSeat -> {
						performanceSeat.seatReservation();
						performanceSeat.updateTicket(ticket);
					});

				performanceSeatUtil.removeLock(userId, performanceSeatIds);

				yield new PaymentResponseDto(paymentRequestDto.getTotalPrice(), LocalDateTime.now());
			}

			case NETWORK_ERROR -> throw new NetworkException();
			case CARD_INFO_ERROR -> throw new CardInfoException();
			case CARD_BALANCE_ERROR -> throw new CardBalanceException();
			case CARD_LIMIT_ERROR -> throw new CardLimitException();
		};
	}

	private PaymentResult getPaymentResult() {
		Random random = new Random();
		PaymentResult[] values = PaymentResult.values();
		PaymentResult value = values[random.nextInt(values.length)];
		return value;
	}
}
