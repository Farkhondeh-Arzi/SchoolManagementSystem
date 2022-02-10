package com.example.sm.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import com.example.sm.model.College;
import com.example.sm.model.Professor;

public class CourseDTO {

	@Size(min = 1, message = "course name should have at least one character")
	private String name;

	@Range(min = 1, max = 10, message = "unit should be in range 1 to 10")
	private int unit;

	private Professor professor;

	private College college;

	// Setters And Getters-------------------------------
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
	
}
