package com.prowings.myapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.prowings.myapp.model.Student;

@Service
public class RestApiConsumeServiceImpl implements RestApiConsumeService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	Environment environment;

	@Override
	public ResponseEntity<List> getStds() {

		System.out.println(">>>> student service :: getStd() started!!");
		// call to rest api

		// System.out.println(environment.getProperty("base_url"));
		// String uri =
		// "http://localhost:8080/Student_REST_API_using_Spring_MVC/students";

//		Student fetchedStd = restTemplate.getForObject(uri, Student.class);

		ResponseEntity<List> fetchedStd = restTemplate.getForEntity("/students", List.class);

		System.out.println(">>> received Std from REST Api : ");
		System.out.println(">>> Response Body : " + fetchedStd.getBody());
		System.out.println(">>> Response Status : " + fetchedStd.getStatusCodeValue());

		return fetchedStd;
	}

	@Override
	public ResponseEntity<String> createStudent(Student student) {
		System.out.println(">>>> student service :: createStudent() started!!");
		// call to rest api

		// S System.out.println(">>>>>>>>>>>>>>>>>><<<<<<<<<<<" +
		// environment.getProperty(url));

		String uri = "http://localhost:8080/Student_REST_API_using_Spring_MVC/students";

		ResponseEntity<String> fetchedStd = restTemplate.postForEntity(uri, student, String.class);

		// return new ResponseEntity<String>(fetchedStd, HttpStatus.CREATED);
		return fetchedStd;
	}

	@Override
	public ResponseEntity<List<Student>> searchStudentByCity(String city) {
		// "/students/search?city="+city
//		ResponseEntity<List<Student>> responseEntity = null;
//		try {
		ResponseEntity<List<Student>>	responseEntity = restTemplate.exchange("/students/search?city=" + city, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Student>>() {
					});

			System.out.println(responseEntity.getBody());
			System.out.println(responseEntity.getStatusCode());
//		} catch (HttpClientErrorException e) {
//			System.out.println("client error 4xx");
//		} catch (HttpServerErrorException e) {
//			System.out.println("server error 5xx");33
			
//		} catch (UnknownHttpStatusCodeException e) {
//			System.out.println("Unknown error");
//		}
		return responseEntity; 
	}

}
