package com.eliteshoppy.productservice.confuguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import com.eliteshoppy.productservice.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ProductMessagingConfiguration {
	
	@Value("${es.jms.topic.product-create}")
	private String productCreateTopicName;

	@Bean
	public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
		return new JacksonPubSubMessageConverter(objectMapper);
	}
	
	@Bean
	@ServiceActivator(inputChannel = "pubSubOutputChannel")
	public MessageHandler messageSender(PubSubTemplate pubSubTemplate) {
		PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, productCreateTopicName);
		return adapter;
	}

	@MessagingGateway(defaultRequestChannel = "pubSubOutputChannel")
	public interface PubSubProductGateway {
		void publish(Product product);
	}
}
