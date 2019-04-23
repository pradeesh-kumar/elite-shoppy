package com.eliteshoppy.userservice.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.eliteshoppy.userservice.model.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
}
