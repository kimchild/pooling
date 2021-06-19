package com.rest.pooling.exception;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

// @Slf4j
public class RestResponseErrorHandler extends DefaultResponseErrorHandler {
	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		// log.error("Has error response: {}", response);
		return super.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		// log.error("Has error response: {}", response);
		super.handleError(response);
	}
}
