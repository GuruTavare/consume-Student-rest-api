package com.prowings.myapp.Interceptor;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class ModifyHeadersInResponse implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		
		System.out.println(">>> inside Header Modifier Interceptor!!!");
		ClientHttpResponse response = execution.execute(request, body);
		
		response.getHeaders().add("xxx123", "aaaa123");
		
		return response;
	}

}
