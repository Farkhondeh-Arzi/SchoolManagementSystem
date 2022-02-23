package com.example.sm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer>{

	Admin findByUsername(String username);
}