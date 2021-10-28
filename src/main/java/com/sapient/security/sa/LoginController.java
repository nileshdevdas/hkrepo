package com.sapient.security.sa;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.security.sa.jwt.ITokenHelper;

@RestController
public class LoginController {

	@Autowired
	ITokenHelper helper;
	private static final String SUCCESS = "Success";

	@PostMapping(path = "api/login", produces = "application/json")
	public @ResponseBody LoginResponse login(@RequestBody LoginRequest request) {
		Map<String, String> claims = new HashMap<>();
		claims.put("department", "IT");
		claims.put("subject", request.getUsername());
		String token = helper.generate(claims);
		return new LoginResponse(SUCCESS, token);
	}
}
