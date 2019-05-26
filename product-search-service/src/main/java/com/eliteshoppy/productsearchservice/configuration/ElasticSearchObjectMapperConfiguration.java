package com.eliteshoppy.productsearchservice.configuration;

import java.io.IOException;

import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.geo.CustomGeoModule;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ElasticSearchObjectMapperConfiguration {

	@Bean
	public EntityMapper getEntityMapper() {
		return new CustomEntityMapper();
	}

	@Bean
	@Primary
	public ElasticsearchOperations elasticsearchTemplateNew(Client client,
			final ElasticsearchConverter elasticsearchConverter,
			final SimpleElasticsearchMappingContext simpleElasticsearchMappingContext, EntityMapper mapper) {
		return new ElasticsearchTemplate(client, elasticsearchConverter,
				new DefaultResultMapper(simpleElasticsearchMappingContext, mapper));
	}

	public class CustomEntityMapper implements EntityMapper {

		private final ObjectMapper objectMapper;

		public CustomEntityMapper() {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.registerModule(new CustomGeoModule());
			objectMapper.registerModule(new JavaTimeModule());
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
