package com.quest.etna.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.model.User;
import com.quest.etna.model.UserDetails;
import com.quest.etna.model.UserLogin;
import com.quest.etna.repositories.UserRepository;

@RestController
public class AuthenticationController {	
	
	static UserRepository userRepository;
	
	@Autowired
	private void setUserRepository(UserRepository userRepo) {
		AuthenticationController.userRepository = userRepo;
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<UserDetails> createUser(@RequestBody UserLogin userLogin) {
		Optional<User[]> userL = userRepository.findByUsername(userLogin.getUsername());
		
		if(userL.isEmpty()) {
			User user = new User(userLogin.getUsername(), userLogin.getPassword());
			userRepository.save( user );
			UserDetails userDetails = new UserDetails( user.getUsername(), user.getRole() ); 
 			return 	new ResponseEntity<>( userDetails, HttpStatus.CREATED );			
		} else
			return 	new ResponseEntity<>( HttpStatus.CONFLICT );
	}
}
