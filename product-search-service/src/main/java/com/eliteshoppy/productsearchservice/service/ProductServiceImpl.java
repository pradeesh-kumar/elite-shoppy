package com.eliteshoppy.productsearchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productsearchservice.model.Product;
import com.eliteshoppy.productsearchservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productdRepo;

	@Override
	public void create(Product product) {
		productdRepo.save(product);
	}

	@Override
	public void update(Product product) {
		productdRepo.save(product);
	}

	@Override
	public void delete(Product product) {
		productdRepo.delete(product);
	}
	
	@Override
	public void deleteById(String productId) {
		productdRepo.deleteById(productId);
	}
	
}
