package com.eliteshoppy.productservice.service;

import java.time.LocalDateTime;

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
	public Product findById(String productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product for the id %d is not available", productId)));
	}

	@Override
	public Product create(Product product) {
		product.setCreatedDate(LocalDateTime.now());
		return productRepository.insert(product);
	}

	@Override
	public void update(Product product) {
		product.setUpdatedDate(LocalDateTime.now());
		productRepository.save(product);
	}

	@Override
	public void delete(String productId) {
		productRepository.deleteById(productId);
	}

}
