package com.qtpselenium.zoho.project.mail;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail

{ 
	public static void main(String[] args) 
	{
		Properties pro = new Properties();
		try {
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "//src//test//resources//projectconfig.properties");
			pro.load(fs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// report folder - extent reports
		// date
		String reportFolder = pro.getProperty("reportFolder");
		@SuppressWarnings("unused")
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		FileFilterDateIntervalUtils filter = new FileFilterDateIntervalUtils("2010-01-04", "2050-01-20");
		File folder = new File(reportFolder);
		File files[] = folder.listFiles(filter);
		// date

		String fileName = files[files.length - 1].getName();
		String extentFilePath = reportFolder + fileName;
		String xsltReportPath = reportFolder + "Reports.zip";

		// mail extent reports
		String[] to = { "miraalmamun@gmail.com" };

		String[] cc = {};
		String[] bcc = {};

		// This is for yahoo

		sendMail("miralmamun@yahoo.com", "Mimo949658", "smtp.mail.yahoo.com", "25", "true", "true", true,
				"javax.net.ssl.SSLSocketFactory", "false", to, cc, bcc, "Automation Test Reports - Extent",
				"Please find the reports attached.\n\n Regards\nWebMaster", extentFilePath, fileName);

		// mail the xslt reports
		Zipp.zipDir(System.getProperty("user.dir") + "//XSLT_Reports", xsltReportPath);
		SendMail.sendMail("vaibhavcool12312@yahoo.com", "Pass@123", "smtp.mail.yahoo.com", "25", "true", "true", true,
				"javax.net.ssl.SSLSocketFactory", "false", to, cc, bcc, "Automation Test Reports - XSLT",
				"Please find the reports attached.\n\n Regards\nWebMaster", xsltReportPath, "Reports.zip");

	}

	public static boolean sendMail(final String userName, final String passWord, String host, String port,
			String starttls, String auth, boolean debug, String socketFactoryClass, String fallback, String[] to,
			String[] cc, String[] bcc, String subject, String text, String attachmentPath, String attachmentName) {

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", starttls);
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		try

		{

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, passWord);
				}
			});

			MimeMessage msg = new MimeMessage(session);

			msg.setText(text);

			msg.setSubject(subject);
			// attachment start
			// create the message part

			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachmentName);
			multipart.addBodyPart(messageBodyPart);

			// attachment ends

			// Put parts in message
			msg.setContent(multipart);
			msg.setFrom(new InternetAddress(userName));

			for (int i = 0; i < to.length; i++) {

				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));

			}

			for (int i = 0; i < cc.length; i++) {

				msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));

			}

			for (int i = 0; i < bcc.length; i++) {

				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));

			}

			msg.saveChanges();

			Transport transport = session.getTransport("smtp");

			transport.connect(host, userName, passWord);

			transport.sendMessage(msg, msg.getAllRecipients());

			transport.close();

			return true;

		}

		catch (Exception mex)

		{

			mex.printStackTrace();

			return false;

		}

	}

}
