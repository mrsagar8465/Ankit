package com.as.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.as.entities.LoginRequest;
import com.as.entities.User;
import com.as.exception.UserNotFoundException;
import com.as.repositories.UserRepository;
import com.as.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
        /**
     * Registers a new user by saving to the repository.
     * @param user the User entity to be saved.
     */
    @Override
    public void registerUser(User user) {
    
    	user.setRole("Userrole");
        if (user != null) {
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User cannot be null");
        }
    }

    /**
     * Logs in a user by validating credentials.
     * @param loginRequest contains username and password for login.
     * @return true if login is successful, false otherwise.
     */
    @Override
    public boolean loginUser(LoginRequest loginRequest) {
        if (loginRequest == null || 
            loginRequest.getUsername() == null || 
            loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Invalid login request");
        }

        return authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }

    /**
     * Authenticates a user by checking credentials in the repository.
     * @param username the username to authenticate.
     * @param password the password to authenticate.
     * @return true if the user exists with the given credentials, false otherwise.
     */
    @Override
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
        	UserNotFoundException ex=new UserNotFoundException("Username and password can not be null");
			throw ex;
        }

        return userRepository.existsByUsernameAndPassword(username, password);
    }

	
	public User getByUserName(String username) {
	 
		User user=userRepository.findByUsername(username);
		if(user==null) {
			UserNotFoundException ex=new UserNotFoundException("User not found by" +username);
			throw ex;
		}
		return user;
	}

	@Override
	public User getByEmail(String email) {
		User user=userRepository.findByEmail(email);
		if(user==null)
		{
			UserNotFoundException ex=new UserNotFoundException("User not found by" +email );   
			throw ex;
		}
		return user;
	}

	
	public User getById(int uid) {
		
		User user=userRepository.findById(uid);
		if(user==null)
		{
			UserNotFoundException ex=new UserNotFoundException("User are not "+uid+"found");
			throw ex;
		}
		return user;
	}

	
}
