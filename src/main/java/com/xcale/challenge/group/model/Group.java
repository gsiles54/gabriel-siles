package com.xcale.challenge.group.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xcale.challenge.message.model.ChatMessage;
import com.xcale.challenge.user.model.User;

@Entity
@Table(name="groups")
public class Group {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="group_seq")
    @SequenceGenerator(name="group_seq", sequenceName="group_seq", allocationSize=10000)
	private Long id;
	@Column
	@NonNull
	private String name;
	@ManyToMany
	@JsonIgnoreProperties("groups")
	private Set<User> users = new HashSet<>();
	@OneToMany(mappedBy="group")
	private List<ChatMessage> messages= new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	public List<ChatMessage> getMessages() {
		return messages;
	}
	public void setMessages(List<ChatMessage> messages) {
		this.messages = messages;
	}
	
	
}
