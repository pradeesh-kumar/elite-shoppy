package com.eliteshoppy.userservice.service;

import org.bson.types.ObjectId;

import com.eliteshoppy.userservice.model.User;

public interface UserService {
	
	User findById(ObjectId userId);

	void create(User user);
	void update(User user);
	void delete(ObjectId userId);
}
