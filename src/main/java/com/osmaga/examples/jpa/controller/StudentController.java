package com.osmaga.examples.jpa.controller;

import com.osmaga.examples.jpa.constants.Career;
import com.osmaga.examples.jpa.model.Student;
import com.osmaga.examples.jpa.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("students")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {
	
	private final StudentService studentService;

	@GetMapping
	public Iterable<Student> getStudents() {
		return studentService.findAll();
	}	

	@GetMapping("{id}")
	public Student getStudent(@PathVariable Long id) {
		return studentService.getStudent(id);
	}

	@PostMapping
	public Student addStudent(@RequestBody Student student) {
		return studentService.addStudent(student);
	}

	@PutMapping("{id}/{career}")
	public Student updateStudentCareer(@PathVariable Long id, @PathVariable Career career) {
		return studentService.updateStudentCareer(id, career);
	}

	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Long id) {
		studentService.deleteById(id);
	}
}
