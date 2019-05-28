package com.eliteshoppy.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eliteshoppy.productservice.model.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {
	
	Optional<List<Review>> findByProductId(String productId);
}