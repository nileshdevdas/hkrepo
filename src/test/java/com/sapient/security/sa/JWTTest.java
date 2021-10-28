package com.sapient.security.sa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sapient.security.sa.jwt.ITokenHelper;
import com.sapient.security.sa.jwt.JWTTokenHelper;

@DisplayName("Token Tests")
public class JWTTest {

	ITokenHelper helper = new JWTTokenHelper();

	@DisplayName("Should be able to generate a web token")
	@Test
	void testGenerateWebToken() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("department", "IT");
		map.put("subject", "Nilesh");
		String token = helper.generate(map);
		helper.validate(token);
	}

	@DisplayName("Should be able to validate a web token ")
	@Test
	void testValidateWebToken() {
		Map<String, String> map = new HashMap<>();
		map.put("department", "IT");
		map.put("subject", "Nilesh");
		String token = helper.generate(map);
		Map<String, String> claims = helper.parseClaim(token);
		assertEquals(map.get("department"), claims.get("department"));
	}
}
