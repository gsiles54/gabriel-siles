package com.xcale.challenge.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xcale.challenge.user.model.User;
import com.xcale.challenge.user.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;


	public User addUser(User newUser) {
		
		return userRepository.save(newUser);
	}
	
	public List<User> getUsers(){
		Iterable<User> users = userRepository.findAll();
		
		List<User> listOfUsers = new ArrayList<>();
		
		users.forEach(listOfUsers::add);
		return listOfUsers;
	}
	
	public Optional<User> getUser(User user) {
		return userRepository.findByNumber(user.getNumber());
		
	}
	
	
	public Optional<User> addContact(User user, User newContact) {
		user.getContacts().add(newContact);
		
		userRepository.save(user);
		
		return Optional.of(user);
	}
	
}
