package com.udemy.microservices.currency_exchange_service;

import java.util.Random;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/currency-exchange")
@Slf4j
public class CircuitBreakerController {

	@GetMapping("/sample-API")
	//@Retry(name = "sample-api", fallbackMethod = "hardcordedResponse")
	@CircuitBreaker(name = "default", fallbackMethod = "hardcordedResponse")
	public String sampleApi() {
		log.info("sample-API");
		ResponseEntity<String> forEntity =
				new RestTemplate().getForEntity("http://localhost:8080/nothing", String.class);
		return forEntity.getBody();
	}
	
	@GetMapping("/sample-API2")
	@RateLimiter(name = "default")
	public String sampleApi2() {
		
		return "sample-API 2";
	}
	
	@GetMapping("/sample-API3")
	@Bulkhead(name = "default") //同時進來的request上限
	public String sampleApi3() {
		
		return "sample-API 2";
	}
	
	
	public String hardcordedResponse(Exception ex) {
		int r = new Random().nextInt(100);
		return "fallback-response" + r;
	}
}
