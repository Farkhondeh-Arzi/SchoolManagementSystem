package com.example.sm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.Professor;

@Repository
public interface ProfessorRepo extends JpaRepository<Professor, Integer>{

}
