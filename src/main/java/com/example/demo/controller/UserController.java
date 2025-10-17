package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.exception.ResouceNotFoundException;
import com.example.demo.repository.UserRespository;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserRespository userRespository;
	
	
	//get all users
	@GetMapping
	public List<User> getAllUsers(){
		return this.userRespository.findAll();
	}
	
	
	//get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable(value = "id") long userId) {
		return this.userRespository.findById(userId)
				.orElseThrow(() -> new ResouceNotFoundException("User not found with id :"+ userId));
	}
	
	//create user
	@PostMapping
	public User createUser(@RequestBody User user) {
		return this.userRespository.save(user);
	}
	//update user by id
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User user, @PathVariable ("id") long userId ) {
		User existingUser = this.userRespository.findById(userId)
				.orElseThrow(() -> new ResouceNotFoundException("User not found with id :"+ userId));
		existingUser.setFirstName(user.getFirstName());
		existingUser.setLastName(user.getLastName());
		existingUser.setEmail(user.getEmail());
		return this.userRespository.save(existingUser);
	}
	//delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable ("id") long userId){
		User existingUser = this.userRespository.findById(userId)
				.orElseThrow(() -> new ResouceNotFoundException("User not found with id :"+ userId));
		this.userRespository.delete(existingUser);
		return ResponseEntity.ok().build();
		
	}
	
	

}
