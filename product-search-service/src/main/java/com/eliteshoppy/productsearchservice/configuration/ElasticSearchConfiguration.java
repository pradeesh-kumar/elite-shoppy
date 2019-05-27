package com.eliteshoppy.productsearchservice.configuration;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ElasticSearchConfiguration {
	
	@Bean
	public CustomEntityMapper customEntityMapper(ObjectMapper objectMapper) {
		return new CustomEntityMapper(objectMapper);
	}

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate(Client client, CustomEntityMapper customEntityMapper) {
		return new ElasticsearchTemplate(client, customEntityMapper);
	}

	public static class CustomEntityMapper implements EntityMapper {

		private final ObjectMapper objectMapper;
		
		public CustomEntityMapper(ObjectMapper objectMapper) {
			this.objectMapper = objectMapper;
		}

		@Override
		public String mapToString(Object object) throws IOException {
			return objectMapper.writeValueAsString(object);
		}

		@Override
		public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
			return objectMapper.readValue(source, clazz);
		}
	}
}