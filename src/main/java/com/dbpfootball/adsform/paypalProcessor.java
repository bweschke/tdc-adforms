package com.dbpfootball.adsform;

import java.util.List;
import java.util.ArrayList;

import com.paypal.api.payments.Address;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class paypalProcessor {

	
	
	public void sendPayment() {

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
			
			   String SANDBOX_CLIENT_ID = "Abxa1gTxVTpf98OuXWTXFvqoPOqPx1mu4SRrfTvvBNE1B3Ia8cxXuJ5-yQ7mLLv9ldCHXU7PiNAfy82S";
			   String SANDBOX_CLIENT_SECRET = "ECYPxzKNCLUbPw-qQkF4laVOgFKGxiKbOP2up_bnhF6UjBd2D-IkLglsYpO7no7z4O5P6rc3a_5xRGYh";
			   APIContext SANDBOX_CONTEXT = new APIContext(SANDBOX_CLIENT_ID, SANDBOX_CLIENT_SECRET, "sandbox");
			
			   Payment payment = new Payment();
			   payment.setIntent("sale");
			   payment.setPayer(payer);
			   Payment createdPayment = payment.create(SANDBOX_CONTEXT);
			   System.out.println("Created payment with id = " + createdPayment.getId());
			} catch (PayPalRESTException e) {
			  System.err.println(e.getDetails());
			}		
		
	}

}
