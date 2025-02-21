package com.zunza.ticketmon.domain.venue.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zunza.ticketmon.domain.venue.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByOrderBySeatRowNumberAscSeatColumnNumberAsc();
}
