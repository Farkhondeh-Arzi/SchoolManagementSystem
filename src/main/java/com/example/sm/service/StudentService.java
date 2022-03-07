package com.example.sm.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sm.dao.StudentRepo;
import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Student;
import com.example.sm.model.StudentCourse;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StudentService implements ServiceInterface<Student> {

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Student> getAll() {

        return studentRepo.findAll();
    }

    @Override
    public Student add(Student student) {

        //set Id before encoding it
        studentRepo.save(student);
        //Set default values
        student.setUsername(String.valueOf(student.getNationalNumber()));
        student.setPassword(passwordEncoder.encode(String.valueOf(student.getId())));
        student.setRole("STUDENT");
        return studentRepo.save(student);
    }

    @Override
    public Student get(int Id) {
        Student student = studentRepo.findById(Id).orElse(null);
        if (student == null) throw new RecordNotFoundException("Not valid ID");

        return student;
    }

    @Override
    public Student update(Student student, int Id) {
        Student updatedStudent = studentRepo.findById(Id).orElse(null);
        if (updatedStudent == null) throw new RecordNotFoundException("Not valid ID");

        updatedStudent.setForename(student.getForename());
        updatedStudent.setSurname(student.getSurname());
        updatedStudent.setNationalNumber(student.getNationalNumber());
        updatedStudent.setCollege(student.getCollege());
        return add(updatedStudent);
    }

    public Student updateProfile(MultipartFile file, int Id) throws IOException {
        Student student = get(Id);
        student.setProfile(file.getBytes());
        return add(student);
    }

    @Override
    public void delete(int Id) {
        Student student = studentRepo.findById(Id).orElse(null);
        if (student == null) throw new RecordNotFoundException("Not valid ID");

        studentRepo.deleteById(Id);
    }

    public float getAverage(int Id) {

        List<StudentCourse> studentCourses = get(Id).getStudentCourses();

        float sum = 0;
        int totalUnit = 0;

        for (StudentCourse studentCourse : studentCourses) {
            totalUnit += studentCourse.getCourse().getUnit();
            sum += studentCourse.getGrade() * studentCourse.getCourse().getUnit();
        }

        return sum / totalUnit;
    }
}
