package com.eliteshoppy.productsearchservice.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ProductSearchConfiguration {
	
	@Value("${es.jms.topic.subscriber.product-create}")
	private String productCreateSubscriberName;

	@Bean
	public MessageChannel pubsubInputChannel() {
		return new DirectChannel();
	}

	@Bean
	public PubSubInboundChannelAdapter messageChannelAdapter(
			@Qualifier("pubsubInputChannel") MessageChannel inputChannel, PubSubTemplate pubsubTemplate) {
		
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(pubsubTemplate, productCreateSubscriberName);
		adapter.setOutputChannel(inputChannel);
		return adapter;
	}
}
