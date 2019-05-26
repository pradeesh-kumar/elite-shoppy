package com.eliteshoppy.productsearchservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.eliteshoppy.productsearchservice.model.Product;
import com.eliteshoppy.productsearchservice.repository.ProductRepository;

public class SearchServiceImpl implements SearchService {
	
	@Autowired
	private ProductRepository productRepo;

	@Override
	public List<Product> newArrivals(String category) {
		return productRepo.findByIdealForOrCategory(category);
	}

}
