package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ParkRepository extends JpaRepository<Park, String>{
	@Query("SELECT u FROM Park u WHERE u.parkName=?1")
	public Park findByParkName(String parkName);
}
