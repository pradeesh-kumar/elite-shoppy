package com.eliteshoppy.authservice.controller;

import java.util.Collection;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteshoppy.authservice.model.UserAccount;
import com.eliteshoppy.authservice.service.UserAccountService;
import com.eliteshoppy.eliteshoppycommons.httpresponse.SuccessResponse;

@RestController
@RequestMapping("/useraccount")
@Validated
public class AuthController {

	@Autowired
	private UserAccountService userAccountService;
	
	@GetMapping("/{userId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserAccount> getUser(@PathVariable String userId) {
		return new ResponseEntity<>(userAccountService.findById(userId), HttpStatus.OK);
	}
	
	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserAccount> getAuthenticatedUser() {
		return new ResponseEntity<>(userAccountService.getAuthenticatedUser(), HttpStatus.OK);
	}
	
	@PostMapping
	@PermitAll
	public ResponseEntity<UserAccount> createUser(@NotNull @RequestBody UserAccount ua) {
		return new ResponseEntity<>(userAccountService.create(ua), HttpStatus.CREATED);
	}
	
	@PutMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<SuccessResponse> updateUser(@NotNull @RequestBody UserAccount ua) {
		userAccountService.update(ua);
		return new ResponseEntity<>(new SuccessResponse("User has been updated"), HttpStatus.CREATED);
	}
	
	@PutMapping("/status/{userId}/{status}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<SuccessResponse> updateStatus(@PathVariable String userId, @PathVariable boolean status) {
		userAccountService.updateStatus(userId, status);
		return new ResponseEntity<>(new SuccessResponse("User has been updated"), HttpStatus.OK);
	}
	
	@GetMapping("/role")
	@PreAuthorize("isAuthenticated()")
	public Collection<GrantedAuthority> getauth() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getAuthorities();
	}
}
