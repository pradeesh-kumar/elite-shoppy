package com.eliteshoppy.productservice.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.exception.ProductNotFoundException;
import com.eliteshoppy.productservice.model.Product;
import com.eliteshoppy.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product findById(ObjectId productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product for the id %d is not available", productId)));
	}

	@Override
	public void create(Product product) {
		productRepository.insert(product);
	}

	@Override
	public void update(Product product) {
		productRepository.save(product);
	}

	@Override
	public void delete(ObjectId productId) {
		productRepository.deleteById(productId);
	}

}
