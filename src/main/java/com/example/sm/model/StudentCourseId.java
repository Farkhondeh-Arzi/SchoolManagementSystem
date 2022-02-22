package com.example.sm.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class StudentCourseId implements Serializable{

	@ManyToOne
	private Student student;
	
	@ManyToOne
	private Course course;

	// Setters And Getters-------------------------------
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		return Objects.hash(course, student);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentCourseId other = (StudentCourseId) obj;
		return Objects.equals(course, other.course) && Objects.equals(student, other.student);
	}

	@Override
	public String toString() {
		return "StudentCourseId [student=" + student.getForename() + " " + student.getSurname() + ", course=" + course.getName() + "]";
	}
}
