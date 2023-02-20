package com.xcale.challenge.user.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xcale.challenge.group.model.Group;

@Entity
@Table(name = "users")
public class User {

	@Id
	private Long number;
	@Column
	private String nick;
	@ManyToMany
	@JoinTable(
			  name = "user_contact",
			  joinColumns = @JoinColumn(name = "user_id"),
			  inverseJoinColumns = @JoinColumn(name = "contact_id"))
	@JsonIgnoreProperties("contacts")
	private Set<User> contacts = new HashSet <>();
	
	@ManyToMany(mappedBy = "users")
	@JsonIgnoreProperties("users")
	private Set<Group> groups = new HashSet<>();
	
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
	public Set<Group> getGroups() {
		return groups;
	}
	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}
	public void setContacts(Set<User> contacts) {
		this.contacts = contacts;
	}
	public Set<User> getContacts() {
		return contacts;
	}
	
	
}
