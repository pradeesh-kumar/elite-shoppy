package com.eliteshoppy.productsearchservice.jms.subscriber;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.eliteshoppy.productsearchservice.model.Product;
import com.eliteshoppy.productsearchservice.service.ProductService;

@Component
public class ProductCreationEventSubscriber {
	
	private static final Logger logger = Logger.getLogger(ProductCreationEventSubscriber.class);
	
	@Autowired
	private ProductService productService;

	@ServiceActivator(inputChannel = "pubSubProductCreateInputChannel")
	public void productCreateMessageReceiver(Product payload,
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
		logger.info("Product Created Event Message arrived! Payload: " + payload);
		logger.info("Inserting product to ElasticSearch");
		productService.create(payload);
		logger.info("Product has been inserted to ElasticSearch");
		message.ack();
	}
}
