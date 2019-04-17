package com.eliteshoppy.productservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.exception.ProductNotFoundException;
import com.eliteshoppy.productservice.model.Product;
import com.eliteshoppy.productservice.repository.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	public Product get(int productId) {
		return productDao.get(productId).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product for the id %d is not available", productId)));
	}

	@Override
	public void create(Product product) {
		productDao.create(product);
	}

	@Override
	public void update(Product product) {
		productDao.update(product);
	}

	@Override
	public void delete(int productId) {
		productDao.delete(productId);
	}

}
