package com.eliteshoppy.userservice.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Document(collection = "user_collection")
public class User {

	@Id
	private ObjectId _id;
	
	private ObjectId accountId;
	private String firstName;
	private String lastName;
	private String contactNo;
	private LocalDateTime dob;
	private LocalDateTime creationDate;
}
