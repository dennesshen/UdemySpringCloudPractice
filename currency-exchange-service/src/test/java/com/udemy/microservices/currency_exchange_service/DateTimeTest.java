package com.udemy.microservices.currency_exchange_service;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

public class DateTimeTest {
	
	
	
	public boolean ifNewMember(Member member) {
		DateTime register = member.register;
		
		Integer year = Integer.valueOf(register.toString("yyyy"));
		
		return member.register == null ? false : year > 2018;
	}
	
	
	@Test
	public void test() {
		DateTime day = new DateTime();
		
		System.out.println(day.toString("yyyy"));
		
	}
	
	
	class Member{
		
		DateTime register;
	}
	
	
	
}
