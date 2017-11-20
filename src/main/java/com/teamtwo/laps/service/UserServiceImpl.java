package com.teamtwo.laps.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamtwo.laps.model.User;
import com.teamtwo.laps.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;

	@Override
	public User authenticate(String uname, String pwd) {
		// TODO Auto-generated method stub
		User u = userRepository.findUserByNamePwd(uname, pwd);
		return u;
	}

	@Override
	public ArrayList<User> findAllUsers() {
		// TODO Auto-generated method stub
		ArrayList<User> ul = (ArrayList<User>) userRepository.findAll();
		return ul;
	}

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.saveAndFlush(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User findUser(String userId) {
		// TODO Auto-generated method stub
		return userRepository.findOne(userId);
	}
	
	@Override
	public User findUserByStaffId(Integer staffId) {
		// TODO Auto-generated method stub
		return userRepository.findUserByStaffId(staffId);
	}
	
	@Override
	@Transactional
	public User changeUser(User user) {
		return userRepository.saveAndFlush(user);
	}
	
	@Override
	@Transactional
	public void removeUser(User user) {
		userRepository.delete(user);
	}



	/* (non-Javadoc)
	 * @see edu.iss.cats.service.UserService#findAllUsers()
	 */


}
