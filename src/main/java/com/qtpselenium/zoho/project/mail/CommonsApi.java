package com.qtpselenium.zoho.project.mail;


import org.apache.commons.mail.*;

public class CommonsApi 
{

	public static void main(String[] args) throws Exception 
	{
		/*
		System.out.println("Sending email");
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("miraalmamun@gmail.com", "Cpmm2013@ny"));
		email.setSSLOnConnect(true);
		email.setFrom("miraalmamun@gmail.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("miraalmamun@gmail.com");
		email.send();	
		System.out.println("Done !! sending email");
		
		*/
		
		
		sendMail();
		

	}
	
	
	public static void sendMail() throws Exception
	{
		
		
		  System.out.println("Sending the email");
		  EmailAttachment attachment = null;

		  // Create the attachment
		  attachment = new EmailAttachment();
		  attachment.setPath("C:\\Users\\Mir\\eclipse-workspace\\DataDriven_Zoho_April18\\reports\\GoogleApp_TestExecution.html");
		  //attachment = new EmailAttachment();
		  //attachment.setPath("C:\\Users\\Mir\\eclipse-workspace\\DataDriven_Zoho_April18\\ExtentReport\\Sat_Aug_03_23_08_05_EDT_2019.png");
		  attachment.setDisposition(EmailAttachment.ATTACHMENT);
		  attachment.setDescription("Screenshot of failed test case");
		  attachment.setName("Mir");

		  // Create the email message
		  MultiPartEmail email = new MultiPartEmail();
		  email.setHostName("smtp.gmail.com");
		    email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("miraalmamun@gmail.com", "Cpmm2013@ny"));
			email.setSSLOnConnect(true);
			email.setFrom("miraalmamun@gmail.com");
		  email.addTo("almamun.mir.qa@gmail.com", "Al Mamun Mir");
		  //email.setFrom("me@apache.org", "Me");
		  email.setSubject("The picture");
		  email.setMsg("Here is the picture you wanted");
		  
		  

		  // add the attachment
		  email.attach(attachment);

		  // send the email
		  email.send();
		  
		  System.out.println("Mail Done!!!");
		
	}

}
