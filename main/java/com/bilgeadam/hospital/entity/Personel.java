package com.bilgeadam.hospital.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import lombok.Data;

@Entity
@Table(name="personnel")
@Data
public class Personel extends Person{
	
	
	private Department department;
	private Title title;
	@NotNull(message="{startDate.notempty}")
	@Past(message= "{startDate.past}")
	private LocalDate startDate;
	@Past(message= "{endDate.past}")
	private LocalDate endDate;
}
