package com.eliteshoppy.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eliteshoppy.productservice.model.Attribute;

public interface AttributeRepository extends MongoRepository<Attribute, String> {
}
