package com.example.sm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Professor extends RepresentationModel<College> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int personnelId;
	
	@Column(name = "first_name")
	private String forename;

	@Column(name = "last_name")
	private String surname;

	@Column(name = "national_number", unique = true)
	private long nationalNumber;
	
	@JsonIgnore
	@OneToMany(mappedBy = "professor")
	private List<Course> courses;
	
	@ManyToOne
	private College college;

	public Professor() {

	}

	// Setters And Getters-------------------------------
	public int getPersonnelId() {
		return personnelId;
	}

	public void setPersonnelId(int personnelId) {
		this.personnelId = personnelId;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String profForename) {
		this.forename = profForename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String profSurname) {
		this.surname = profSurname;
	}

	public long getNationalNumber() {
		return nationalNumber;
	}

	public void setNationalNumber(long profNationalNumber) {
		this.nationalNumber = profNationalNumber;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	@Override
	public String toString() {
		return "Professor [personnelId=" + personnelId + ", forename=" + forename + ", surname=" + surname
				+ ", nationalNumber=" + nationalNumber + ", college=" + college.getName() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		Professor other = (Professor)obj;
		return this.personnelId == other.personnelId;
	}
}

