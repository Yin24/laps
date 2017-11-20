package com.teamtwo.laps.service;



import java.util.ArrayList;
import java.util.List;

import com.teamtwo.laps.model.User;





public interface UserService {



	User authenticate(String uname, String pwd);
	
	ArrayList<User> findAllUsers();
	
	User createUser(User user);

	List<User> getAllUsers();
	
	User findUser(String userId);
	
	User changeUser(User user);
	
	void removeUser(User user);

	User findUserByStaffId(Integer staffId);

}