package com.sapient.security.sa;

public class LoginResponse {

	private String status;

	public LoginResponse() {
	}

	public LoginResponse(String status, String token) {
		this.status = status;
		this.token = token;
	}

	private String token;

	public String getStatus() {
		return status;
	}

	public String getToken() {
		return token;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
