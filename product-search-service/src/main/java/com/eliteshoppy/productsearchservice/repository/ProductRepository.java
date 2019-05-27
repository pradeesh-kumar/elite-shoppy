package com.eliteshoppy.productsearchservice.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.eliteshoppy.productsearchservice.model.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
	
	List<Product> findByIdealFor(String idealFor);
	List<Product> findByCategory(String category);
}
