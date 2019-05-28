package com.eliteshoppy.productservice.restclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${es.feignclient.auth-service}")
public interface UserFeignClient {

	@GetMapping("/{userId}")
	Map<String, Object> getUser(@PathVariable String userId);
}
