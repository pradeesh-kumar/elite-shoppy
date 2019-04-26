package com.eliteshoppy.authservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
	public User loadUserByUsername(final String username) throws UsernameNotFoundException {

		try {
			Optional<List<UserAccount>> userAccounts = userAccountRepo.findByUsername(username);
			userAccounts.orElseThrow(
					() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
			userAccounts.get().stream().findAny().orElseThrow(
					() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
			User ud = populateAuthUser(userAccounts.get().stream().findAny().get());
			return ud;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
	}

	private User populateAuthUser(UserAccount ua) {
		List<GrantedAuthority> ga = new ArrayList<>();
		ga.add(new SimpleGrantedAuthority(ua.getUserType().name()));
		User user = new User(ua.getUsername(), ua.getPassword(), ga);
		return user;
	}
}