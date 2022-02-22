package com.example.sm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sm.dao.StudentCourseRepo;
import com.example.sm.model.Course;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import com.example.sm.model.StudentCourseId;

@Service
public class StudentCourseService implements ServiceInterface<StudentCourse> {

	@Autowired
	StudentCourseRepo studentCourseRepo;

	@Override
	public List<StudentCourse> getAll() {

		return studentCourseRepo.findAll();
	}

	@Override
	public StudentCourse add(StudentCourse studentCourse) {
		return studentCourseRepo.save(studentCourse);
	}

	public StudentCourse getByStudentCourseId(StudentCourseId stuentCourseId) {
		return studentCourseRepo.findById(stuentCourseId).get();
	}

	@Override
	public StudentCourse get(int Id) {
		return null;
	}

	@Override
	public StudentCourse update(StudentCourse studentCourse, int Id) {
		return add(studentCourse);
	}

	@Override
	public void delete(int Id) {
		// studentCourseRepo.deleteById(Id);
	}

	public StudentCourse updateGrade(Student student, Course course, float grade) {

		StudentCourseId sci = new StudentCourseId();
		sci.setCourse(course);
		sci.setStudent(student);

		StudentCourse studentCourse = getByStudentCourseId(sci);

		studentCourse.setGrade(grade);

		return add(studentCourse);
	}
}
