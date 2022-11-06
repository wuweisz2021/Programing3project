package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserRepository extends JpaRepository<User, String>{
	@Query("SELECT u FROM User u WHERE u.email=?1")
	public User findByEmail(String email);
}
