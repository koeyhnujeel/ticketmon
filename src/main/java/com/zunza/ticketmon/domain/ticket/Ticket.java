package com.zunza.ticketmon.domain.ticket;

import java.util.ArrayList;
import java.util.List;

import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;
import com.zunza.ticketmon.domain.user.User;
import com.zunza.ticketmon.global.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "ticket")
	private List<PerformanceSeat> performanceSeats = new ArrayList<>();

	public Ticket(User user, List<PerformanceSeat> performanceSeats) {
		this.user = user;
		this.performanceSeats = performanceSeats;
	}
}
