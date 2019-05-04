package com.eliteshoppy.eliteshoppyweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eliteshoppy.eliteshoppyweb.interceptor.GlobalInterceptor;

@Component
public class ProductServiceInterceptorAppConfig implements WebMvcConfigurer {
	
	@Autowired
	private GlobalInterceptor productServiceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(productServiceInterceptor);
	}
}