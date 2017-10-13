package com.dbpfootball.adsform.rest.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dbpfootball.adsform.paypalProcessor;
import com.dbpfootball.adsform.sesemail;
import com.dbpfootball.adsform.domain.formsubmission;
import com.dbpfootball.adsform.domain.vvinesformsubmission;

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

		@POST
		@Path("/vvines")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Map<String, String> processVVinesForm(vvinesformsubmission formIn) {
			Map<String, String> response = new HashMap<String, String>();

			formsubmission ppalformIn = new formsubmission();
			ppalformIn.setAddress(formIn.getCcaddress());
			ppalformIn.setCccity(formIn.getCccity());
			ppalformIn.setCczip(formIn.getCczip());
			ppalformIn.setCcstate(formIn.getCcstate());
			ppalformIn.setCcexpmo(formIn.getCcexpmo());
			ppalformIn.setCcexpyr(formIn.getCcexpyr());
			ppalformIn.setCcfname(formIn.getCcfname());
			ppalformIn.setCclname(formIn.getCclname());
			ppalformIn.setCcnum(formIn.getCcnum().replaceAll("\\D+",""));
			ppalformIn.setCccvv(formIn.getCccvv());
			ppalformIn.setCctype(formIn.getCctype());
			ppalformIn.setAdtypevalue(formIn.getTotalvalue());
			
			response = ppal.sendFormPayment(ppalformIn);
			
			sesemail emailSender = new sesemail();
			
			try {
				//emailSender.sendEmail();
				
				String emailSubject = "DBP Vineyard Vines Order Submission - " +formIn.getContactname()+" - PayPal Result: "+response.get("result");
				String emailBody = 
									"Contact Name: "+formIn.getContactname()+"\n"+
									"Address: "+formIn.getAddress()+"\n"+
									"Email: "+formIn.getEmail()+"\n"+
									"Phone: "+formIn.getPhone()+"\n"+
									"Amount Charged $: "+formIn.getTotalvalue()+"\n"+
									"Credit Card First Name: "+formIn.getCcfname()+"\n"+
									"Credit Card Last Name: "+formIn.getCclname()+"\n"+
									"Credit Card Address: "+formIn.getCcaddress()+"\n"+
									"Credit Card City: "+formIn.getCccity()+"\n"+
									"Credit Card State: "+formIn.getCcstate()+"\n"+
									"Credit Card Zip: "+formIn.getCczip()+"\n"+
									"Credit Card Type: "+formIn.getCctype()+"\n"+
									"Credit Card Last 4: "+formIn.getCcnum().substring(formIn.getCcnum().length() - 4)+"\n"+
									"Credit Card Expiration Month: "+formIn.getCcexpmo()+"\n"+
									"Credit Card Expiration Year: "+formIn.getCcexpyr()+"\n\n";
				
				JSONObject jso = new JSONObject();
				jso.put("conactname", formIn.getContactname());
				jso.put("email", formIn.getEmail());
				jso.put("phone", formIn.getPhone());
				
				jso.put("tie1qty", formIn.getTie1qty());
				jso.put("tie2qty", formIn.getTie2qty());
				jso.put("hat1qty", formIn.getHat1qty());
				jso.put("hat2qty", formIn.getHat2qty());

				jso.put("belt1size", formIn.getBelt1size());
				jso.put("belt1qty", formIn.getBelt1qty());
				jso.put("belt2size", formIn.getBelt2size());
				jso.put("belt2qty", formIn.getBelt2qty());
				jso.put("belt3size", formIn.getBelt3size());
				jso.put("belt3qty", formIn.getBelt3qty());
				
				jso.put("mshep1size", formIn.getMshep1size());
				jso.put("mshep1qty", formIn.getMshep1qty());
				jso.put("mshep2size", formIn.getMshep2size());
				jso.put("mshep2qty", formIn.getMshep2qty());
				jso.put("mshep3size", formIn.getMshep3size());
				jso.put("mshep3qty", formIn.getMshep3qty());

				jso.put("wshep1size", formIn.getWshep1size());
				jso.put("wshep1qty", formIn.getWshep1qty());
				jso.put("wshep2size", formIn.getWshep2size());
				jso.put("wshep2qty", formIn.getWshep2qty());
				jso.put("wshep3size", formIn.getWshep3size());
				jso.put("wshep3qty", formIn.getWshep3qty());

				jso.put("qtrzipgrey1size", formIn.getQtrzipgrey1size());
				jso.put("qtrzipgrey1qty", formIn.getQtrzipgrey1qty());				
				jso.put("qtrzipgrey2size", formIn.getQtrzipgrey2size());
				jso.put("qtrzipgrey2qty", formIn.getQtrzipgrey2qty());				
				jso.put("qtrzipgrey3size", formIn.getQtrzipgrey3size());
				jso.put("qtrzipgrey3qty", formIn.getQtrzipgrey3qty());				

				jso.put("qtrzipblack1size", formIn.getQtrzipblack1size());
				jso.put("qtrzipblack1qty", formIn.getQtrzipblack1qty());				
				jso.put("qtrzipblack2size", formIn.getQtrzipblack2size());
				jso.put("qtrzipblack2qty", formIn.getQtrzipblack2qty());				
				jso.put("qtrzipblack3size", formIn.getQtrzipblack3size());
				jso.put("qtrzipblack3qty", formIn.getQtrzipblack3qty());				

				jso.put("tshirt1size", formIn.getTshirt1size());
				jso.put("tshirt1qty", formIn.getTshirt1qty());
				jso.put("tshirt2size", formIn.getTshirt2size());
				jso.put("tshirt2qty", formIn.getTshirt2qty());
				jso.put("tshirt3size", formIn.getTshirt3size());
				jso.put("tshirt3qty", formIn.getTshirt3qty());
				
				emailBody = emailBody + "\nJSON Order: "+jso.toString()+"\n\n";

				String asciiInsert = jso.toString() + "\n";
				try {
				    Files.write(Paths.get("/home/bweschke/vvinesorders.txt"), asciiInsert.getBytes(), StandardOpenOption.APPEND);
				}catch (IOException e) {

				}				
				
				if (response.get("result").equals("failure")) {
					emailBody = emailBody + "PayPal Error Message for Rejection: "+response.get("errormsg")+"\n";
				} else {
					emailBody = emailBody + "PayPal Success Payment ID: "+response.get("paymentid")+"\n";
				}
				
				emailSender.sendEmail("bweschke@btwtech.com", emailBody, emailSubject);
				emailSender.sendEmail("dbp.tdc.2017ads@gmail.com", emailBody, emailSubject);
				emailSender.sendEmail("v.kovanes@verizon.net", emailBody, emailSubject);
				emailSender.sendEmail("keenanclan5@aol.com", emailBody, emailSubject);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return response;
		}		
		
		
}
