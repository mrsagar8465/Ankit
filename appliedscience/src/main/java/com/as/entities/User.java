 package com.as.entities;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name="userregister")
public class User {

	@Id
	@GeneratedValue(generator = "userseq")
	@SequenceGenerator(name="userseq" ,initialValue = 101,allocationSize = 1)
	private int id;
	@NotNull(message="username is required")
	private String username;
	
	private String contactnumber;
	@NotNull(message="email is required")
	@Column(unique = true)   
	private String email;
	@NotNull(message="password is required")
	private String password;
	
	private String confirmpassword;
	
	private String profilepic;
	
	private String gender;
	private String role;
	
}
