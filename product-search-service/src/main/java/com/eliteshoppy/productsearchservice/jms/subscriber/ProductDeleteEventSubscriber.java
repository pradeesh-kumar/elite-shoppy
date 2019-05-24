package com.eliteshoppy.productsearchservice.jms.subscriber;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.support.BasicAcknowledgeablePubsubMessage;
import org.springframework.cloud.gcp.pubsub.support.GcpPubSubHeaders;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.eliteshoppy.productsearchservice.service.ProductService;

@Component
public class ProductDeleteEventSubscriber {
	
	private static final Logger logger = Logger.getLogger(ProductDeleteEventSubscriber.class);
	
	@Autowired
	private ProductService productService;

	@ServiceActivator(inputChannel = "pubSubProductDeleteInputChannel")
	public void productDeleteMessageReceiver(String productId,
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
		logger.info("Product Delete Event Message arrived! ProductId: " + productId);
		logger.info("Deleting the product from ElasticSearch");
		productService.deleteById(productId);
		logger.info("Product has been Deleted from ElasticSearch");
		message.ack();
	}
}
