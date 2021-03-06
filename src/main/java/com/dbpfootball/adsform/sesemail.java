package com.dbpfootball.adsform;

import java.io.IOException;
import com.amazonaws.services.simpleemail.*;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.regions.*;

public class sesemail {

    // Replace sender@example.com with your "From" address.
    // This address must be verified with Amazon SES.
    static final String FROM = "bweschke@btwtech.com";

  // Replace recipient@example.com with a "To" address. If your account
  // is still in the sandbox, this address must be verified.
  static final String TO = "bweschke@gmail.com";

  // The configuration set to use for this email. If you do not want to use a
  // configuration set, comment the next line and line 60.
  // static final String CONFIGSET = "ConfigSet";

  // The subject line for the email.
  static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";
  
  // The HTML body for the email.
  static final String HTMLBODY = "<h1>Amazon SES test (AWS SDK for Java)</h1>"
      + "<p>This email was sent with <a href='https://aws.amazon.com/ses/'>"
      + "Amazon SES</a> using the <a href='https://aws.amazon.com/sdk-for-java/'>" 
      + "AWS SDK for Java</a>";

  // The email body for recipients with non-HTML email clients.
  static final String TEXTBODY = "This email was sent through Amazon SES "
      + "using the AWS SDK for Java.";


  public void sendEmail(String emailTo, String emailBody, String emailSubject) throws IOException {

    try {
      AmazonSimpleEmailService client = 
          AmazonSimpleEmailServiceClientBuilder.standard()
          // Replace US_WEST_2 with the AWS Region you're using for
          // Amazon SES.
            .withRegion(Regions.US_EAST_1).build();
      SendEmailRequest request = new SendEmailRequest()
          .withDestination(
              new Destination().withToAddresses(emailTo))
          .withMessage(new Message()
              .withBody(new Body()
                  .withText(new Content()
                      .withCharset("UTF-8").withData(emailBody)))
              .withSubject(new Content()
                  .withCharset("UTF-8").withData(emailSubject)))
          .withSource(FROM);
          // Comment or remove the next line if you are not using a
          // configuration set
          //.withConfigurationSetName(CONFIGSET);
      client.sendEmail(request);
      System.out.println("Email sent!");
    } catch (Exception ex) {
      System.out.println("The email was not sent. Error message: " 
          + ex.getMessage());
    }
  }
}