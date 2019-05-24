package com.eliteshoppy.productsearchservice.service;

import com.eliteshoppy.productsearchservice.model.Product;

public interface ProductService {

	void create(Product product);
	void update(Product product);
	void delete(Product product);
	void deleteById(String productId);
}
