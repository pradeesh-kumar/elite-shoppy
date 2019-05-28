package com.eliteshoppy.productservice.service;

import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.confuguration.OAuthAuthoritiesExtractor;
import com.eliteshoppy.productservice.model.Review;
import com.eliteshoppy.productservice.repository.ReviewRepository;
import com.eliteshoppy.productservice.restclient.UserFeignClient;

@Service
public class ReviewServiceImpl implements ReviewService {

	private static final Logger logger = Logger.getLogger(ReviewServiceImpl.class);
	
	@Autowired private ReviewRepository reviewRepo;
	@Autowired private OAuthAuthoritiesExtractor authoritiesExtractor;
	@Autowired private UserFeignClient userFeignClient;

	@Override
	public List<Review> findByProductId(String productId) {
		return reviewRepo.findByProductId(productId).get();
	}

	@Override
	public Review create(Review review) {
		logger.info("Creating review");
		review.setUserId(authoritiesExtractor.getUserId());
		logger.info("Fetching user from Auth service");
		Map<String, Object> userMap = userFeignClient.getUser(authoritiesExtractor.getUserId());
		logger.info("User object obtained: " + userMap);
		review.setReviewerName((String) userFeignClient.getUser(authoritiesExtractor.getUserId()).get("fullName"));
		logger.info("Saving Review in db");
		return reviewRepo.save(review);
	}

}
