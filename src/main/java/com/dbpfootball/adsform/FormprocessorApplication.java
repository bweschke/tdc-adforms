package com.dbpfootball.adsform;

import javax.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.ApplicationContext;

import ch.qos.logback.classic.Logger;

@SpringBootApplication
public class FormprocessorApplication {

	private Logger logger = (Logger) LoggerFactory.getLogger(FormprocessorApplication.class);
	
	public static void main(String[] args) {
		
		//SpringApplication.run(FormprocessorApplication.class, args);

		SpringApplication springApplication = new SpringApplication(FormprocessorApplication.class);
		springApplication.addListeners(new ApplicationPidFileWriter("formprocessor.pid"));
		
		ApplicationContext ctx = springApplication.run(args);		
		
	}
	
	@PreDestroy
	public void cleanUp() {
		logger.info("APP cleanUP Called");
	}	
	
}
