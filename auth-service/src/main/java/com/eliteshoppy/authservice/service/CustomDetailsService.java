package com.eliteshoppy.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eliteshoppy.authservice.model.CustomUser;
import com.eliteshoppy.authservice.model.UserEntity;
import com.eliteshoppy.authservice.repository.OAuthDao;

@Service("customDetailsService")
public class CustomDetailsService implements UserDetailsService {
	
	@Autowired
	private OAuthDao oauthDao;

	@Override
	public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
		UserEntity userEntity = null;
		try {
			userEntity = oauthDao.getUserDetails(username);
			CustomUser customUser = new CustomUser(userEntity);
			return customUser;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}
	}
}