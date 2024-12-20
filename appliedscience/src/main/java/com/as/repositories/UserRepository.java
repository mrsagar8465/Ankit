package com.as.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.as.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByUsernameAndPassword(String username, String password);

	 User findByUsername(String username);
	 User findByEmail(String email);
     User findById(int uid);

}
