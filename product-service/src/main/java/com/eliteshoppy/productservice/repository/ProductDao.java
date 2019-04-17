package com.eliteshoppy.productservice.repository;

import java.util.Optional;

import com.eliteshoppy.productservice.model.Product;

public interface ProductDao {
	
	Optional<Product> get(int productId);

	void create(Product product);
	void update(Product product);
	void delete(int productId);
}
