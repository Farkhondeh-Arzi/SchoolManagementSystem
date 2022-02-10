package com.example.sm.model;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;

@Entity
@AssociationOverrides({
    @AssociationOverride(name = "sci.student", 
        joinColumns = @JoinColumn(name = "student_Id")),
    @AssociationOverride(name = "sci.course", 
        joinColumns = @JoinColumn(name = "course_Id")) })
public class StudentCourse {

	@EmbeddedId
	StudentCourseId sci = new StudentCourseId();
	
	@Column
	private float grade;
	
	// Setters And Getters-------------------------------
	
	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public StudentCourseId getSci() {
		return sci;
	}

	public void setSci(StudentCourseId sci) {
		this.sci = sci;
	}
	
	@Transient
	public Student getStudent() {
		return getSci().getStudent();
	}
	
	public void setStudent(Student student) {
		sci.setStudent(student);
	}
	
	@Transient
	public Course getCourse() {
		return getSci().getCourse();
	}
	
	public void setCourse(Course course) {
		sci.setCourse(course);
	}

	@Override
	public String toString() {
		return "StudentCourse [sci=" + sci + ", grade=" + grade + "]";
	}
}
