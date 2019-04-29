package com.eliteshoppy.productservice.service;

import com.eliteshoppy.productservice.model.Product;

public interface ProductService {

	Product findById(String productId);

	Product create(Product product);
	void update(Product product);
	void delete(String productId);
}
