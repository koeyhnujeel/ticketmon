package com.zunza.ticketmon.oauth2.dto;

public interface Oauth2Response {
	String getProvider();
	String getProviderId();
	String getEmail();
	String getName();
}
