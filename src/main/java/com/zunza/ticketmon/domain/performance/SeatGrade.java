package com.zunza.ticketmon.domain.performance;

import java.math.BigInteger;

public enum SeatGrade {
	VIP("VIP", 170000),
	R("R", 140000),
	S("S", 110000),
	A("A", 80000);

	private String grade;
	private int price;

	SeatGrade(String grade, int price) {
		this.grade = grade;
		this.price = price;
	}
}
