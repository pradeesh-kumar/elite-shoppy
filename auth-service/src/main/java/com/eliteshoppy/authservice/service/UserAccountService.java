package com.eliteshoppy.authservice.service;

import org.bson.types.ObjectId;

import com.eliteshoppy.authservice.model.UserAccount;

public interface UserAccountService {

	UserAccount findById(ObjectId objectId);
	UserAccount create(UserAccount ua);
	void update(UserAccount ua);
	void updateStatus(ObjectId userId, boolean status);
}
