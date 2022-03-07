package com.example.sm.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
public class MainController {

	@GetMapping("/signup")
	public Link signUp() {
		return WebMvcLinkBuilder.linkTo(CollegeController.class).withSelfRel();
	}
}
