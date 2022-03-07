package com.example.sm.service;

import com.example.sm.exception.RecordNotFoundException;
import com.example.sm.model.Professor;
import com.example.sm.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sm.dao.UserRepo;
import com.example.sm.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    StudentService studentService;

    @Autowired
    ProfessorService professorService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return user;
    }

    public User get(int Id) {
        User user = userRepo.findById(Id);
        if (user == null) {
            throw new RecordNotFoundException("Not valid ID");
        }
        return user;
    }

    public User updateProfile(MultipartFile file, int Id) throws IOException {

        String name = file.getOriginalFilename();
        String suffix = name != null ? name.substring(name.lastIndexOf('.')) : null;
        if (!((suffix != null && suffix.equals("jpg")) || (suffix != null && suffix.equals("png")))) {
            throw new IOException("format is not supported");
        }
        User user = get(Id);
        if (user instanceof Student) {
            user = studentService.updateProfile(file, Id);
        } else if (user instanceof Professor) {
            user = professorService.updateProfile(file, Id);
        }
        return user;
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }
}
