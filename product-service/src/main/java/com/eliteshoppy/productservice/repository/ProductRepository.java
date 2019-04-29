package com.eliteshoppy.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eliteshoppy.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}