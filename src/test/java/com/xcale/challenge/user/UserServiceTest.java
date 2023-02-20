package com.xcale.challenge.user;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.xcale.challenge.user.model.User;
import com.xcale.challenge.user.repository.UserRepository;
import com.xcale.challenge.user.service.UserService;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	@Test
	public void getZeroUsers() {
		List<User> emptyList = new ArrayList<>();
		when(userRepository.findAll()).thenReturn(emptyList);
		
		Assertions.assertEquals(userService.getUsers().size(),0);
	}
	
	@Test
	public void getOneUser() {

		List<User> almostEmptyList = new ArrayList<>();
		User userDummy = new User();
		userDummy.setNumber(123L);
		userDummy.setNick("123");
		almostEmptyList.add(userDummy);
		
		when(userRepository.findAll()).thenReturn(almostEmptyList);
		

		Assertions.assertEquals(userService.getUsers().size(),1);
	}
}
