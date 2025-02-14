package com.zunza.ticketmon.domain.performance;

import java.time.LocalDateTime;

import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
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

	@Column(nullable = false)
	private LocalDateTime startDt;

	@Column(nullable = false)
	private LocalDateTime endDt;

	@Column(nullable = false)
	private Category category;
}
