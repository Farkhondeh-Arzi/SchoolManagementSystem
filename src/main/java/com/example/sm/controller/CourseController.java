package com.example.sm.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.sm.dto.CourseDTO;
import com.example.sm.model.Course;
import com.example.sm.service.CollegeService;
import com.example.sm.service.CourseService;

@RestController
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {

	@Autowired
	CourseService courseService;

	@Autowired
	CollegeService collegeService;

	@Autowired
	ModelMapper mapper;

	@GetMapping("/colleges/{collegeId}/courses")
	public CollectionModel<Course> getAll(@PathVariable int collegeId) {

		List<Course> courses = collegeService.getCourses(collegeId);
		
		for (Course course : courses) {
			Link selfLink = WebMvcLinkBuilder.linkTo(CourseController.class)
					.slash("colleges").slash(collegeId).slash("courses").slash(course.getId()).withSelfRel();
			course.add(selfLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(CourseController.class)
				.slash("colleges").slash(collegeId).slash("courses").withSelfRel();
		CollectionModel<Course> result = CollectionModel.of(courses, link);
		return result;
	}

	@PostMapping("/colleges/{collegeId}/courses")
	public Course addToCourses(@PathVariable int collegeId, @Valid @RequestBody CourseDTO courseDTO) {

		return courseService.add(mapper.map(courseDTO, Course.class));
	}

	@PutMapping("/colleges/{collegeId}/courses/{courseId}")
	public Course updateCourse(@PathVariable int Id, @RequestBody CourseDTO courseDTO, @PathVariable int courseId) {
		return courseService.update(mapper.map(courseDTO, Course.class), courseId);
	}

	@DeleteMapping("/colleges/{collegeId}/courses/{courseId}")
	public void deleteCourse(@PathVariable int Id, @PathVariable int courseId) {
		courseService.delete(courseId);
	}
}
