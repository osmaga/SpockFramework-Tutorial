package com.osmaga.examples.jpa.model;

import com.osmaga.examples.jpa.constants.Career;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String year;

	private Career career;

	private LocalDate dueDate;

	private Long discountFee;

	private String discountReason;

	private Long semesterFee;

}
