package com.example.sm.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sm.dao.ProfessorRepo;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Course;
import com.example.sm.model.Professor;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfessorService implements ServiceInterface<Professor>{

	@Autowired
	ProfessorRepo profRepo;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public List<Professor> getAll() {
	
		return profRepo.findAll();
	}
	
	@Override
	public Professor add(Professor prof) {
		profRepo.save(prof);
		prof.setUsername(String.valueOf(prof.getNationalNumber()));
		prof.setPassword(passwordEncoder.encode(String.valueOf(prof.getId())));
		prof.setRole("PROFESSOR");
		return profRepo.save(prof);
	}

	@Override
	public Professor get(int Id) {
		Professor professor = profRepo.findById(Id).orElse(null);
		if (professor == null) throw new RecordNotFoundException("Not valid ID");
		
		return professor;
	}
	
	@Override
	public Professor update(Professor prof, int Id) {
		Professor updatedProf = profRepo.findById(Id).orElse(null);
		if (updatedProf == null) throw new RecordNotFoundException("Not valid ID");
		
		updatedProf.setForename(prof.getForename());
		updatedProf.setSurname(prof.getSurname());
		updatedProf.setNationalNumber(prof.getNationalNumber());
		updatedProf.setCollege(prof.getCollege());
		return add(updatedProf);
	}

	public Professor updateProfile(MultipartFile file, int Id) throws IOException {
		Professor professor = get(Id);
		professor.setProfile(file.getBytes());
		return add(professor);
	}
	
	@Override
	public void delete(int Id) {
		Professor professor = profRepo.findById(Id).orElse(null);
		if (professor == null) throw new RecordNotFoundException("Not valid ID");
		
		profRepo.deleteById(Id);
	}
	
	public Set<Student> getStudents(int Id) {
		
		Set<Student> profStudents = new HashSet<>();
		List<Course> courses = getCourses(Id);
		
		for (Course course : courses) {

			List<StudentCourse> studentCourses = course.getStudentCourses();
			
			for (StudentCourse studentCourse : studentCourses) {
				profStudents.add(studentCourse.getStudent());
			}
		}
		
		return profStudents;
	}

	public List<Course> getCourses(int profId) {
		return get(profId).getCourses();
	}
	
	public float getStudentsAverage(int Id) {
		
		Set<Student> students =  getStudents(Id);
		
		float totalSum = 0;
		
		for (Student student: students) {
			List<StudentCourse> studentCourses = student.getStudentCourses();
			
			float sum = 0;
			int totalUnit = 0;
			
			for (StudentCourse studentCourse : studentCourses) {
				totalUnit += studentCourse.getCourse().getUnit();
				sum += studentCourse.getGrade() * studentCourse.getCourse().getUnit();
			}
			
			totalSum += sum / totalUnit;
		}
		
		return totalSum / students.size();
	}
}
