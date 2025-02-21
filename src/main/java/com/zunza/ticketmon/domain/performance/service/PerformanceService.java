package com.zunza.ticketmon.domain.performance.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zunza.ticketmon.domain.performance.dto.PerformanceDetailResponseDto;
import com.zunza.ticketmon.domain.performance.dto.PerformancesResponseDto;
import com.zunza.ticketmon.domain.performance.dto.SeatGradeAndPrice;
import com.zunza.ticketmon.domain.performance.entity.Performance;
import com.zunza.ticketmon.domain.performance.exception.PerformanceNotFoundException;
import com.zunza.ticketmon.domain.performance.repository.PerformanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PerformanceService {

	private final PerformanceRepository performanceRepository;

	public Page<PerformancesResponseDto> getPerformanceList(String category, int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size);
		return performanceRepository.findPerformancesWithPagination(category, pageable);
	}

	public PerformanceDetailResponseDto getPerformanceDetail(Long performanceId) {
		Performance performance = performanceRepository.findByIdFetchJoinVenueAndPrice(performanceId)
			.orElseThrow(PerformanceNotFoundException::new);

		List<SeatGradeAndPrice> seatGradeAndPriceList = performance.getPerformancePrices().stream()
			.map(SeatGradeAndPrice::from)
			.toList();

		return PerformanceDetailResponseDto.of(performance, seatGradeAndPriceList);
	}
}
