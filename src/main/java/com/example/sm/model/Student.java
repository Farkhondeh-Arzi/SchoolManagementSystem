package com.example.sm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Student extends User {
	@ManyToOne()
	private College college;

	@JsonIgnore
	@OneToMany(mappedBy = "sci.student")
	private List<StudentCourse> studentCourses;

	// Setters And Getters-------------------------------
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

	public void setStudentCourses(List<StudentCourse> studentCourses) {
		this.studentCourses = studentCourses;
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
