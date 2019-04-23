package com.eliteshoppy.userservice.controller;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteshoppy.eliteshoppycommons.httpresponse.HttpResponse;
import com.eliteshoppy.eliteshoppycommons.httpresponse.SuccessResponse;
import com.eliteshoppy.userservice.model.User;
import com.eliteshoppy.userservice.service.UserService;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUser(@PathVariable("userId") ObjectId userId) {
		return new ResponseEntity<>(userService.findById(userId), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<HttpResponse> createUser(@NotNull @RequestBody User user) {
		userService.create(user);
		return new ResponseEntity<>(new SuccessResponse("User has been created."), HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<HttpResponse> updateUser(@NotNull @RequestBody User user) {
		userService.update(user);
		return new ResponseEntity<>(new SuccessResponse("User has been updated."), HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<HttpResponse> deleteUser(@NotNull ObjectId userId) {
		userService.delete(userId);
		return new ResponseEntity<>(new SuccessResponse("User has been updated."), HttpStatus.OK);
	}
}
