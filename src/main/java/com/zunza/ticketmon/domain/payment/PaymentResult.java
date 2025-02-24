package com.zunza.ticketmon.domain.payment;

public enum PaymentResult {
	SUCCESS,
	NETWORK_ERROR,
	CARD_INFO_ERROR,
	CARD_LIMIT_ERROR,
	CARD_BALANCE_ERROR
}
