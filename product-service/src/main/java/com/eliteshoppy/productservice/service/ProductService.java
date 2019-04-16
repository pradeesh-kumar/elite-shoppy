package com.eliteshoppy.productservice.service;

import com.eliteshoppy.productservice.model.Product;

public interface ProductService {

	Product get(int productId);

	void create(Product product);
	void update(Product product);
	void delete(int productId);
}
