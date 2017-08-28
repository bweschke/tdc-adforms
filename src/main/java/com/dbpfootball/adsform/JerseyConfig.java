package com.dbpfootball.adsform;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.dbpfootball.adsform.rest.controller.Endpoint;

@Component
public class JerseyConfig extends ResourceConfig {

		public JerseyConfig() {
			register(Endpoint.class);
		}
		
	
}
