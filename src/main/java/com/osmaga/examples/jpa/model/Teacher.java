package com.osmaga.examples.jpa.model;

import com.osmaga.examples.jpa.constants.Country;
import com.osmaga.examples.jpa.constants.Subject;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	
	private String lastName;

	@ElementCollection(targetClass = Subject.class)
	@Enumerated(EnumType.STRING)
	private Collection<Subject> subjectsTaught;

	private Country country;
}
