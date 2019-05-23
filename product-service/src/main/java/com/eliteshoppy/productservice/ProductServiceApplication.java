package com.eliteshoppy.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.DependsOn;
import org.springframework.integration.annotation.MessagingGateway;

import com.eliteshoppy.productservice.model.Product;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.eliteshoppy.productservice")
@EnableSwagger2
@EnableEurekaClient
@EnableOAuth2Sso
@RefreshScope
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
	@DependsOn("jacksonPubSubMessageConverter")
	public interface PubsubOutboundGateway {
		void publish(Product product);
	}

}
