package com.eliteshoppy.productservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.confuguration.OAuthAuthoritiesExtractor;
import com.eliteshoppy.productservice.exception.ProductNotFoundException;
import com.eliteshoppy.productservice.model.Product;
import com.eliteshoppy.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OAuthAuthoritiesExtractor authoritiesExtractor;

	@Override
	public Product findById(String productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product for the id %d is not available", productId)));
	}
	
	@Override
	public List<Product> findByOwnerId(String ownerId) {
		return productRepository.findByOwnerId(ownerId).orElseThrow(() -> new ProductNotFoundException("Owner doesn't own any products"));
	}

	@Override
	public Product create(Product product) {
		product.setCreatedDate(LocalDateTime.now());
		product.setOwnerId(authoritiesExtractor.getUserId());
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
