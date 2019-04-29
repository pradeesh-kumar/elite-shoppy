package com.eliteshoppy.authservice.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eliteshoppy.authservice.exception.UserAccountAlreadyExistsException;
import com.eliteshoppy.authservice.exception.UserNotFoundException;
import com.eliteshoppy.authservice.model.UserAccount;
import com.eliteshoppy.authservice.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserAccount findById(String objectId) {
		Optional<UserAccount> ua = userAccountRepo.findById(objectId);
		ua.orElseThrow(() -> new UserNotFoundException("User not found!"));
		return ua.get();
	}

	@Override
	public UserAccount getAuthenticatedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
		return userAccountRepo.findByUsername(username).get().get(0);
	}

	@Override
	public UserAccount create(UserAccount ua) {
		checkIfExist(ua.getUsername());
		ua.setCreationDate(LocalDateTime.now());
		ua.setPassword(passwordEncoder.encode(ua.getPassword()));
		return userAccountRepo.insert(ua);
	}

	@Override
	public void update(UserAccount ua) {
		userAccountRepo.save(ua);
	}

	@Override
	public void updateStatus(String userId, boolean status) {
		Optional<UserAccount> ua = userAccountRepo.findById(userId);
		ua.orElseThrow(() -> new UserNotFoundException("User not found!"));
		UserAccount u = ua.get();
		u.setStatus(status);
		userAccountRepo.save(u);
	}

	private void checkIfExist(String username) {
		userAccountRepo.findByUsername(username).ifPresent(uaList -> { 
			if (!uaList.isEmpty()) {
				throw new UserAccountAlreadyExistsException("User account already exists with this username!");
			}
		});
	}

}
