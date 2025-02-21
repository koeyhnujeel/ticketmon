package com.zunza.ticketmon.domain.venue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zunza.ticketmon.domain.venue.entity.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}
