package com.zunza.ticketmon.domain.performance.entity;

import com.zunza.ticketmon.domain.schedule.Schedule;
import com.zunza.ticketmon.domain.venue.entity.Seat;
import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceSeat extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_id")
	private Performance performance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_price_id")
	private PerformancePrice performancePrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus = ReservationStatus.AVAILABLE;

	public PerformanceSeat(Performance performance, Seat seat, PerformancePrice performancePrice, Schedule schedule) {
		this.performance = performance;
		this.seat = seat;
		this.performancePrice = performancePrice;
		this.schedule = schedule;
	}
}
