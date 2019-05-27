package com.eliteshoppy.productservice.confuguration;

import org.springframework.beans.factory.annotation.Qualifier;
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
	@Value("${es.jms.topic.product-update}")
	private String productUpdateTopicName;
	@Value("${es.jms.topic.product-delete}")
	private String productDeleteTopicName;

	@Bean
	public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(@Qualifier("objectMapper") ObjectMapper objectMapper) {
		return new JacksonPubSubMessageConverter(objectMapper);
	}
	
	@Bean
	@ServiceActivator(inputChannel = "pubSubProductCreateOutputChannel")
	public MessageHandler productCreateMessageSender(PubSubTemplate pubSubTemplate) {
		PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, productCreateTopicName);
		return adapter;
	}
	
	@Bean
	@ServiceActivator(inputChannel = "pubSubProductUpdateOutputChannel")
	public MessageHandler productUpdateMessageSender(PubSubTemplate pubSubTemplate) {
		PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, productUpdateTopicName);
		return adapter;
	}
	
	@Bean
	@ServiceActivator(inputChannel = "pubSubProductDeleteOutputChannel")
	public MessageHandler productDeleteMessageSender(PubSubTemplate pubSubTemplate) {
		PubSubMessageHandler adapter = new PubSubMessageHandler(pubSubTemplate, productDeleteTopicName);
		return adapter;
	}

	@MessagingGateway(defaultRequestChannel = "pubSubProductCreateOutputChannel")
	public interface PubSubProductCreateGateway {
		void publish(Product product);
	}
	
	@MessagingGateway(defaultRequestChannel = "pubSubProductUpdateOutputChannel")
	public interface PubSubProductUpdateGateway {
		void publish(Product product);
	}
	
	@MessagingGateway(defaultRequestChannel = "pubSubProductDeleteOutputChannel")
	public interface PubSubProductDeleteGateway {
		void publish(String productId);
	}
}
