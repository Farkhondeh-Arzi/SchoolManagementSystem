package com.example.sm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer>{

}
