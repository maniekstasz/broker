package soa.premisebroker.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import soa.premisebroker.eventhandlers.PaymentEventHandler;
import soa.premisebroker.extern.BankRequester;
import soa.premisebroker.model.Payment;
import soa.premisebroker.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private BankRequester bankRequester;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private PaymentEventHandler paymentEventHandler;

	@Async
	public void handlePayment(String wireId) {
		Payment payment = bankRequester.getPayment(wireId);
		if (payment != null) {
			paymentRepository.save(payment);
			paymentEventHandler.afterCreate(payment);
		}
	}
}
