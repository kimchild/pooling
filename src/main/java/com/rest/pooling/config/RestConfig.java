package com.rest.pooling.config;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.rest.pooling.exception.RestResponseErrorHandler;

@Configuration
public class RestConfig {

	// IP, PORT 하나당 동시 연결 Connection 최대 수
	@Value("${rest-template.max-conn-total}")
	private int restTemplateMaxConnTotal;
	// 연결 경로별 최대 수
	@Value("${rest-template.max-conn-per-route}")
	private int restTemplateMaxConnPerRoute;
	// http keep alive(Connection 연결 유지시간)
	@Value("${rest-template.keep-alive.time-seconds}")
	private int keepAliveTimeSeconds;
	// 읽기시간 초과 ms
	@Value("${rest-template.factory.read-timeout}")
	private int restTemplateFactoryReadTimeout;
	// 연결시간 초과 ms
	@Value("${rest-template.factory.connect-timeout}")
	private int restTemplateFactoryConnectTimeout;

	// @Qualifier("restTemplate")
	// @Bean
	// RestTemplate restTemplate() {
	// 	return new RestTemplate();
	// }

	// @Qualifier("restTemplatePool")
	@Bean
	RestTemplate restTemplatePool() {
		CloseableHttpClient httpClients = HttpClientBuilder.create()
			.setMaxConnTotal(restTemplateMaxConnTotal)
			.setMaxConnPerRoute(restTemplateMaxConnPerRoute)
			.setConnectionTimeToLive(keepAliveTimeSeconds, TimeUnit.SECONDS)
			.build();

		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setReadTimeout(restTemplateFactoryReadTimeout);
		httpRequestFactory.setConnectTimeout(restTemplateFactoryConnectTimeout);
		httpRequestFactory.setHttpClient(httpClients);

		return new RestTemplateBuilder()
			.errorHandler(new RestResponseErrorHandler())
			.build();
	}
}
