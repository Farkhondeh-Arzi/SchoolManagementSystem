package com.example.sm.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CollegeDTO {

	@NotEmpty(message = "name must not be null")
	@Size(min = 2, message = "college name should have at least 2 characters")
	private String name;

	// Setters And Getters-------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
