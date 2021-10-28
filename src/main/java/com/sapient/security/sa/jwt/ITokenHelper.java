package com.sapient.security.sa.jwt;

import java.util.Map;

public interface ITokenHelper {

	public void validate(String token) throws InvalidTokenException;

	public String generate(Map<String, String> claim);

	public Map<String, String> parseClaim(String token);
}
