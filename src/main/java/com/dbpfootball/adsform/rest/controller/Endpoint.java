package com.dbpfootball.adsform.rest.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.dbpfootball.adsform.domain.formsubmission;

@Component
@Path("/ws-sender")
public class Endpoint {
	
		@Autowired
		paypalProcessor ppal;
		
		private Logger logger = LoggerFactory.getLogger(Endpoint.class);	
		
		@GET
		public String message() {
			ppal.sendPayment();
			return "Hiiiiiiiiiiiiiiiiiiiiiiiiii! ";
		}	
		
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Map<String, String> processForm(formsubmission formIn) {
			Map<String, String> response = new HashMap<String, String>();

			response = ppal.sendFormPayment(formIn);
			
			return response;
		}		
	
}
