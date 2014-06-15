package soa.premisebroker.extern;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.Bank.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.tempuri.BankLocator;
import org.tempuri.IBank;

import soa.premisebroker.model.Payment;
import soa.premisebroker.model.Payment.PaymentType;

@Service
public class BankRequester {

	@Autowired
	private Environment env;

	@Autowired
	private BankLocator bankLocator;

	public Payment getPayment(String wireId) {
		Payment payment = null;
		try {
			IBank client;
			Receipt receipt = null;
			client = bankLocator.getBasicHttpBinding_IBank();
			
			receipt = client.getReceipt(env.getProperty("bank.login"),
					env.getProperty("bank.password"), wireId);
			if(receipt.getStatus() == -2)
				return null;
			payment = new Payment();
			payment.setAccountNr(receipt.getBanOfSender());
			payment.setTitle(receipt.getTitle());
			payment.setPaymentType(PaymentType.PAYMENT);
			payment.setAmount(receipt.getAmount());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payment;
	}
}
