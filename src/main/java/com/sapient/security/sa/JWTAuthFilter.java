package com.sapient.security.sa;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sapient.security.sa.jwt.ITokenHelper;
import com.sapient.security.sa.jwt.InvalidTokenException;

@Service
public class JWTAuthFilter extends OncePerRequestFilter {

	@Autowired
	private ITokenHelper helper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// step-1 Check if the the head has token
		String token = request.getHeader("Authorization");
		// step-2 i check if the token is not null and the url fired is not login or
		// those urls which is escaped ..
		if (token != null && !request.getRequestURI().contains("login")) {
			// now substring the token and remove the Bearer part out of it
			token = token.substring("Bearer ".length());
			try {
				// validate if the signature is proper /if not it will throw a exception and if
				// throws the exception
				// then we would have to return 401
				helper.validate(token);
				// if all is okay what i would be doing
				// parsing the claim -->convert the information from token to spring token
//				User userDetails = new User(helper.parseClaim(token).get("subject"), "",
//						Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//						userDetails, null, userDetails.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				SecurityContextHolder.getContext().setAuthentication(authentication);

				PreAuthenticatedAuthenticationToken ptoken = new PreAuthenticatedAuthenticationToken(
						helper.parseClaim(token).get("subject"), null);
				ptoken.setAuthenticated(true);
				ptoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(ptoken);
				

				// if you remove this line the whole thing falls down as how do you expect
				// spring to know who is logged ?
			} catch (InvalidTokenException e) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		filterChain.doFilter(request, response);
	}

}
