package com.example.sm.controller;

import com.example.sm.dto.StudentDTO;
import com.example.sm.model.Course;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import com.example.sm.service.CollegeService;
import com.example.sm.service.CourseService;
import com.example.sm.service.StudentCourseService;
import com.example.sm.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StudentController {

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
	
	@GetMapping("/colleges/{collegeId}/students")
	@PreAuthorize("hasAuthority('ADMIN')")
	public CollectionModel<Student> getAll(@PathVariable int collegeId) {
		
		List<Student> students = collegeService.getStudents(collegeId);
		
		for (Student student: students) {
			Link selfLink =  WebMvcLinkBuilder.linkTo(StudentController.class).slash("student")
					.slash(student.getId()).withSelfRel();
			student.add(selfLink);
			
			Link courseLink =  WebMvcLinkBuilder.linkTo(StudentController.class)
					.slash("student").slash(student.getId()).slash("addCourse").withRel("course");
			student.add(courseLink);
			
			Link averageLink =  WebMvcLinkBuilder.linkTo(StudentController.class).
					slash("student").slash(student.getId()).slash("average").withRel("average");
			student.add(averageLink);
		}

		Link link = WebMvcLinkBuilder.linkTo(StudentController.class).slash("colleges")
				.slash(collegeId).slash("students").withSelfRel();
		return CollectionModel.of(students, link);
	}
	
	@PostMapping("/colleges/{collegeId}/students")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Student addToStudents(@Valid @RequestBody StudentDTO studentDTO) {
		
		//the repository sets Id after adding it
		Student student = studentService.add(mapper.map(studentDTO, Student.class));

		return student;
	}
	
	@PutMapping("/colleges/{collegeId}/{studentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public Student updateStudent(@Valid @RequestBody StudentDTO studentDTO, @PathVariable int studentId) {
		return studentService.update(mapper.map(studentDTO, Student.class), studentId);
	}
	
	@DeleteMapping("/colleges/{collegeId}/{studentId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public void deleteStudent(@PathVariable int studentId) {
		studentService.delete(studentId);
	}
	
	@GetMapping("student/{studentId}")
	@PreAuthorize("hasAuthority('STUDENT') and @userSecurity.hasUserId(authentication, #studentId)")
	public Student getStudent(@PathVariable int studentId) {
		Student student = studentService.get(studentId);
		Link courseLink =  WebMvcLinkBuilder.linkTo(StudentController.class)
				.slash("student").slash(student.getId()).slash("addCourse").withRel("course");
		student.add(courseLink);
		
		Link averageLink =  WebMvcLinkBuilder.linkTo(StudentController.class).
				slash("student").slash(student.getId()).slash("average").withRel("average");
		student.add(averageLink);
		return student;
	}
	
	@PutMapping("student/{studentId}/addCourse") 
	@PreAuthorize("hasAuthority('STUDENT') and @userSecurity.hasUserId(authentication, #studentId)")
	public StudentCourse addToStudentCourses(@PathVariable int studentId, @RequestBody int courseId) {
		
		Student student = studentService.get(studentId);
		Course course = courseService.get(courseId);
		
		StudentCourse studentCourse = new StudentCourse();
		studentCourse.setStudent(student);
		studentCourse.setCourse(course);
		
		return studentCourseService.add(studentCourse);
	}
	
	@GetMapping("student/{studentId}/average")
	@PreAuthorize("hasAuthority('STUDENT') and @userSecurity.hasUserId(authentication, #studentId)")
	public float getAverage(@PathVariable int studentId) {
		
		return studentService.getAverage(studentId);
	}
}
