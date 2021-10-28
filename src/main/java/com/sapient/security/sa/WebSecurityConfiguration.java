package com.sapient.security.sa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTAuthFilter filter;

	/**
	 * this is where you can tell what to allow what not to allow
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// which customizes the spring security to embed the jwt token information...
		http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests().antMatchers("/api/login", "/error").permitAll().anyRequest()
				.hasAnyRole("ADMIN");
		// this is the filter i am adding to allow my JWT Validation
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
	}
}
