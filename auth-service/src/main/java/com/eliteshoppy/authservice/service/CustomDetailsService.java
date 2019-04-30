package com.eliteshoppy.authservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eliteshoppy.authservice.model.UserAccount;
import com.eliteshoppy.authservice.repository.UserAccountRepository;

@Service("customDetailsService")
public class CustomDetailsService implements UserDetailsService {

	@Autowired
	private UserAccountRepository userAccountRepo;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		try {
			Optional<List<UserAccount>> userAccounts = userAccountRepo.findByUsername(username);
			userAccounts.orElseThrow(
					() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
			userAccounts.get().stream().findAny().orElseThrow(
					() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
			return populateAuthUser(userAccounts.get().stream().findAny().get());
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
	}

	private UserDetails populateAuthUser(UserAccount ua) {
		return User.builder().username(ua.getUsername()).password(ua.getPassword())
			.disabled(!ua.isStatus()).authorities("ROLE_" + ua.getUserType().name()).build();
		
	}
}