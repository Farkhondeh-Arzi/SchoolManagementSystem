package com.example.sm.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.sm.dao.UserRepo;
import com.example.sm.model.User;

@Component("userSecurity")
public class UserSecurity {
	
	UserRepo userRepo;
	
	public UserSecurity(UserRepo userRepo) {
		this.userRepo = userRepo;
	}
	
	public boolean hasUserId(Authentication authentication, int userId) {
		
		String prinsipal = (String) authentication.getPrincipal();
		User user = userRepo.findByUsername(prinsipal);
		return user.getId() == userId;
	}
}
