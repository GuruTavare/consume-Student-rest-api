package com.prowings.myapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.prowings.myapp.model.Student;

public interface RestApiConsumeService {
	
	public ResponseEntity<List> getStds();

	public ResponseEntity<String> createStudent(Student student);

	public ResponseEntity<List<Student>> searchStudentByCity(String city);
	

}
