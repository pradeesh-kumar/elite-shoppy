package com.eliteshoppy.authservice.service;

import com.eliteshoppy.authservice.model.UserAccount;

public interface UserAccountService {

	UserAccount getAuthenticatedUser();
	UserAccount findById(String objectId);
	UserAccount create(UserAccount ua);
	void update(UserAccount ua);
	void updateStatus(String userId, boolean status);
}
