package com.xcale.challenge.group.model;

import com.xcale.challenge.user.model.User;

public class NewGroupRequest {

	String groupName;
	User user;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
