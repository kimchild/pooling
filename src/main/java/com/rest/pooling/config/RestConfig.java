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
	@Value("rest.template.max_conn_total")
	public static int REST_TEMPLATE_MAX_CONN_TOTAL;
	// 연결 경로별 최대 수
	@Value("rest.template.max_conn_per_route")
	public static int REST_TEMPLATE_MAX_CONN_PER_ROUTE;
	// http keep alive(Connection 연결 유지시간)
	@Value("rest.template.keep_alive.time_seconds")
	public static int KEEP_ALIVE_TIME_SECONDS;
	// 읽기시간 초과 ms
	@Value("rest.template.factory.read_timeout")
	public static int REST_TEMPLATE_FACTORY_READ_TIMEOUT;
	// 연결시간 초과 ms
	@Value("rest.template.factory.connect_timeout")
	public static int REST_TEMPLATE_FACTORY_CONNECT_TIMEOUT;

	@Bean
	RestTemplate restTemplate() {
		CloseableHttpClient httpClients = HttpClientBuilder.create()
			.setMaxConnTotal(REST_TEMPLATE_MAX_CONN_TOTAL)
			.setMaxConnPerRoute(REST_TEMPLATE_MAX_CONN_PER_ROUTE)
			.setConnectionTimeToLive(KEEP_ALIVE_TIME_SECONDS, TimeUnit.SECONDS)
			.build();

		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setReadTimeout(REST_TEMPLATE_FACTORY_READ_TIMEOUT);
		httpRequestFactory.setConnectTimeout(REST_TEMPLATE_FACTORY_CONNECT_TIMEOUT);
		httpRequestFactory.setHttpClient(httpClients);

		return new RestTemplateBuilder()
			.errorHandler(new RestResponseErrorHandler())
			.build();
	}
}
