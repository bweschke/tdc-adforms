package com.dbpfootball.adsform.rest.controller;

import java.io.IOException;
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
import com.dbpfootball.adsform.sesemail;
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
			
			sesemail emailSender = new sesemail();
			
			try {
				//emailSender.sendEmail();
				
				String emailSubject = "DBP Ad Submission - "+formIn.getBizorfamname()+", "+formIn.getContactname()+" - PayPal Result: "+response.get("result");
				String emailBody = "Family or Business Ad: "+formIn.getFamorbiz()+"\n"+
									"Business or Family Name: "+formIn.getBizorfamname()+"\n"+
									"Contact Name: "+formIn.getContactname()+"\n"+
									"Address: "+formIn.getAddress()+"\n"+
									"Email: "+formIn.getEmail()+"\n"+
									"Phone: "+formIn.getPhone()+"\n"+
									"Amount Charged $: "+formIn.getAdtypevalue()+"\n"+
									"Ad Type: "+formIn.getAdtypeid()+"\n"+
									"Player Credit: "+formIn.getPlayercredit()+"\n"+
									"Player Graduation Yr Credit: "+formIn.getPlayeryrcredit()+"\n"+
									"Credit Card First Name: "+formIn.getCcfname()+"\n"+
									"Credit Card Last Name: "+formIn.getCclname()+"\n"+
									"Credit Card Address: "+formIn.getCcaddress()+"\n"+
									"Credit Card City: "+formIn.getCccity()+"\n"+
									"Credit Card State: "+formIn.getCcstate()+"\n"+
									"Credit Card Zip: "+formIn.getCczip()+"\n"+
									"Credit Card Type: "+formIn.getCctype()+"\n"+
									"Credit Card Last 4: "+formIn.getCcnum().substring(formIn.getCcnum().length() - 4)+"\n"+
									"Credit Card Expiration Month: "+formIn.getCcexpmo()+"\n"+
									"Credit Card Expiration Year: "+formIn.getCcexpyr()+"\n";
				
				if (response.get("result").equals("failure")) {
					emailBody = emailBody + "PayPal Error Message for Rejection: "+response.get("errormsg")+"\n";
				} else {
					emailBody = emailBody + "PayPal Success Payment ID: "+response.get("paymentid")+"\n";
				}
				
				emailSender.sendEmail("bweschke@btwtech.com", emailBody, emailSubject);
				emailSender.sendEmail("dbp.tdc.2017ads@gmail.com", emailBody, emailSubject);
				emailSender.sendEmail("davebrennan@comast.net", emailBody, emailSubject);
				emailSender.sendEmail("schmidhauser.dbp.tdc@gmail.com", emailBody, emailSubject);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}		
	
}
