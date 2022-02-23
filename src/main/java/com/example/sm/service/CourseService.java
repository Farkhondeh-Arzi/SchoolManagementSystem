package com.example.sm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sm.dao.CourseRepo;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Course;

@Service
public class CourseService implements ServiceInterface<Course>{

	@Autowired
	CourseRepo courseRepo;
	
	@Override
	public List<Course> getAll() {
	
		return courseRepo.findAll();
	}
	
	@Override
	public Course add(Course course) {
		return courseRepo.save(course);
	}

	@Override
	public Course get(int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");
		return course;
	}

	@Override
	public Course update(Course course, int Id) {
		Course updatedCourse = courseRepo.findById(Id).orElse(null);
		if (updatedCourse == null) throw new RecordNotFoundException("Not valid ID");
		updatedCourse.setName(course.getName());
		updatedCourse.setUnit(course.getUnit());
		updatedCourse.setCollege(course.getCollege());
		updatedCourse.setProfessor(course.getProfessor());
		return add(updatedCourse);
	}

	@Override
	public void delete(int Id) {
		Course course = courseRepo.findById(Id).orElse(null);
		if (course == null) throw new RecordNotFoundException("Not valid ID");
		courseRepo.deleteById(Id);
	}
}
