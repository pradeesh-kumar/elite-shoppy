package com.eliteshoppy.productsearchservice.configuration;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ElasticSearchConfiguration {

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate(Client client) {
		return new ElasticsearchTemplate(client, new CustomEntityMapper());
	}

	public static class CustomEntityMapper implements EntityMapper {

		private final ObjectMapper objectMapper;

		public CustomEntityMapper() {
			objectMapper = new ObjectMapper();
			objectMapper.registerModule(new CustomGeoModule());
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		    objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
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