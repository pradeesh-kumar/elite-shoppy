package com.eliteshoppy.eliteshoppyweb.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@RefreshScope
public class GlobalInterceptor implements HandlerInterceptor {
	
	@Value("${es.api.url}")
	private String apiUrl;
	@Value("${es.api.client_id}")
	private String apiClientId;
	@Value("${es.api.client_secret}")
	private String apiClientScret;
	@Value("${es.api.grant_type}")
	private String apiGrantType;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (modelAndView != null) {
			modelAndView.getModelMap().addAttribute("apiUrl", apiUrl);
			modelAndView.getModelMap().addAttribute("apiClientId", apiClientId);
			modelAndView.getModelMap().addAttribute("apiClientSecret", apiClientScret);
			modelAndView.getModelMap().addAttribute("apiGrantType", apiGrantType);
		}
	}
}