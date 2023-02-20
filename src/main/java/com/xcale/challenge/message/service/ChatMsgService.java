package com.xcale.challenge.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xcale.challenge.message.model.ChatMessage;
import com.xcale.challenge.message.repository.ChatMsgRepository;

@Service
public class ChatMsgService {
	
	@Autowired
	ChatMsgRepository chatRepo;
	
	
	public void saveMessageInDb(ChatMessage chatMsg) {
		chatRepo.save(chatMsg);
	}

	public List<ChatMessage> getMsgsOfLobby() {
		return chatRepo.findByGroupNameOrderByidAsc("LOBBY");
		
	}
}
