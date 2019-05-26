package com.eliteshoppy.productsearchservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eliteshoppy.productsearchservice.model.Product;
import com.eliteshoppy.productsearchservice.service.SearchService;

@RestController
@RequestMapping("/search")
public class ProductSearchController {
	
	@Autowired
	private SearchService searchService;

	@GetMapping("/new-arrivals/{category}")
	public ResponseEntity<List<Product>> getNewArrivals(@PathVariable String category) {
		return new ResponseEntity<>(searchService.newArrivals(category), HttpStatus.OK);
	}
}
