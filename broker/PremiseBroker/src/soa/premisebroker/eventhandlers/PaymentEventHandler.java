package soa.premisebroker.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Bill;
import soa.premisebroker.model.Payment;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.BillRepository;

@RepositoryEventHandler(Payment.class)
public class PaymentEventHandler {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BidderRepository bidderRepository;

	@HandleAfterCreate
	public void afterCreate(Payment payment) {
		Long billId = new Long(payment.getTitle());
		Bill bill = billRepository.findOne(billId);
		if (bill != null) {
			Bidder bidder = bidderRepository.findOne(bill.getBidder().getId());
			if (bidder.getAccountNr().equals(payment.getAccountNr())) {
				bidder.setVerified(true);
				bidderRepository.save(bidder);
			} else {
				// TODO: send email
			}
		} else {
			throw new IllegalStateException("Bill with id: " + billId
					+ " not found");
		}
	}
}
