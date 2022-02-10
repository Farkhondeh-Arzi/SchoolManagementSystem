package com.example.sm.controller;

import java.util.List;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.sm.dto.ProfessorDTO;
import com.example.sm.model.Course;
import com.example.sm.model.Professor;
import com.example.sm.model.Student;
import com.example.sm.service.CollegeService;
import com.example.sm.service.CourseService;
import com.example.sm.service.ProfessorService;
import com.example.sm.service.StudentCourseService;
import com.example.sm.service.StudentService;

@RestController
public class ProfessorController {

	@Autowired
	ProfessorService profService;
	
	@Autowired
	StudentService studentService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	StudentCourseService studentCourseService; 
	
	@Autowired
	CollegeService collegeService; 
	
	@Autowired
	ModelMapper mapper;

	@GetMapping("/colleges/{collegeId}/profs")
	public CollectionModel<Professor> getAll(@PathVariable int collegeId) {

		List<Professor> profs = collegeService.getProfs(collegeId);
		
		for(Professor prof: profs) {
			Link profLink = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash(prof.getPersonnelId()).withSelfRel();
			prof.add(profLink);
			
			Link profStudentsLink = WebMvcLinkBuilder.linkTo(ProfessorController.class)
					.slash("prof").slash(prof.getPersonnelId()).slash("students").withRel("students");
			prof.add(profStudentsLink);
			
			Link profCoursesLink = WebMvcLinkBuilder.linkTo(ProfessorController.class)
					.slash("prof").slash(prof.getPersonnelId()).slash("courses").withRel("courses");
			prof.add(profCoursesLink);
			
			Link gradeLink = WebMvcLinkBuilder.linkTo(ProfessorController.class)
					.slash("prof").slash(prof.getPersonnelId()).slash("setGrade").withRel("grade");
			prof.add(gradeLink);
			
			Link averageLink = WebMvcLinkBuilder.linkTo(ProfessorController.class)
					.slash("prof ").slash(prof.getPersonnelId()).slash("students").slash("average").withRel("grade");
			prof.add(averageLink);
		}
		
		Link link = WebMvcLinkBuilder.linkTo(ProfessorController.class).withSelfRel();
		CollectionModel<Professor> result = CollectionModel.of(profs, link);
		return result;
	}

	@PostMapping("/colleges/{collegeId}/profs")
	public Professor addToProfs(@Valid @RequestBody ProfessorDTO profDTO) {

		Professor prof = profService.add(mapper.map(profDTO, Professor.class));
		Link link = WebMvcLinkBuilder.linkTo(ProfessorController.class).slash(prof.getPersonnelId()).withSelfRel();
		prof.add(link); 
		return prof;
	}

	@PutMapping("/colleges/{collegeId}/profs/{Id}")
	public Professor updateProf(@RequestBody ProfessorDTO profDTO ,@PathVariable int Id) {
		return profService.update(mapper.map(profDTO, Professor.class), Id);
	}
	
	@DeleteMapping("/colleges/{collegeId}/profs/{Id}")
	public void deleteProf(@PathVariable int Id) {
		profService.delete(Id);
	}
	
	@GetMapping("prof/{profId}/students")
	public Set<Student> getStudents(@PathVariable int profId) {
		
		return profService.getStudents(profId);
	}
	
	@GetMapping("prof/{profId}/courses")
	public List<Course> getCourses(@PathVariable int profId) {
		
		return profService.getCourses(profId);
	}
	
	@PutMapping("prof/{profId}/setGrade")
	public void setGrade(@RequestBody List<Integer> details) {
		
		int studentId = details.get(0); 
		int courseId = details.get(1); 
		int grade = details.get(2); 
		studentCourseService.updateGrade(studentService.get(studentId), courseService.get(courseId), grade);
	}
	
	@GetMapping("prof/{Id}/students/average")
	public float getStudentsAverage(@PathVariable int Id) {
		return profService.getStudentsAverage(Id);
	}
}
