package com.xcale.challenge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.xcale.challenge.exception.NoUserException;
import com.xcale.challenge.group.model.Group;
import com.xcale.challenge.group.model.NewGroupRequest;
import com.xcale.challenge.group.service.WspGroupService;
import com.xcale.challenge.message.model.ChatMessage;
import com.xcale.challenge.message.service.ChatMsgService;
import com.xcale.challenge.user.model.User;
import com.xcale.challenge.user.model.UserContact;
import com.xcale.challenge.user.service.UserService;

@RestController
public class ChatRestController {
	@Autowired
	WspGroupService groupService;
	@Autowired
	UserService userService;
	@Autowired
	ChatMsgService chatService;
	
	@PostMapping(path = "getGroups", 
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Group>> getGroups(@RequestBody User user) throws NoUserException {
		Optional<User> fullUser = userService.getUser(user);
		List<Group> groups = new ArrayList<>();
		
		fullUser.orElseThrow(() -> new NoUserException("No such user in database"))
				.getGroups().iterator()
				.forEachRemaining(groups::add);
		
			return ResponseEntity.ok(groups);
	}
	
	
	@PostMapping(path = "addUser", 
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception{
		Optional<User> userInDB = userService.getUser(user);
		
		if(userInDB.isPresent()) {
			return ResponseEntity.ok(userInDB.get());
		}
		user.getGroups().add(getLobbyGroup());
		User savedUser = userService.addUser(user);

		groupService.addUserToGroup(savedUser, getLobbyGroup());
		
		return ResponseEntity.ok(savedUser);
	}
	
	
	@PostMapping(path = "addContact", 
			 consumes = MediaType.APPLICATION_JSON_VALUE, 
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addContact(@RequestBody UserContact user) throws Exception{
			Optional<User> updatedUser =userService.addContact(user.getOrigin(), user.getNewContact());
			
	return ResponseEntity.ok(updatedUser.orElseThrow(()->new Exception("No contact has been added")));
	}
	
	
	private Group getLobbyGroup() throws Exception {
		return groupService.getLobbyGroup();
	}
	
	@PostMapping(path = "addGroup", 
			 consumes = MediaType.APPLICATION_JSON_VALUE, 
			 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addGroup(@RequestBody NewGroupRequest request) throws Exception{
		
		String groupName = request.getGroupName();
		User user = request.getUser();
		
		Optional<Group> groupInDB = groupService.getGroupByName(groupName);
		Optional<User> userInDB = userService.getUser(user);
		if(groupInDB.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Group already exists");
		}
		
		Group newGroup = new Group();
		newGroup.setName(groupName);
		newGroup.getUsers().add(userInDB.orElseThrow(()-> new NoUserException("El usuario ya no existe")));
		groupService.addNewGroup(newGroup);
		
		return ResponseEntity.ok("Group has been created");
		
	}
	
	
	@GetMapping(path = "getLobby")
	public ResponseEntity<Group> getLobby() throws Exception{
		return ResponseEntity.ok(getLobbyGroup());
	}
		
	@GetMapping(path = "getLobbyMsg")
	public ResponseEntity<List<ChatMessage>> getOldMsgLobby() throws Exception{
		
		return ResponseEntity.ok(chatService.getMsgsOfLobby());
	}
		
}
