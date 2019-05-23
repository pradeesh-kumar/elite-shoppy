package com.eliteshoppy.productservice.jms.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

import com.eliteshoppy.productservice.model.Product;

@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
@Component
public interface PubsubOutboundGateway {
	
	void publish(Product product);
}