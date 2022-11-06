package com.example.login;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class ParkService {
	
	@Autowired
	private ParkRepository paRepo;
	
	
	//get all products
		public List<Park> listAllPark()
		{
			return paRepo.findAll();
		}
		
		//delete method
		public void deletePark(String parkId)
		{
			paRepo.deleteById(parkId);
		}
		//add product
		public void savePark(Park park)
		{
			paRepo.save(park);
		}
		
		//get product
		public Park getPark(String parkId)
		{
			return paRepo.findById(parkId).get();
		}


	
}
