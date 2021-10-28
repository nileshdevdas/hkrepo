package com.sapient.security.sa.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * @author Nilesh
 * @since 1.0
 * 
 */
@Service
public class JWTTokenHelper implements ITokenHelper {

	private static final String SECRET = "thisisveryveryverysecret";
	private static final String ROLE_KEY = "department";
	private static final String SUBJECT_KEY = "subject";
	private static final String ISSUER = "Vinsys";

	/**
	 * {@doc} Validate the token Signature.
	 */
	@Override
	public void validate(String token) throws InvalidTokenException {
		Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
	}

	@Override
	public String generate(Map<String, String> claim) {
		return Jwts.builder().signWith(SignatureAlgorithm.HS256, SECRET).claim(ROLE_KEY, claim.get(ROLE_KEY))
				.setIssuedAt(new Date()).setSubject(claim.get(SUBJECT_KEY)).setIssuer(ISSUER)
				.setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 30))).compact();
	}

	@Override
	public Map<String, String> parseClaim(String token) {
		var claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
		Map<String, String> myClaims = new HashMap<>();
		var theclaims = claims.getBody();
		myClaims.put(ROLE_KEY, theclaims.get(ROLE_KEY).toString());
		System.out.println(claims.getHeader());
		System.out.println(claims);
		myClaims.put("subject", theclaims.getSubject());
		return myClaims;
	}

}
