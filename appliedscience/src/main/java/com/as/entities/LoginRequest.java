package com.as.entities;

import org.springframework.beans.factory.annotation.Autowired;

import com.as.repositories.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginRequest {
	@Autowired
	private UserRepository userRepository;

	private String username;
	private String password;
	
}
