package com.eliteshoppy.productsearchservice.service;

import java.util.List;

import com.eliteshoppy.productsearchservice.model.Product;

public interface SearchService {

	List<Product> newArrivals(String category);
}
