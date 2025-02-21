package com.zunza.ticketmon.domain.performance.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.zunza.ticketmon.domain.venue.entity.Venue;
import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance extends BaseEntity {

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	private Venue venue;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	@OneToMany(mappedBy = "performance")
	private List<PerformancePrice> performancePrices = new ArrayList<>();

	@Builder
	private Performance(String title, String description, Venue venue, Category category, LocalDate startDate,
		LocalDate endDate) {
		this.title = title;
		this.description = description;
		this.venue = venue;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public static Performance of(String title, String description, Venue venue, Category category, LocalDate startDate,
		LocalDate endDate) {

		return Performance.builder()
			.title(title)
			.description(description)
			.venue(venue)
			.category(category)
			.startDate(startDate)
			.endDate(endDate)
			.build();
	}
}
