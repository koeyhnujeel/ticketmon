package com.zunza.ticketmon.domain.schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import com.zunza.ticketmon.domain.performance.entity.Performance;
import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity {

	@Column(nullable = false)
	private LocalDate date;

	@Column(nullable = false)
	private LocalTime time;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_id")
	private Performance performance;

	public Schedule(LocalDate date, LocalTime time, Performance performance) {
		this.date = date;
		this.time = time;
		this.performance = performance;
	}
}
