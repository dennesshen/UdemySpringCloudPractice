package com.udemy.microservices.currency_conversion_service;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {
	
	private Long id;
	
	private String from;
	
	private String to;
	
	private BigDecimal quantity;
	
	private BigDecimal conversionMutiple;
	
	private BigDecimal totalCalculateAmount;
	
	private String enviroment;

}
