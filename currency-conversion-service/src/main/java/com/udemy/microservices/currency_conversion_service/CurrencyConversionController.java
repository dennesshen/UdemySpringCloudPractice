package com.udemy.microservices.currency_conversion_service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConversionController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	@GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
														  @PathVariable String to, 
														  @PathVariable BigDecimal quantity) {
		
		String url = "http://localhost:8000/currency-exchange/from/{from}/to/{to}";
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversion> responseEntity = 
				new RestTemplate().getForEntity(url, CurrencyConversion.class, uriVariables);
		
		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		String port = environment.getProperty("local.server.port");

		return new CurrencyConversion(currencyConversion.getId(), 
									  from, 
									  to, 
									  quantity, 
									  currencyConversion.getConversionMutiple(),
									  quantity.multiply(currencyConversion.getConversionMutiple()), 
									  port);
		
	}
	
	@GetMapping("/feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from,
															   @PathVariable String to, 
														       @PathVariable BigDecimal quantity) {
		
		
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), 
									  from, 
									  to, 
									  quantity, 
									  currencyConversion.getConversionMutiple(),
									  quantity.multiply(currencyConversion.getConversionMutiple()), 
									  currencyConversion.getEnviroment());
		
	}
}
