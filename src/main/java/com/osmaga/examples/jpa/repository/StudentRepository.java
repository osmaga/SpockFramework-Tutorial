package com.osmaga.examples.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import com.osmaga.examples.jpa.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
