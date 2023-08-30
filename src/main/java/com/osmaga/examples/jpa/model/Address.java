package com.osmaga.examples.jpa.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	private long userId;
	
	private String street;

	private String number;

	private String phone;
}
