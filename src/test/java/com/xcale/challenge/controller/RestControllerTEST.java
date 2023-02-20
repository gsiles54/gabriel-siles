package com.xcale.challenge.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xcale.challenge.group.model.Group;
import com.xcale.challenge.group.service.WspGroupService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class RestControllerTEST {

	 @InjectMocks
	  private ChatRestController chatController;
	 
	 @Mock
	 private WspGroupService groupService;
	 
	 @Test
	 public void getLobbyAPI() throws Exception 
	 {
		    MockHttpServletRequest request = new MockHttpServletRequest();
	        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	        
	        Group group = new Group();
	        group.setId(1L);
	        
	        when(groupService.getLobbyGroup()).thenReturn(group);

	        
	        ResponseEntity<Group> responseEntity = chatController.getLobby();//.addEmployee(employeeToAdd);

	        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
	   
	 }

}
