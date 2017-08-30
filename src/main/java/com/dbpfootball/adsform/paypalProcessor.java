package com.dbpfootball.adsform;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.dbpfootball.adsform.domain.formsubmission;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class paypalProcessor {

	String PAYPAL_CLIENT_ID = "";
	String PAYPAL_CLIENT_SECRET = "";

	public paypalProcessor(String clientId, String clientSecret) {
		PAYPAL_CLIENT_ID = clientId;
		PAYPAL_CLIENT_SECRET = clientSecret;
	}
	
	public Map<String, String> sendFormPayment(formsubmission formIn) {
		
		Address billingAddress = new Address();
		billingAddress.setCity(formIn.getCccity());
		billingAddress.setCountryCode("US");
		billingAddress.setLine1(formIn.getCcaddress());
		billingAddress.setPostalCode(formIn.getCczip());
		billingAddress.setState(formIn.getCcstate());
		
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setExpireMonth(Integer.parseInt(formIn.getCcexpmo()));
		creditCard.setExpireYear(Integer.parseInt(formIn.getCcexpyr()));
		creditCard.setFirstName(formIn.getCcfname());
		creditCard.setLastName(formIn.getCclname());
		creditCard.setNumber(formIn.getCcnum());
		creditCard.setCvv2(formIn.getCccvv());
		creditCard.setType(formIn.getCctype());
		
		Details details = new Details();
		details.setShipping("0");
		details.setTax("0");
		details.setSubtotal(formIn.getAdtypevalue());
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(formIn.getAdtypevalue());
		amount.setDetails(details);
		
		// Transaction details
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
		  .setDescription("This is the payment transaction description.");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Set funding instrument
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);
		
		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		// Set payer details
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");
		
		Map<String, String> response = new HashMap<String, String>();
		
		
		try {
			  // Create payment
			
			   APIContext PAYPAL_CONTEXT = new APIContext(PAYPAL_CLIENT_ID, PAYPAL_CLIENT_SECRET, "live");
			
			   Payment payment = new Payment();
			   payment.setIntent("sale");
			   payment.setPayer(payer);
			   payment.setTransactions(transactions);
			   Payment createdPayment = payment.create(PAYPAL_CONTEXT);
			   System.out.println("Created payment with id = " + createdPayment.getId());
			   response.put("result", "success");
			   response.put("paymentid", createdPayment.getId());
			   return response;
			   
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			   response.put("result", "failure");
			   response.put("errormsg", e.getDetails().toString());
			   return response;
			}		
		
		
	}
	
	public String sendPayment() {
		
		// Set address info
		Address billingAddress = new Address();
		billingAddress.setCity("Secaucus");
		billingAddress.setCountryCode("US");
		billingAddress.setLine1("1088 Farm Road");
		billingAddress.setPostalCode("07094");
		billingAddress.setState("OH");

		// Credit card info
		CreditCard creditCard = new CreditCard();
		creditCard.setBillingAddress(billingAddress);
		creditCard.setExpireMonth(11);
		creditCard.setExpireYear(2018);
		creditCard.setFirstName("BJ");
		creditCard.setLastName("Weschke");
		creditCard.setNumber("4669424246660779");
		// creditCard.setCvv2(cvv2);
		creditCard.setType("visa");

		// Payment details
		Details details = new Details();
		details.setShipping("1");
		details.setSubtotal("5");
		details.setTax("1");

		// Total amount
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal("7");
		amount.setDetails(details);

		// Transaction details
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction
		  .setDescription("This is the payment transaction description.");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		// Set funding instrument
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setCreditCard(creditCard);
		
		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		// Set payer details
		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");		
		
		try {
			  // Create payment
			
			   APIContext PAYPAL_CONTEXT = new APIContext(PAYPAL_CLIENT_ID, PAYPAL_CLIENT_SECRET, "live");
			
			   Payment payment = new Payment();
			   payment.setIntent("sale");
			   payment.setPayer(payer);
			   payment.setTransactions(transactions);
			   Payment createdPayment = payment.create(PAYPAL_CONTEXT);
			   System.out.println("Created payment with id = " + createdPayment.getId());
			   return createdPayment.getId();
			   
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			  return e.getDetails().toString();
			}		
		
	}

}
