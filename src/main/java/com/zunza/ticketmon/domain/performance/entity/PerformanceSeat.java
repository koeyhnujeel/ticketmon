package com.zunza.ticketmon.domain.performance.entity;

import com.zunza.ticketmon.domain.schedule.Schedule;
import com.zunza.ticketmon.domain.ticket.Ticket;
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
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_price_id")
	private PerformancePrice performancePrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticket_id")
	private Ticket ticket;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus = ReservationStatus.AVAILABLE;

	public PerformanceSeat(Seat seat, PerformancePrice performancePrice, Schedule schedule) {
		this.seat = seat;
		this.performancePrice = performancePrice;
		this.schedule = schedule;
	}

	public void seatReservation() {
		this.reservationStatus = ReservationStatus.RESERVED;
	}

	public void updateTicket(Ticket ticket) {
		this.ticket = ticket;
	}
}
