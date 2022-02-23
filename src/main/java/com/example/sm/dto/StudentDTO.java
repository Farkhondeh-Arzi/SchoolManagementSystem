package com.example.sm.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.example.sm.model.College;

public class StudentDTO {

	@NotEmpty(message = "forename could not be empty")
	@Size(min = 1, max = 85, message = "forename should have at least 1 character")
	private String forename;

	@NotEmpty(message = "surname could not be empty")
	@Size(min = 1, max = 85, message = "surname should have at least 1 character")
	private String surname;

	@Range(min = 1, max = 9999999999l)
	private long nationalNumber;

	private College college;

	// Setters And Getters-------------------------------
	
	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getNationalNumber() {
		return nationalNumber;
	}

	public void setNationalNumber(long nationalNumber) {
		this.nationalNumber = nationalNumber;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}
}
