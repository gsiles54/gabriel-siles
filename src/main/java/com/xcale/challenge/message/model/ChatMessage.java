package com.xcale.challenge.message.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.xcale.challenge.group.model.Group;

@Entity
@Table(name = "message")
public class ChatMessage {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="msg_seq")
    @SequenceGenerator(name="msg_seq", sequenceName="msg_seq", allocationSize=10000)
	private Long id;
	@Column
	private String type;
	@Column
	private String text;
	@Column
	private String sender;
	@Column
	private String nick;
	@ManyToOne
	@JoinColumn(name = "group_id", referencedColumnName = "id")

	@JsonIgnoreProperties("messages")
	private Group group;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	} 

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
}
