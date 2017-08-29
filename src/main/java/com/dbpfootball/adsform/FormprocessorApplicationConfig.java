package com.dbpfootball.adsform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FormprocessorApplicationConfig {

	@Value("${paypal.clientid}")
	private String paypalClientID;
	@Value("${paypal.clientsecret}")
	private String paypalClientSecret;
	
	@Bean
	public paypalProcessor ppalClient() {
		paypalProcessor ppalClient = new paypalProcessor(paypalClientID, paypalClientSecret);
		return ppalClient;
	}
	
}
