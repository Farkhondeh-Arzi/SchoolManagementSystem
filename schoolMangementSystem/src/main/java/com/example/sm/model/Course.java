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
public class Course extends RepresentationModel<College> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "number_of_units")
	private int unit;

	@ManyToOne
	private Professor professor;

	@JsonIgnore
	@ManyToOne
	private College college;
	
	@JsonIgnore
	@OneToMany(mappedBy = "sci.course")
	private List<StudentCourse> studentCourses;

	// Setters And Getters-------------------------------
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
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
		return "Course [Id=" + Id + ", name=" + name + ", unit=" + unit + "]";
	}	
	
	@Override
	public boolean equals(Object obj) {
		Course other = (Course)obj;
		return this.Id == other.Id;
	}
}
