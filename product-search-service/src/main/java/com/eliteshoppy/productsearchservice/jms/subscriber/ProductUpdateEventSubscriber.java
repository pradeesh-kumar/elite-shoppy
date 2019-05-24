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
public class ProductUpdateEventSubscriber {
	
	private static final Logger logger = Logger.getLogger(ProductUpdateEventSubscriber.class);
	
	@Autowired
	private ProductService productService;

	@ServiceActivator(inputChannel = "pubSubProductUpdateInputChannel")
	public void productUpdateMessageReceiver(Product payload,
			@Header(GcpPubSubHeaders.ORIGINAL_MESSAGE) BasicAcknowledgeablePubsubMessage message) {
		logger.info("Product Updated Event Message arrived! Payload: " + payload);
		logger.info("Updateing product to ElasticSearch");
		productService.update(payload);
		logger.info("Product has been Updated to ElasticSearch");
		message.ack();
	}
}
