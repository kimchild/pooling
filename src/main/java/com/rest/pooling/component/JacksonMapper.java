package com.rest.pooling.component;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JacksonMapper<T> {
	private final ObjectMapper mapper;

	public JacksonMapper(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public T getConvertValue(Object body, Class<T> contentClass) {
		return mapper.convertValue(body, contentClass);
	}
}
