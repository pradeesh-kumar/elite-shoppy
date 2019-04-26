package com.eliteshoppy.authservice.controller;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	public ResponseEntity<UserAccount> getUser(@PathVariable ObjectId userId) {
		return new ResponseEntity<>(userAccountService.findById(userId), HttpStatus.OK);
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
	
	@GetMapping("/status/{userId}/{status}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<SuccessResponse> getUser(@PathVariable ObjectId userId, @PathVariable boolean status) {
		userAccountService.updateStatus(userId, status);
		return new ResponseEntity<>(new SuccessResponse("User has been updated"), HttpStatus.OK);
	}
}
