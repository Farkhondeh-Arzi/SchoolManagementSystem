package com.example.sm.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.sm.dao.FileDBRepo;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.FileDB;
import com.example.sm.model.Student;

@Service
public class FileDBService {

	@Autowired
	private FileDBRepo repo;

	public FileDB store(MultipartFile file, Student student) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
		fileDB.setStudent(student);
		return repo.save(fileDB);
	}
	
	public Stream<FileDB> getAllFiles() {
		return repo.findAll().stream();
	}
	
	public FileDB getFile(String Id) {
		FileDB file = repo.findById(Id).orElse(null);

		if (file == null)
			throw new RecordNotFoundException("Not valid ID");
		return file;
	}
}
