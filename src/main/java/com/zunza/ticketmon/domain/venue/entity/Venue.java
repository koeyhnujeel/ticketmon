package com.zunza.ticketmon.domain.venue.entity;

import java.util.ArrayList;
import java.util.List;

import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Venue extends BaseEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int seatRows;

	@Column(nullable = false)
	private int seatColumns;

	@OneToMany(mappedBy = "venue")
	private List<Seat> seats = new ArrayList<>();

	@Builder
	private Venue(String name, int seatRows, int seatColumns) {
		this.name = name;
		this.seatRows = seatRows;
		this.seatColumns = seatColumns;
	}

	public static Venue of(String name, int seatRows, int seatColumns) {
		return Venue.builder()
			.name(name)
			.seatRows(seatRows)
			.seatColumns(seatColumns)
			.build();
	}
}
