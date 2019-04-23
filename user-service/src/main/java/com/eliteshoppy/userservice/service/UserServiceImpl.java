package com.eliteshoppy.userservice.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.userservice.exception.UserNotFoundException;
import com.eliteshoppy.userservice.model.User;
import com.eliteshoppy.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User findById(ObjectId userId) {
		return userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException(String.format("User for the id %d is not available", userId)));
	}

	@Override
	public void create(User user) {
		userRepository.insert(user);
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
	}

	@Override
	public void delete(ObjectId userId) {
		userRepository.deleteById(userId);
	}

}
