package com.example.sm.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Professor extends User {
	
	@JsonIgnore
	@OneToMany(mappedBy = "professor")
	private List<Course> courses;
	
	@ManyToOne
	private College college;

	public Professor() {

	}

	// Setters And Getters-------------------------------

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
		return "Professor [personnelId=" + Id + ", forename=" + forename + ", surname=" + surname
				+ ", nationalNumber=" + nationalNumber + ", college=" + college.getName() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		Professor other = (Professor)obj;
		return this.Id == other.Id;
	}
}

