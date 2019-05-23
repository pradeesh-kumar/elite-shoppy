package com.eliteshoppy.productservice.confuguration;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@RefreshScope
public class ProductConfiguration {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.eliteshoppy.productservice.controller")).build();
	}

	/*
	 * @Bean("jacksonPubSubMessageConverter") public JacksonPubSubMessageConverter
	 * jacksonPubSubMessageConverter() { ObjectMapper mapper = new
	 * ObjectMapper().registerModule(new ParameterNamesModule()) .registerModule(new
	 * Jdk8Module()).registerModule(new JavaTimeModule()); return new
	 * JacksonPubSubMessageConverter(mapper); }
	 */

	

}
