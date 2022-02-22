
package com.example.sm.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.sm.model.Professor;
import com.example.sm.model.Student;
import com.example.sm.model.User;

@Repository
public class UserRepo {

	private StudentRepo studentRepo;
	private ProfessorRepo profRepo;
	private AdminRepo adminRepo;
	
	public UserRepo(StudentRepo studentRepo, ProfessorRepo profRepo, AdminRepo adminRepo) {
		this.studentRepo = studentRepo;
		this.profRepo = profRepo;
		this.adminRepo = adminRepo;
	}

	public User findByUsername(String username) {
		Student student = studentRepo.findByUsername(username);
		if (student != null) return student;
		Professor prof = profRepo.findByUsername(username);
		if (prof != null) return prof;
		return adminRepo.findByUsername(username);
	}

	public List<User> findAll() {

		List<User> users = new ArrayList<>();
		users.addAll(studentRepo.findAll());
		users.addAll(profRepo.findAll());
		return users;
	}
}
