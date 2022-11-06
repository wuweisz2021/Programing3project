package com.example.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderRepository extends JpaRepository<Order, Long>{
	
//	@Query("SELECT u FROM Order u WHERE u.payment=?1")
//	public Park findBypayment(String payment);
	
}
