package com.langnatech.ipms.webservice;

public class Authenticator {

	private String username;
	private String timestamp;
	private String authenticator;

	public Authenticator() {

	}

	public Authenticator(String username, String timestamp, String authenticator) {
		this.username = username;
		this.timestamp = timestamp;
		this.authenticator = authenticator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getAuthenticator() {
		return authenticator;
	}

	public void setAuthenticator(String authenticator) {
		this.authenticator = authenticator;
	}

}
