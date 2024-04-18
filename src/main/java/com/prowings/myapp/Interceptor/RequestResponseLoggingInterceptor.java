package com.prowings.myapp.Interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		printRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		printResponse(response);
		return response;
	}

	private void printRequest(HttpRequest request, byte[] body) throws UnsupportedEncodingException {

		System.out.println("===========================request begin================================================");
		System.out.println("URI         : {}" + request.getURI());
		System.out.println("Method      : {}" + request.getMethod());
		System.out.println("Headers     : {}" + request.getHeaders());
		String convertedBody = new String(body, "UTF-8");
		System.out.println("Request body: {}" + convertedBody);
		System.out.println("==========================request end================================================");
	}

	private void printResponse(ClientHttpResponse response) throws IOException {
		System.out.println("============================response begin==========================================");
		System.out.println("Status code  : {}" + response.getStatusCode());
		System.out.println("Status text  : {}" + response.getStatusText());
		System.out.println("Headers      : {}" + response.getHeaders());
		System.out
				.println("Response body: {}" + StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
		System.out.println("=======================response end=================================================");
	}

}
