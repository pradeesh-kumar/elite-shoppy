package com.eliteshoppy.authservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Document(collection = "user_account")
public class UserAccount {
	
	@Id
	private String id;
	
	private String username;
	private String password;
	private UserType userType;
	private LocalDateTime creationDate;
	private User user;
	private boolean status;
	
}