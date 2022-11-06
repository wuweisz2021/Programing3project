package com.example.login;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class OrderService {
	
	@Autowired
	private OrderRepository orRepo;
	
	
	//get all products
		public List<Order> listAllOrder()
		{
			return orRepo.findAll();
		}
		
		//delete method
		public void deleteOrder(Long orderId)
		{
			orRepo.deleteById(orderId);
		}
		//add product
		public void saveOrder(Order order)
		{
			orRepo.save(order);
		}
		
		//get product
		public Order get(Long orderId)
		{
			return orRepo.findById(orderId).get();
		}
		
		

	
}
