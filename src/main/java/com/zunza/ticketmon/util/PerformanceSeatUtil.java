package com.zunza.ticketmon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;
import com.zunza.ticketmon.domain.performance.entity.ReservationStatus;
import com.zunza.ticketmon.domain.ticket.exception.SeatAlreadyReservedException;
import com.zunza.ticketmon.domain.ticket.exception.SeatReservationInProgressException;
import com.zunza.ticketmon.global.common.RedisKeyPrefix;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PerformanceSeatUtil {

	private final RedisTemplate<String, Object> redisTemplate;

	private boolean isSeatReserved(ReservationStatus reservationStatus) {
		return reservationStatus == ReservationStatus.RESERVED;
	}

	//TODO Redis List에 저장된 유저 : 좌석 정보 삭제하는 방법(대안) 찾기
	public void tryLockSeat(Long userId, List<PerformanceSeat> performanceSeats) {
		performanceSeats.stream()
			.filter(performanceSeat -> isSeatReserved(performanceSeat.getReservationStatus()))
			.findAny()
			.ifPresent(performanceSeat -> {
				throw new SeatAlreadyReservedException();
			});

		List<String> lockedKeys = new ArrayList<>();
		String userKey = RedisKeyPrefix.USER_SELECTED_SEAT.getPrefix() + userId;

		try {
			for (PerformanceSeat performanceSeat : performanceSeats) {
				String seatKey = RedisKeyPrefix.TEMP_LOCKED.getPrefix() + performanceSeat.getId();
				Boolean result = redisTemplate.opsForValue().setIfAbsent(seatKey, userId, 5, TimeUnit.MINUTES);

				if (Boolean.FALSE.equals(result)) {
					throw new SeatReservationInProgressException();
				}

				redisTemplate.opsForList().rightPush(userKey, performanceSeat.getId());
				lockedKeys.add(seatKey);
			}
		} catch (SeatReservationInProgressException e) {
			lockedKeys.forEach(redisTemplate::delete);
			redisTemplate.delete(userKey);
			throw e;
		}
	}

	public List<Long> getSelectedSeats(Long userId) {
		List<Object> seatIds = redisTemplate.opsForList()
			.range(RedisKeyPrefix.USER_SELECTED_SEAT.getPrefix() + userId, 0, -1);

		return seatIds.stream()
			.map(seatId -> {
				Integer id = (Integer)seatId;
				return id.longValue();
			})
			.toList();
	}

	public boolean isExpired(Long performanceSeatId) {
		String key = RedisKeyPrefix.TEMP_LOCKED.getPrefix() + performanceSeatId;
		return Boolean.FALSE.equals(redisTemplate.hasKey(key));
	}

	public void removeLock(Long userId, List<Long> performanceSeatIds) {
		performanceSeatIds.forEach(id -> redisTemplate.delete(RedisKeyPrefix.TEMP_LOCKED.getPrefix() + id));
		redisTemplate.delete(RedisKeyPrefix.USER_SELECTED_SEAT.getPrefix() + userId);
	}
}
