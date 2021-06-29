package com.rest.pooling.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
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

	public ApiService(RestTemplate restTemplate, @SuppressWarnings("rawtypes") JacksonMapper mapper) {
		this.restTemplate = restTemplate;
		//noinspection unchecked
		this.mapper = mapper;
	}

	@Cacheable("test-api")
	public ResponseBusinessDto testApi() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Content-Type", "application/json");
		@SuppressWarnings("rawtypes")
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

	public String read() {
		ClassPathResource resource = new ClassPathResource("pem/iv_pem.pem");
		StringBuilder readPemText = new StringBuilder();
		try (BufferedInputStream bf = new BufferedInputStream(resource.getInputStream());
			 BufferedReader r = new BufferedReader(
				 new InputStreamReader(bf, StandardCharsets.UTF_8)
			 )
		) {
			String line;
			while ((line = r.readLine()) != null) {
				readPemText.append(line);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return readPemText.toString();
	}
}
