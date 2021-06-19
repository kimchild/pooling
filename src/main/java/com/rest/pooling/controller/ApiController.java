package com.rest.pooling.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class ApiController {

	private final RestTemplate restTemplate;

	public ApiController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@GetMapping("/call")
	public String testCall() {
		return "called!";
	}

	@GetMapping
	public String test() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.add("Authorization", "Bearer " + this.getAuth());
		headers.add("Content-Type", "application/json");

		//when
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/test/call",
			HttpMethod.GET,
			new HttpEntity<>(null, headers),
			String.class
		);

		String str = response.getBody();
		System.out.println(str);
		return str;
	}
}
