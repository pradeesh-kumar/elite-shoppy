package com.eliteshoppy.authservice.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class User {

	private String firstName;
	private String lastName;
	private LocalDateTime creationDate;
}
