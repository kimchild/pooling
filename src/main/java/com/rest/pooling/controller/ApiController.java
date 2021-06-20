package com.rest.pooling.controller;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test")
@Slf4j
public class ApiController {

	private final RestTemplate restTemplate;

	public ApiController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private static boolean isFirst = true;
	private static Map map = new ConcurrentHashMap<>();
	private static int i;

	@GetMapping("/call")
	public String testCall() {

		StringBuffer resultBuilder = new StringBuffer();
		resultBuilder.append("index : ");
		resultBuilder.append(++i);
		resultBuilder.append(" start : ");
		resultBuilder.append(map.get("start"));
		resultBuilder.append(" / end : ");
		resultBuilder.append(map.get("end"));
		return resultBuilder.toString();
	}

	@GetMapping
	public String test() {
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(date.getHours());
		sb.append(":");
		sb.append(date.getMinutes());
		sb.append(":");
		sb.append(date.getSeconds());
		if (isFirst) {
			map.put("start", sb.toString());
			isFirst = false;
		}
		map.put("end", sb.toString());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// headers.add("Authorization", "Bearer " + this.getAuth());
		headers.add("Content-Type", "application/json");

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/test/call",
			HttpMethod.GET,
			new HttpEntity<>(null, headers),
			String.class
		);
		//
		String str = response.getBody();

		// System.out.println("start : " + map.get("start"));
		// System.out.println("end : " + map.get("end"));
		// return str;

		return str;
	}

	/**
	 * ApiController Exception Handler
	 */
	@ExceptionHandler(value = Exception.class)
	public void handleSchedulerError(Exception exception) {
		log.error("ApiController ERROR : {}", exception.getMessage(), exception);
	}
}
