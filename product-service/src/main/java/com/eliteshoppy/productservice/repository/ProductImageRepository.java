package com.eliteshoppy.productservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.eliteshoppy.productservice.model.ProductImage;

public interface ProductImageRepository extends MongoRepository<ProductImage, String> {

	@Query("{ 'productId' : ?0 }")
	Optional<List<ProductImage>> findByProductId(String productId);
	@Query(value = "{'productId': ?0}", delete = true)
	void deleteByProductId(String productId);
}
