package com.eliteshoppy.productsearchservice.jms.subscriber;

import org.jboss.logging.Logger;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import com.eliteshoppy.productsearchservice.model.Product;

@Service
public class ProductMessageReciever {

	private static final Logger logger = Logger.getLogger(ProductMessageReciever.class);
	
	@ServiceActivator(inputChannel = "pubsubInputChannel")
	public void messageReciever(Product product) {
		logger.info("Product create Message arrived: " + product);
	}
}
