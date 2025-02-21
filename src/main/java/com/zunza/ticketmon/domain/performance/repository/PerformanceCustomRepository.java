package com.zunza.ticketmon.domain.performance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zunza.ticketmon.domain.performance.dto.PerformancesResponseDto;

public interface PerformanceCustomRepository {
	Page<PerformancesResponseDto> findPerformancesWithPagination(String category, Pageable pageable);
}
