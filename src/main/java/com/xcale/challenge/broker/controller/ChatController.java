package com.xcale.challenge.broker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.xcale.challenge.message.model.ChatMessage;
import com.xcale.challenge.message.service.ChatMsgService;

@Controller
public class ChatController {
	
	@Autowired
	ChatMsgService chatService;
	
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/realtimechat")
	public ChatMessage sendMessage(@Payload ChatMessage webSocketChatMessage) {
		chatService.saveMessageInDb(webSocketChatMessage);
		return webSocketChatMessage;
	}

	@MessageMapping("/chat.newUser")
	@SendTo("/topic/realtimechat")
	public ChatMessage newUser(@Payload ChatMessage webSocketChatMessage,
			SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
		return webSocketChatMessage;
	}
}
