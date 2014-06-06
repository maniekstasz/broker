package soa.premisebroker.extern;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import com.lowagie.text.DocumentException;

import soa.common.finance.Invoice;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.common.webservices.response.ReservationRequestResponse;
import soa.premisebroker.finance.Invoicer;
import soa.premisebroker.model.PremiseReservation;

public class BidderRequester {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SecurityEventHandlerSupportBean securityEventHandlerSupportBean;

	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private Environment env;
	
	
	@Autowired
	private Invoicer invoicer;
	
	
	
	public void sendInvoice(String uri, Invoice invoice) {
		HttpEntity<Invoice> entity = securityEventHandlerSupportBean.addAuthenticationHeader(invoice, "password","premisebroker");
		restTemplate.postForLocation(uri, entity);
	}

	public ReservationRequestResponse sendReservationRequest(String uri,
			PremiseReservation reservation) {
		HttpEntity<PremiseReservation> entity = securityEventHandlerSupportBean.addAuthenticationHeader(reservation,  "password","premisebroker");
		ReservationRequestResponse response = restTemplate.postForObject(uri,
				entity, ReservationRequestResponse.class);
		return response;
	}
	
	public void sendReservationsRequestsEmail(String email, List<PremiseReservation> reservations){
		System.out.println("Send email");
		//TODO: send email
	}
	
	public void sendEmailWithInvoice(String email, Invoice invoice) throws MessagingException, DocumentException{
		MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setText("Faktura");
         
        //now write the PDF content to the output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        invoicer.writeInvoicePdf(outputStream, invoice);
        byte[] bytes = outputStream.toByteArray();
         
        //construct the pdf body part
        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("test.pdf");
                     
        //construct the mime multi part
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(textBodyPart);
        mimeMultipart.addBodyPart(pdfBodyPart);
         
        //create the sender/recipient addresses
        InternetAddress iaSender = new InternetAddress(env.getProperty("mail.username"));
        InternetAddress iaRecipient = new InternetAddress(email);
         
        //construct the mime message
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setSender(iaSender);
        mimeMessage.setSubject("Faktura");
        mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
        mimeMessage.setContent(mimeMultipart);
		mailSender.send(mimeMessage);
	}
	
	
}
