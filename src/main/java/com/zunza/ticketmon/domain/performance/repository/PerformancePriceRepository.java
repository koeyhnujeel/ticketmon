package com.zunza.ticketmon.domain.performance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zunza.ticketmon.domain.performance.entity.PerformancePrice;

@Repository
public interface PerformancePriceRepository extends JpaRepository<PerformancePrice, Long> {
}
