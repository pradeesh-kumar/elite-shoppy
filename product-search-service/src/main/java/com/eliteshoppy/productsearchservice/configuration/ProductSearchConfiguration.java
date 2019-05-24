package com.eliteshoppy.productsearchservice.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.support.converter.JacksonPubSubMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import com.eliteshoppy.productsearchservice.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ProductSearchConfiguration {
	
	@Value("${es.jms.topic.subscriber.product-create}")
	private String productCreateSubscriberName;
	@Value("${es.jms.topic.subscriber.product-update}")
	private String productUpdateSubscriberName;
	@Value("${es.jms.topic.subscriber.product-delete}")
	private String productDeleteSubscriberName;

	@Bean
	public JacksonPubSubMessageConverter jacksonPubSubMessageConverter(ObjectMapper objectMapper) {
		return new JacksonPubSubMessageConverter(objectMapper);
	}
	
	@Bean
	public DirectChannel pubSubInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public PubSubInboundChannelAdapter productCreateMessageChannelAdapter(
			@Qualifier("pubSubProductCreateInputChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, productCreateSubscriberName);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		adapter.setPayloadType(Product.class);
		return adapter;
	}
	
	@Bean
	public PubSubInboundChannelAdapter productUpdateMessageChannelAdapter(
			@Qualifier("pubSubProductUpdateInputChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, productUpdateSubscriberName);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		adapter.setPayloadType(Product.class);
		return adapter;
	}
	
	@Bean
	public PubSubInboundChannelAdapter productDeleteMessageChannelAdapter(
			@Qualifier("pubSubProductDeleteInputChannel") MessageChannel inputChannel, PubSubTemplate pubSubTemplate) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubSubTemplate, productDeleteSubscriberName);
		adapter.setOutputChannel(inputChannel);
		adapter.setAckMode(AckMode.MANUAL);
		adapter.setPayloadType(String.class);
		return adapter;
	}

}
