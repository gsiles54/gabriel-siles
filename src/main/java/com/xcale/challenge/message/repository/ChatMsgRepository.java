package com.xcale.challenge.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xcale.challenge.message.model.ChatMessage;

@Repository
public interface ChatMsgRepository extends  CrudRepository<ChatMessage,Integer>{
	@Query("SELECT m FROM ChatMessage m JOIN m.group g WHERE g.name = :groupname ORDER BY m.id ASC")
	List<ChatMessage> findByGroupNameOrderByidAsc(String groupname);
}
