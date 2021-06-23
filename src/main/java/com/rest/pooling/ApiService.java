package com.rest.pooling;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rest.pooling.component.JacksonMapper;
import com.rest.pooling.dto.ResponseBusinessDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiService {
	private final RestTemplate restTemplate;
	private final JacksonMapper<ResponseBusinessDto> mapper;

	public ApiService(RestTemplate restTemplate, JacksonMapper mapper) {
		this.restTemplate = restTemplate;
		this.mapper = mapper;
	}

	public ResponseBusinessDto testApi() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Content-Type", "application/json");
		ResponseEntity<Map> responseDto = restTemplate.exchange("http://localhost:8080/test/api/data",
			HttpMethod.GET,
			new HttpEntity<>(null, headers),
			Map.class
		);

		ResponseBusinessDto responseBusinessDto = mapper.getConvertValue(responseDto.getBody(),
			ResponseBusinessDto.class);
		log.info("info : " + responseBusinessDto.getInfo());
		log.info("name : " + responseBusinessDto.getName());
		return responseBusinessDto;
	}
}
