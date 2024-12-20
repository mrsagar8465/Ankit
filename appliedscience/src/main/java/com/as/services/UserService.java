package com.as.services;

import com.as.entities.LoginRequest;
import com.as.entities.User;

public interface UserService {

	public void registerUser(User user);
    public boolean loginUser(LoginRequest loginRequest);
	public boolean authenticate(String username, String password);
	 User getByUserName(String username);
	 User getByEmail(String email);
	public User getById(int uid);
	
	
	

}
