package com.xcale.challenge.group.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xcale.challenge.group.model.Group;
import com.xcale.challenge.group.repository.GroupRepository;
import com.xcale.challenge.user.model.User;

@Service
public class WspGroupService {

	
	Map<String, String> topicPerGroup = new HashMap<>();
	@Autowired
	GroupRepository groupRepository;
	
	public void addNewGroup(Group newGroup) {
		groupRepository.save(newGroup);
	
	}
	
	public List<Group> getGroups(){
		List<Group> groups = new ArrayList<>();
		Iterable<Group> resultGroups = groupRepository.findAll();
		
		resultGroups.forEach(groups::add);
		return groups;
	}
	
	public Group getLobbyGroup() throws Exception {
		Optional<Group> lobby = groupRepository.findByName(LobbyInitializer.LOBBY);
		
		return lobby.orElseThrow(() -> new Exception("No lobby found"));
	}
	
	public void addUserToGroup(User user, Group group) {
		group.getUsers().add(user);
		
		groupRepository.save(group);
		
	}
	
	public Optional<Group> getGroupByName( String groupName) {
		return groupRepository.findByName(groupName);
	}
}
