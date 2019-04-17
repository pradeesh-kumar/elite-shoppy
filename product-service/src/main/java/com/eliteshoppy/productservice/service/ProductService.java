package com.eliteshoppy.productservice.service;

import org.bson.types.ObjectId;

import com.eliteshoppy.productservice.model.Product;

public interface ProductService {

	Product findById(ObjectId productId);

	void create(Product product);
	void update(Product product);
	void delete(ObjectId productId);
}
