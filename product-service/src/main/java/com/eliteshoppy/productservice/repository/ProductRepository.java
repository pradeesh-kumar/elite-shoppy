package com.eliteshoppy.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eliteshoppy.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	
	@Query("{ 'ownerId' : ?0 }")
	Optional<List<Product>> findByOwnerId(String ownerId);
}