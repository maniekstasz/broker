package soa.premisebroker.eventhandlers;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.web.client.HttpClientErrorException;
import com.lowagie.text.DocumentException;

import soa.common.finance.Invoice;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.finance.Biller;
import soa.premisebroker.finance.Invoicer;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.BidderWebHook;
import soa.premisebroker.model.Bill;
import soa.premisebroker.model.BidderWebHook.WebHooType;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.BillRepository;

@RepositoryEventHandler(Bidder.class)
public class BidderEventHandler {

	@Autowired
	private Biller biller;

	@Autowired
	private Invoicer invoicer;

	@Autowired
	private BidderRepository bidderRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BidderRequester bidderRequester;

	@HandleAfterCreate
	public void handleAfterBidderCreate(Bidder bidder)
			throws MessagingException, DocumentException {
		Bill bill = biller.getBillForBidderVerification(bidder);
		Invoice invoice = invoicer.getInvoice(bill, "Account verification");
		BidderWebHook webHook = bidderRepository.getBidderWebHook(
				bidder.getId(), WebHooType.INVOICE_SEND);
		if (webHook != null)
			try {
				bidderRequester.sendInvoice(webHook.getUri(), invoice);
			} catch (HttpClientErrorException e) {
				bidderRequester.sendEmailWithInvoice(bidder.getCreatedBy()
						.getCredentials().getMail(), invoice);
			}
		else
			bidderRequester.sendEmailWithInvoice(bidder.getCreatedBy()
					.getCredentials().getMail(), invoice);
	}

}
