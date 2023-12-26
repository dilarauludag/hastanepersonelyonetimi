package com.bilgeadam.hospital.entity;

import java.time.LocalDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@MappedSuperclass // JPA da inheritance yapılmak isteniyorsa yöntemlerden biri super sınıfı @MappedSuperclass ile
// belirlemek.
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty(message="{firstName.notempty}")
	private String firstName;
	@NotEmpty(message="{lastName.notempty}")
	private String lastName;
	@NotNull(message="{birthdate.notempty}")
	@Past(message= "{birthdate.past}")
	private LocalDate birthdate;
	@NotNull(message="{gender.notempty}")
	private Gender gender;
	private String image;
	@NotEmpty(message="{mobile.notempty}")
	private String mobile;
	@NotEmpty(message="{mobile.notempty}")
	private String email;
}
