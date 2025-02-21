package com.zunza.ticketmon;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.zunza.ticketmon.domain.performance.entity.PerformancePrice;
import com.zunza.ticketmon.domain.performance.entity.PerformanceSeat;
import com.zunza.ticketmon.domain.performance.entity.SeatGrade;
import com.zunza.ticketmon.domain.performance.repository.PerformancePriceRepository;
import com.zunza.ticketmon.domain.performance.repository.PerformanceRepository;
import com.zunza.ticketmon.domain.performance.entity.Category;
import com.zunza.ticketmon.domain.performance.entity.Performance;
import com.zunza.ticketmon.domain.performance.repository.PerformanceSeatRepository;
import com.zunza.ticketmon.domain.schedule.Schedule;
import com.zunza.ticketmon.domain.schedule.ScheduleRepository;
import com.zunza.ticketmon.domain.venue.entity.Seat;
import com.zunza.ticketmon.domain.venue.repository.SeatRepository;
import com.zunza.ticketmon.domain.venue.entity.Venue;
import com.zunza.ticketmon.domain.venue.repository.VenueRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInit implements CommandLineRunner {

	private final PerformanceRepository performanceRepository;
	private final PerformancePriceRepository performancePriceRepository;
	private final PerformanceSeatRepository performanceSeatRepository;
	private final ScheduleRepository scheduleRepository;
 	private final VenueRepository venueRepository;
	private final SeatRepository seatRepository;

	@Override
	public void run(String... args) throws Exception {
		Venue venue = venueRepository.save(Venue.of("Main", 10, 50));
		for (int i = 1; i <= venue.getSeatRows(); i++) {
			for (int j = 1; j <= venue.getSeatColumns(); j++) {
				seatRepository.save(Seat.of(i, j, venue));
			}
		}

		long interval = 0;
		for (int i = 1; i <= 5; i++) {
			LocalDate startDate = LocalDate.now().plusDays(interval);
			LocalDate endDate = startDate.plusDays(3);
			Performance performance = performanceRepository.save(
				Performance.of("title" + i, "description" + i, venue, Category.CONCERT, startDate, endDate));
			List<PerformancePrice> performancePrices = createPerformancePrice(performance);
			List<Schedule> schedules = createSchedule(performance, startDate);
			List<Seat> seats = seatRepository.findByOrderBySeatRowNumberAscSeatColumnNumberAsc();
			createPerformanceSeat(performance, performancePrices, schedules, seats);
			interval += 4;
		}
	}

	public List<PerformancePrice> createPerformancePrice(Performance performance) {
		List<PerformancePrice> performancePrices = new ArrayList<>();

		PerformancePrice performancePrice1 = new PerformancePrice(performance, SeatGrade.VIP, BigDecimal.valueOf(170000));
		performancePrices.add(performancePriceRepository.save(performancePrice1));
		PerformancePrice performancePrice2 = new PerformancePrice(performance, SeatGrade.R, BigDecimal.valueOf(140000));
		performancePrices.add(performancePriceRepository.save(performancePrice2));
		PerformancePrice performancePrice3 = new PerformancePrice(performance, SeatGrade.S, BigDecimal.valueOf(110000));
		performancePrices.add(performancePriceRepository.save(performancePrice3));
		PerformancePrice performancePrice4 = new PerformancePrice(performance, SeatGrade.A, BigDecimal.valueOf(80000));
		performancePrices.add(performancePriceRepository.save(performancePrice4));

		return performancePrices;
	}

	public List<Schedule> createSchedule(Performance performance, LocalDate startDate) {
		List<Schedule> schedules = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			LocalDate date = startDate.plusDays(i);
			LocalTime time = LocalTime.of(13, 30);
			Schedule schedule1 = new Schedule(date, time, performance);
			schedules.add(scheduleRepository.save(schedule1));

			LocalDate date2 = startDate.plusDays(i);
			LocalTime time2 = LocalTime.of(16, 30);
			Schedule schedule2 = new Schedule(date2, time2, performance);
			schedules.add(scheduleRepository.save(schedule2));
		}
		return schedules;
	}

	public void createPerformanceSeat(
		Performance performance,
		List<PerformancePrice> performancePrices,
		List<Schedule> schedules,
		List<Seat> seats) {

		for (Schedule schedule : schedules) {
			for (Seat seat : seats) {
				if (seat.getSeatRowNumber() >= 9) performanceSeatRepository.save(new PerformanceSeat(performance, seat, performancePrices.get(3), schedule));
				else if (seat.getSeatRowNumber() >= 7) performanceSeatRepository.save(new PerformanceSeat(performance, seat, performancePrices.get(2), schedule));
				else if (seat.getSeatRowNumber() >= 5) performanceSeatRepository.save(new PerformanceSeat(performance, seat, performancePrices.get(1), schedule));
				else performanceSeatRepository.save(new PerformanceSeat(performance, seat, performancePrices.get(0), schedule));
			}
		}
	}
}
