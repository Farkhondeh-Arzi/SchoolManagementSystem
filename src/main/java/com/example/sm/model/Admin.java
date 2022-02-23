package com.example.sm.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Admin extends User {
	
	@Column(name = "first_name")
	private String forename;

	@Column(name = "last_name")
	private String surname;

	@Column(name = "national_number", unique = true)
	private long nationalNumber;
	
	//Setter and Getter
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
	
	@Override
	public boolean equals(Object obj) {
		Admin other = (Admin)obj;
		return this.Id == other.Id;
	}
}
