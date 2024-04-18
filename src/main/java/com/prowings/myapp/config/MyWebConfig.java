package com.prowings.myapp.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.prowings.myapp.Interceptor.ModifyHeadersInResponse;
import com.prowings.myapp.Interceptor.RequestResponseLoggingInterceptor;

import lombok.Getter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.prowings.myapp")
@PropertySource("classpath:application1.properties")
@Getter
public class MyWebConfig implements WebMvcConfigurer {

	@Value("${base_url}")
	private String base_url;
	
	@Value("${timeout}")
	int requestTimeout;

//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
	
	
	//customHttpClient
	@Bean
	public CloseableHttpClient httpClient() {
		return HttpClients.createDefault();
	}
	

	@Bean
	public RestTemplate restTemplate() {
//		return new RestTemplate();
		
		// instead of clientHttpRequestFactory() use bufferingClientHttpRequestFactory()(for multiple request) as first one give stream closed error. 
		RestTemplate restTemplate = new RestTemplate(bufferingClientHttpRequestFactory());
		//setting Base URL
		restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(base_url));
		//setting Error Handler
		restTemplate.setErrorHandler(new MyRestTemplateResponseErrorHandler());
		//setting list of interceptor
		List<ClientHttpRequestInterceptor> interceptor= new ArrayList<>();
		interceptor.add(new RequestResponseLoggingInterceptor());
		interceptor.add(new ModifyHeadersInResponse());
		restTemplate.setInterceptors(interceptor);
		
		return restTemplate;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient());
//		 clientHttpRequestFactory.setConnectionRequestTimeout(requestTimeout);
		// clientHttpRequestFactory.setConnectTimeout(requestTimeout);
		// clientHttpRequestFactory.setReadTimeout(requestTimeout);

		return clientHttpRequestFactory;
	}
	private BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory() {

		return new BufferingClientHttpRequestFactory(clientHttpRequestFactory());

	}
}
