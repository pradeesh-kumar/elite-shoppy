package com.eliteshoppy.productservice.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eliteshoppy.productservice.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {
}