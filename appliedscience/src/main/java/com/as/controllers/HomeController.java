package com.as.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.as.entities.LoginRequest;
import com.as.entities.LoginResponse;
import com.as.entities.User;
import com.as.repositories.UserRepository;
import com.as.services.UserService;

import ch.qos.logback.core.status.Status;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@ResponseBody
@RequestMapping("/user")

public class HomeController {

	@Autowired
	private UserService userService;
	
	
	

	

	
	@PostMapping("/add")
	public ResponseEntity<User> adduser(@Valid @RequestBody User user){
		userService.registerUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
		
	}
	@GetMapping("/details/byname/{username}")
	public ResponseEntity<User> getUserByName(@RequestParam String username)
	{
		User user =userService.getByUserName(username);
		return ResponseEntity.ok(user);
	}
	@GetMapping("/details/by/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable  String email)
	{
		User user=userService.getByEmail(email);
		return ResponseEntity.ok(user);
	}
	@GetMapping("/details/byid/{uid}")
	public ResponseEntity<User> getUserById(@PathVariable  int uid)
	{
		User user=userService.getById(uid);
		return ResponseEntity.ok(user);
	}
	
	
	
	@PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok(new LoginResponse("Login successful", true));
        } else {
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", false));
        }
    }
	
	
	
	
	
	

}
