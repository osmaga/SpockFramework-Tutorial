package com.osmaga.examples.jpa.service;

import com.osmaga.examples.jpa.constants.Career;
import com.osmaga.examples.jpa.model.Student;

import java.time.LocalDate;

public interface StudentService {

    Long getSemesterFee(Career career, LocalDate dueDate);

    Long getSemesterFee(Student student);

    Iterable<Student> findAll();

    Student getStudent(Long id);

    Student addStudent(Student student);

    void deleteById(Long id);

    Student updateStudentCareer(Long id, Career career);
}
