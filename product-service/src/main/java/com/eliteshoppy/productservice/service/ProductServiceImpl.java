package com.eliteshoppy.productservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productservice.confuguration.OAuthAuthoritiesExtractor;
import com.eliteshoppy.productservice.confuguration.ProductMessagingConfiguration.PubSubProductCreateGateway;
import com.eliteshoppy.productservice.confuguration.ProductMessagingConfiguration.PubSubProductDeleteGateway;
import com.eliteshoppy.productservice.confuguration.ProductMessagingConfiguration.PubSubProductUpdateGateway;
import com.eliteshoppy.productservice.exception.ProductNotFoundException;
import com.eliteshoppy.productservice.model.Product;
import com.eliteshoppy.productservice.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	
	@Autowired private ProductRepository productRepository;
	@Autowired private OAuthAuthoritiesExtractor authoritiesExtractor;
	@Autowired private ImageService imageService;
	@Autowired private PubSubProductCreateGateway productCreateMessageGateway;
	@Autowired private PubSubProductUpdateGateway productUpdateMessageGateway;
	@Autowired private PubSubProductDeleteGateway productDeleteMessageGateway;

	@Override
	public Product findById(String productId) {
		return productRepository.findById(productId).orElseThrow(
				() -> new ProductNotFoundException(String.format("Product for the id %s is not available", productId)));
	}
	
	@Override
	public List<Product> findByOwnerId(String ownerId) {
		return productRepository.findByOwnerId(ownerId).orElseThrow(() -> new ProductNotFoundException("Owner doesn't own any products"));
	}

	@Override
	public Product create(Product product) {
		product.setCreatedDate(LocalDateTime.now());
		product.setOwnerId(authoritiesExtractor.getUserId());
		logger.info("Inserting product into mongodb: " + product);
		product = productRepository.insert(product);
		logger.info("Product has been inserted");
		productCreateMessageGateway.publish(product);
		logger.info("Product Created event has been pushed to JMS");
		return product;
	}

	@Override
	public void update(Product product) {
		logger.info("Updating product into mongodb: " + product);
		product.setUpdatedDate(LocalDateTime.now());
		productRepository.save(product);
		logger.info("Product has been Updated");
		productUpdateMessageGateway.publish(product);
		logger.info("Product Updated event has been pushed to JMS");
	}

	@Override
	public void delete(String productId) {
		logger.info("Deleting product into mongodb: " + productId);
		productRepository.deleteById(productId);
		logger.info("Product has been Deleted");
		imageService.deleteByProductId(productId);
		logger.info("Product Image has been Deleted");
		productDeleteMessageGateway.publish(productId);
		logger.info("Product Deleted event has been pushed to JMS");
	}

}
