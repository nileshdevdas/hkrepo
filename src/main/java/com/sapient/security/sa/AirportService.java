package com.sapient.security.sa;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed("abc")
public class AirportService {

	@GetMapping(path = "/api/all", produces = "application/json")
	public List<String> allAirports() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		List<String> airports = new ArrayList<>();
		airports.add("XXX");
		return airports;
	}
}
