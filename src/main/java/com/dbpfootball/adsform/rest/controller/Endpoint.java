package com.dbpfootball.adsform.rest.controller;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dbpfootball.adsform.paypalProcessor;

@Component
@Path("/ws-sender")
public class Endpoint {
		
		private Logger logger = LoggerFactory.getLogger(Endpoint.class);	
		
		@GET
		public String message() {
			paypalProcessor ppal = new paypalProcessor();
			ppal.sendPayment();
			return "Hiiiiiiiiiiiiiiiiiiiiiiiiii! ";
		}	
	
}
