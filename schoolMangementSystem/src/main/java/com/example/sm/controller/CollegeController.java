package com.example.sm.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sm.dto.CollegeDTO;
import com.example.sm.model.College;
import com.example.sm.service.CollegeService;

@RestController
@RequestMapping("/colleges")
public class CollegeController {

	@Autowired
	CollegeService collegeService;

	@Autowired
	ModelMapper mapper;

	@GetMapping()
	public CollectionModel<College> getAllColleges() {

		List<College> colleges = collegeService.getAll();

		for (College college : colleges) {
			
			Link selfLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId()).withSelfRel();
			college.add(selfLink);
			
			Link studentLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId())
					.slash("students").withRel("students");
			college.add(studentLink);
			
			Link profLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId())
					.slash("profs").withRel("professors");
			college.add(profLink);
			
			Link courseLink = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId())
					.slash("courses").withRel("courses");
			college.add(courseLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(CollegeController.class).withSelfRel();
		CollectionModel<College> result = CollectionModel.of(colleges, link);
		return result;
	}

	@PostMapping()
	public College addToCollegs(@Valid @RequestBody CollegeDTO collegeDTO) {

		College college = collegeService.add(mapper.map(collegeDTO, College.class));
		Link link = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId()).withSelfRel();
		college.add(link);
		return college;
	}

	@PutMapping("/{Id}")
	public College updateCollege(@Valid @RequestBody CollegeDTO collegeDTO, @PathVariable int Id) {

		College college = collegeService.update(mapper.map(collegeDTO, College.class), Id);
		Link link = WebMvcLinkBuilder.linkTo(CollegeController.class).slash(college.getId()).withSelfRel();
		college.add(link);
		return college;
	}

	@DeleteMapping("/{Id}")
	public void deleteCollege(@PathVariable int Id) {
		collegeService.delete(Id);
	}
}
