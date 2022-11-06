package com.example.login;


import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	
	//get all products
		public List<User> listAll()
		{
			return repo.findAll();
		}
		
		//delete method
		public void delete(String id)
		{
			repo.deleteById(id);
		}
		//add product
		public void save(User user)
		{
			repo.save(user);
		}
		
		//get product
		public User get(String id)
		{
			return repo.findById(id).get();
		}
		
		public void populateUsers() {
			User u = new User();
			u.setEmail("kevindarby5@gmail.com");
			u.setPassword("1qaz2wsx");
			u.setId("1234");
			u.setRole("user");
			repo.save(u);
		}
	
	
}
