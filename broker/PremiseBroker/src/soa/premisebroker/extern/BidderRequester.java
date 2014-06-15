package soa.premisebroker.extern;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.lowagie.text.DocumentException;

import soa.common.finance.Invoice;
import soa.common.model.PremiseReservationDto;
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

	@Async
	public void sendInvoice(String uri, Invoice invoice) {
		HttpEntity<Invoice> entity = securityEventHandlerSupportBean
				.addAuthenticationHeader(invoice, "password", "premisebroker");
		restTemplate.postForLocation(uri, entity);
	}

	// public ReservationRequestResponse sendReservationRequest(String uri,
	// PremiseReservationDto reservation) {
	// HttpEntity<PremiseReservationDto> entity =
	// securityEventHandlerSupportBean
	// .addAuthenticationHeader(reservation, "password",
	// "premisebroker");
	// ReservationRequestResponse response = restTemplate.postForObject(uri,
	// entity, ReservationRequestResponse.class);
	// return response;
	// }

	public ReservationRequestResponse sendReservation(String uri,
			PremiseReservationDto reservation, boolean update) {
		HttpEntity<PremiseReservationDto> entity = securityEventHandlerSupportBean
				.addAuthenticationHeader(reservation, "password",
						"premisebroker");
		ResponseEntity<ReservationRequestResponse> response;
		try {
			if (update) {
				response = restTemplate.exchange(new URI(uri), HttpMethod.PUT,
						entity, ReservationRequestResponse.class);
				return response.getBody();
			} else {
				ReservationRequestResponse res = restTemplate.postForObject(
						uri, entity, ReservationRequestResponse.class);
				return res;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(HttpClientErrorException e){
			e.printStackTrace();
		}
		return null;
	}

	@Async
	public void sendReservationsRequestsEmail(String email,
			List<PremiseReservation> reservations) {
		SimpleMailMessage smm = new SimpleMailMessage();
		smm.setFrom(env.getProperty("mail.username"));
		smm.setTo(email);
		smm.setSubject("New reservations to approve");
		smm.setText("You have new reservations to approve in PremiseBroker");
		mailSender.send(smm);
	}

	@Async
	public void sendEmailWithInvoice(String email, Invoice invoice)
			throws MessagingException, DocumentException {
		MimeBodyPart textBodyPart = new MimeBodyPart();
		textBodyPart.setText("Faktura");

		// now write the PDF content to the output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		invoicer.writeInvoicePdf(outputStream, invoice);
		byte[] bytes = outputStream.toByteArray();

		// construct the pdf body part
		DataSource dataSource = new ByteArrayDataSource(bytes,
				"application/pdf");
		MimeBodyPart pdfBodyPart = new MimeBodyPart();
		pdfBodyPart.setDataHandler(new DataHandler(dataSource));
		pdfBodyPart.setFileName("test.pdf");

		// construct the mime multi part
		MimeMultipart mimeMultipart = new MimeMultipart();
		mimeMultipart.addBodyPart(textBodyPart);
		mimeMultipart.addBodyPart(pdfBodyPart);

		// create the sender/recipient addresses
		InternetAddress iaSender = new InternetAddress(
				env.getProperty("mail.username"));
		InternetAddress iaRecipient = new InternetAddress(email);

		// construct the mime message
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		mimeMessage.setSender(iaSender);
		mimeMessage.setSubject("Faktura");
		mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
		mimeMessage.setContent(mimeMultipart);
		mailSender.send(mimeMessage);
	}

}
