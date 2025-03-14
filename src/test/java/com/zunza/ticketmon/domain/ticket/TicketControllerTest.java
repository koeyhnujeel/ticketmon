package com.zunza.ticketmon.domain.ticket;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.zunza.ticketmon.domain.ticket.dto.SelectSeatsRequestDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class TicketControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private static final int TOTAL_USERS = 2000;
	private static final int SEAT_ID_RANGE = 500;
	private static final String URL = "/api/seats/lock";
	private final Random random = new Random();

	// 랜덤으로 섞은 좌석 번호 리스트
	private List<Integer> seatNumbers = new ArrayList<>();

	@BeforeEach
	public void setUp() {
		// 1부터 500까지의 좌석 번호를 생성
		for (int i = 1; i <= SEAT_ID_RANGE; i++) {
			seatNumbers.add(i);
		}
		// 리스트를 랜덤하게 섞기
		Collections.shuffle(seatNumbers, random);
	}

	@Test
	public void testConcurrentSeatLocking() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newFixedThreadPool(100);
		List<Future<ResponseEntity<String>>> futures = new ArrayList<>();

		for (int i = 1; i <= TOTAL_USERS; i++) {
			int userId = i;
			futures.add(executorService.submit(() -> sendRequest(userId)));
		}

		for (Future<ResponseEntity<String>> future : futures) {
			ResponseEntity<String> response = future.get();
			assertEquals(HttpStatus.OK, response.getStatusCode());
		}

		executorService.shutdown();
	}

	private ResponseEntity<String> sendRequest(int userId) {
		try {
			Thread.sleep(random.nextInt(3000) + 1000); // 10~50ms 랜덤 딜레이
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		var headers = new HttpHeaders();
		headers.set("X-User-Id", String.valueOf(userId));
		headers.setContentType(MediaType.APPLICATION_JSON);

		SelectSeatsRequestDto requestDto = new SelectSeatsRequestDto();
		// 섞은 좌석 번호 리스트에서 차례대로 하나씩 가져오기
		requestDto.setPerformanceSeatIds(List.of((long) seatNumbers.get(userId - 1))); // 1부터 500까지 랜덤 순서

		HttpEntity<SelectSeatsRequestDto> requestEntity = new HttpEntity<>(requestDto, headers);
		return restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
	}
}
