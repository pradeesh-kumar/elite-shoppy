package com.eliteshoppy.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.confuguration.OAuthAuthoritiesExtractor;
import com.eliteshoppy.productservice.model.Review;
import com.eliteshoppy.productservice.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired private ReviewRepository reviewRepo;
	@Autowired private OAuthAuthoritiesExtractor authoritiesExtractor;

	@Override
	public List<Review> findByProductId(String productId) {
		return reviewRepo.findByProductId(productId).get();
	}

	@Override
	public Review create(Review review) {
		review.setUserId(authoritiesExtractor.getUserId());
		return reviewRepo.save(review);
	}

}
