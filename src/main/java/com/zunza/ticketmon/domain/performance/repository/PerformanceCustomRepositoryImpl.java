package com.zunza.ticketmon.domain.performance.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zunza.ticketmon.domain.performance.dto.PerformancesResponseDto;
import com.zunza.ticketmon.domain.performance.entity.Category;
import com.zunza.ticketmon.domain.performance.entity.QPerformance;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PerformanceCustomRepositoryImpl implements PerformanceCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	private QPerformance performance = QPerformance.performance;

	@Override
	public Page<PerformancesResponseDto> findPerformancesWithPagination(String category, Pageable pageable) {
		List<PerformancesResponseDto> result = jpaQueryFactory
			.select(
				Projections.constructor(
					PerformancesResponseDto.class,
					performance.id,
					performance.title,
					performance.venue.name,
					performance.startDate,
					performance.endDate
				)
			)
			.from(performance)
			.where(performance.category.eq(Category.valueOf(category)))
			.orderBy(performance.startDate.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = Objects.requireNonNullElse(jpaQueryFactory
			.select(performance.count())
			.from(performance)
			.where(performance.category.eq(Category.valueOf(category)))
			.fetchOne(),
			0L
		);

		return new PageImpl<>(result, pageable, total);
	}
}
