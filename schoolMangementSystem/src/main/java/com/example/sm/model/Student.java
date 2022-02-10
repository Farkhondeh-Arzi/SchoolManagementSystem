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
public class Student extends RepresentationModel<College> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(name = "first_name")
	private String forename;

	@Column(name = "last_name")
	private String surname;

	@Column(name = "national_number", unique = true)
	private long nationalNumber;

	@ManyToOne()
	private College college;

	@JsonIgnore
	@OneToMany(mappedBy = "sci.student")
	private List<StudentCourse> studentCourses;

	// Setters And Getters-------------------------------
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

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

	public List<StudentCourse> getStudentCourses() {
		return studentCourses;
	}

	public void setStudentCourses(StudentCourse studentCourse) {
		this.studentCourses.add(studentCourse);
	}

	@Override
	public String toString() {
		return "Student [Id=" + Id + ", forename=" + forename + ", surname=" + surname + ", nationalNumber="
				+ nationalNumber + ", college=" + college.getName() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		Student other = (Student)(obj);
		return this.Id == other.Id;
	}
}
