package com.eliteshoppy.productsearchservice.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.eliteshoppy.productsearchservice.model.Product;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
}
