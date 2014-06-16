package soa.premisebroker.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Bill;
import soa.premisebroker.model.Bill.BillStatus;
import soa.premisebroker.model.Payment;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.BillRepository;

public class PaymentEventHandler {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BidderRepository bidderRepository;

	@HandleAfterCreate
	public void afterCreate(Payment payment) {
		Long billId = new Long(payment.getBillId());
		Bill bill = billRepository.findOne(billId);
		if (bill == null)
			throw new IllegalStateException("Bill with id: " + " not found");
		if(payment.getTitle().startsWith("Account verification"))
			verifyBidder(bill, payment);
		
		bill.setStatus(BillStatus.PAID);
		billRepository.save(bill);
	}

	private void verifyBidder(Bill bill, Payment payment) {
		Bidder bidder = bidderRepository.findOne(bill.getBidder().getId());
		if (bidder.getAccountNr().equals(payment.getAccountNr())) {
			bidder.setVerified(true);
			bidderRepository.save(bidder);
		}
	}
	
}
