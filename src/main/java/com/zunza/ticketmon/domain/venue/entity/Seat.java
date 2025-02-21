package com.zunza.ticketmon.domain.venue.entity;

import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseEntity {

	@Column(nullable = false)
	private int seatRowNumber;

	@Column(nullable = false)
	private int seatColumnNumber;

	@Column(nullable = false)
	private String seatNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venue_id")
	private Venue venue;

	@Builder
	private Seat(int seatRowNumber, int seatColumnNumber, Venue venue) {
		this.seatRowNumber = seatRowNumber;
		this.seatColumnNumber = seatColumnNumber;
		this.seatNumber = seatRowNumber + "열 " + seatColumnNumber;
		this.venue = venue;
	}

	public static Seat of(int seatRowNumber, int seatColumnNumber, Venue venue) {
		return Seat.builder()
			.seatRowNumber(seatRowNumber)
			.seatColumnNumber(seatColumnNumber)
			.venue(venue)
			.build();
	}
}


