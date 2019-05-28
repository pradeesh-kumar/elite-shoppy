package com.eliteshoppy.productservice.service;

import java.util.List;

import com.eliteshoppy.productservice.model.Review;

public interface ReviewService {

	List<Review> findByProductId(String productId);
	Review create(Review review);
}
