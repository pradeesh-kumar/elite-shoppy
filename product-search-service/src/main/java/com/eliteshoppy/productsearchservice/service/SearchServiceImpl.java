package com.eliteshoppy.productsearchservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.eliteshoppy.productsearchservice.model.Product;
import com.eliteshoppy.productsearchservice.repository.ProductRepository;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ProductRepository productRepo;

	@Override
	public List<Product> newArrivals(String category) {
		List<Product> productList = productRepo.findByIdealFor(category);
		if (CollectionUtils.isEmpty(productList)) {
			productList = productRepo.findByCategory(category);
		}
		return productList;
	}

}
