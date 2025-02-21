package com.zunza.ticketmon.domain.performance.entity;

import java.math.BigDecimal;

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
public class PerformancePrice extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_id")
	private Performance performance;

	@Column(nullable = false)
	private SeatGrade seatGrade;

	@Column(nullable = false)
	private BigDecimal price;

	public PerformancePrice(Performance performance, SeatGrade seatGrade, BigDecimal price) {
		this.performance = performance;
		this.seatGrade = seatGrade;
		this.price = price;
	}
}
