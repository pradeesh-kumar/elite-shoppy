package com.eliteshoppy.productservice.jms.gateway;

import org.springframework.integration.annotation.MessagingGateway;

import com.eliteshoppy.productservice.model.Product;

@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
public interface PubsubOutboundGateway {
	
	void send(Product product);
}
