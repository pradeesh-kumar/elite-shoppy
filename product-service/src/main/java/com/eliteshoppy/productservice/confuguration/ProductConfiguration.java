package com.eliteshoppy.productservice.confuguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import com.eliteshoppy.productservice.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class ProductConfiguration {

	@Value("${es.jms.topic.product-create}")
	private String productCreateTopicName;

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.eliteshoppy.productservice.controller")).build();
	}

	@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
	@Component
	public interface PubsubOutboundGateway {

		void publish(Product product);
	}

	@Bean("jacksonPubSubMessageConverter")
	public JacksonPubSubMessageConverter jacksonPubSubMessageConverter() {
		ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule())
				.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
		return new JacksonPubSubMessageConverter(mapper);
	}

	@Bean
	@ServiceActivator(inputChannel = "pubsubOutputChannel")
	public MessageHandler messageHandler(PubSubTemplate pubsubTemplate) {
		return new PubSubMessageHandler(pubsubTemplate, productCreateTopicName);
	}

}
