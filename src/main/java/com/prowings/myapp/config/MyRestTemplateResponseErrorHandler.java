package com.prowings.myapp.config;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.prowings.myapp.exception.NotFoundException;

public class MyRestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		
		return response.getStatusCode().is5xxServerError() || response.getStatusCode().is4xxClientError();
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		 if (response.getStatusCode().is5xxServerError()) {
	            //Handle SERVER_ERROR
	            throw new HttpClientErrorException(response.getStatusCode());
	        } else if (response.getStatusCode().is4xxClientError()) {
	            //Handle CLIENT_ERROR
	            if (response.getStatusCode() == HttpStatus.METHOD_NOT_ALLOWED) {
	                throw new NotFoundException("Method not allowed on target api!!");
	            }
	            else  if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
	                throw new NotFoundException("Bad Request on target api!!");
	            }
	        }
	}
}
