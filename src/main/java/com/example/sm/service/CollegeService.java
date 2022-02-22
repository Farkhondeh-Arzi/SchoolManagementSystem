package com.example.sm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sm.dao.CollegeRepo;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.College;
import com.example.sm.model.Course;
import com.example.sm.model.Professor;
import com.example.sm.model.Student;

@Service
public class CollegeService implements ServiceInterface<College> {

	@Autowired
	CollegeRepo collegeRepo;

	@Override
	public List<College> getAll() {

		return collegeRepo.findAll();
	}

	@Override
	public College add(College college) {
		return collegeRepo.save(college);
	}

	@Override
	public College get(int Id) {
		College college = collegeRepo.findById(Id).orElse(null);
		if (college == null)
			throw new RecordNotFoundException("Not valid ID");
		return college;
	}

	@Override
	public College update(College college, int Id) {
		College updatedCollege = collegeRepo.findById(Id).orElse(null);
		if (updatedCollege == null)
			throw new RecordNotFoundException("Not valid ID");
		updatedCollege.setName(college.getName());
		return add(updatedCollege);
	}

	@Override
	public void delete(int Id) {
		College college = collegeRepo.findById(Id).orElse(null);
		if (college == null)
			throw new RecordNotFoundException("Not valid ID");
		collegeRepo.deleteById(Id);
	}
	
	public List<Course> getCourses(int Id) {
		return get(Id).getCourses();
	}
	
	public List<Student> getStudents(int Id) {
		return get(Id).getStudents();
	}
	
	public List<Professor> getProfs(int Id) {
		return get(Id).getProfessors();
	}

	/*@Override
	public UserDetails loadUserByUsername(String collegeName) throws UsernameNotFoundException {

		Optional<College> opt = collegeRepo.findByName(collegeName);
		User user = null;

		if (opt.isEmpty()) {
			throw new UsernameNotFoundException("College not found");
		}

		College college = opt.get();
		List<String> roles = college.getRoles();

		Set<GrantedAuthority> ga = new HashSet<>();
		for (String role : roles) {
			ga.add(new SimpleGrantedAuthority(role));
		}

		user = new User(collegeName, String.valueOf(college.getId()), ga);

		return user;
	}*/
}
