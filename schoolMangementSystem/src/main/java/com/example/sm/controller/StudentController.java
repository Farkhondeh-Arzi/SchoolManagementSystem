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
import org.springframework.web.bind.annotation.RestController;

import com.example.sm.dto.StudentDTO;
import com.example.sm.model.Course;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import com.example.sm.service.CollegeService;
import com.example.sm.service.CourseService;
import com.example.sm.service.ProfessorService;
import com.example.sm.service.StudentCourseService;
import com.example.sm.service.StudentService;

@RestController
public class StudentController {

	@Autowired
	StudentService studentService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	ProfessorService professorService;
	
	@Autowired
	StudentCourseService studentCourseService;
	
	@Autowired
	CollegeService collegeService;
	
	@Autowired
	ModelMapper mapper;
	
	@GetMapping("/colleges/{collegeId}/students")
	public CollectionModel<Student> getAll(@PathVariable int collegeId) {
		
		List<Student> students = collegeService.getStudents(collegeId);
		
		for (Student student: students) {
			Link selfLink =  WebMvcLinkBuilder.linkTo(StudentController.class).slash(student.getId()).withSelfRel();
			student.add(selfLink);
			
			Link courseLink =  WebMvcLinkBuilder.linkTo(StudentController.class)
					.slash("student").slash(student.getId()).slash("addCourse").withRel("course");
			student.add(courseLink);
			
			Link averageLink =  WebMvcLinkBuilder.linkTo(StudentController.class).
					slash("student").slash(student.getId()).slash("average").withRel("average");
			student.add(averageLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(StudentController.class).withSelfRel();
		CollectionModel<Student> result = CollectionModel.of(students, link);
		return result;
	}

	@PostMapping("/colleges/{collegeId}/students")
	public Student addToStudents(@Valid @RequestBody StudentDTO studentDTO) {
		
		return studentService.add(mapper.map(studentDTO, Student.class));
	}
	
	@PutMapping("/colleges/{collegeId}/{courseId}")
	public Student updateStudent(@Valid @RequestBody StudentDTO studentDTO, @PathVariable int courseId) {
		return studentService.update(mapper.map(studentDTO, Student.class), courseId);
	}
	
	@DeleteMapping("/colleges/{collegeId}/{courseId}")
	public void deleteStudent(@PathVariable int Id) {
		studentService.delete(Id);
	}
	
	@PutMapping("student/{studentId}/addCourse") 
	public StudentCourse addToStudentCourses(@PathVariable int studentId, @RequestBody int courseId) {
		
		Student student = studentService.get(studentId);
		Course course = courseService.get(courseId);
		
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setStudent(student);
		studentCourse.setCourse(course);
		
		return studentCourseService.add(studentCourse);
	}
	
	@GetMapping("student/{studentId}/average")
	public float getAverage(@PathVariable int studentId) {
		
		return studentService.getAverage(studentId);
	}
}
