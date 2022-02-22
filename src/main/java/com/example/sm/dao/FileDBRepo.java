package com.example.sm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sm.model.FileDB;

@Repository
public interface FileDBRepo extends JpaRepository<FileDB, String> {

}
