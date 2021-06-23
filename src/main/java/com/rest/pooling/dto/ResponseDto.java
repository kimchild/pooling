package com.rest.pooling.dto;

import java.util.Map;

public class ResponseDto {
	int status;
	Map body;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map getBody() {
		return body;
	}

	public void setBody(Map body) {
		this.body = body;
	}

}
