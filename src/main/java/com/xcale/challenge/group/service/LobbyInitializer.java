package com.xcale.challenge.group.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xcale.challenge.group.model.Group;
import com.xcale.challenge.group.repository.GroupRepository;

@Component
public class LobbyInitializer {
	
	public final static String LOBBY = "LOBBY";
	@Autowired
	GroupRepository groupRepository;
	
	@PostConstruct
	public void initializeLobby() {
		Optional<Group> lobbyGroup = groupRepository.findByName(LOBBY);
		
		if(!lobbyGroup.isPresent()) {
			Group firstLobby = new Group();
			firstLobby.setId(1L);
			firstLobby.setName(LOBBY);
			
			groupRepository.save(firstLobby);
		}
		
		
		
	}

}
