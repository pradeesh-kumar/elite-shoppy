package com.eliteshoppy.authservice.service;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.authservice.exception.UserNotFoundException;
import com.eliteshoppy.authservice.model.UserAccount;
import com.eliteshoppy.authservice.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepo;

	@Override
	public UserAccount findById(ObjectId objectId) {
		Optional<UserAccount> ua = userAccountRepo.findById(objectId);
		ua.orElseThrow(() -> new UserNotFoundException("User not found!"));
		return ua.get();
	}

	@Override
	public UserAccount create(UserAccount ua) {
		return userAccountRepo.insert(ua);
	}

	@Override
	public void update(UserAccount ua) {
		userAccountRepo.save(ua);
	}

	@Override
	public void updateStatus(ObjectId userId, boolean status) {
		Optional<UserAccount> ua = userAccountRepo.findById(userId);
		ua.orElseThrow(() -> new UserNotFoundException("User not found!"));
		UserAccount u = ua.get();
		u.setStatus(status);
		userAccountRepo.save(u);
	}

}
