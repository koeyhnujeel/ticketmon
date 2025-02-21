package com.zunza.ticketmon.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zunza.ticketmon.domain.performance.entity.ReservationStatus;
import com.zunza.ticketmon.domain.ticket.exception.SeatAlreadyReservedException;
import com.zunza.ticketmon.global.common.RedisKeyPrefix;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerformanceSeatUtil {

	private final RedisTemplate<String, Object> redisTemplate;

	private boolean isSeatReserved(ReservationStatus reservationStatus) {
		return reservationStatus == ReservationStatus.RESERVED;
	}

	public Boolean tryLockSeat(Long userId, Long performanceSeatId, ReservationStatus reservationStatus) {
		if (isSeatReserved(reservationStatus)) {
			throw new SeatAlreadyReservedException();
		}

		return redisTemplate.opsForValue().setIfAbsent(
			RedisKeyPrefix.TEMPLOCKED.getPrefix() + performanceSeatId,
			userId,
			5,
			TimeUnit.MINUTES);
	}

	public Long getSeatSelector(Long performanceSeatId) {
		Integer value = (Integer)redisTemplate.opsForValue().get(RedisKeyPrefix.TEMPLOCKED.getPrefix() + performanceSeatId);
		return value != null ? value.longValue() : -1;
	}
}
