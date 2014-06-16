package soa.biddersystem.finance;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.Bank.WireResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tempuri.BankLocator;
import org.tempuri.IBank;

import soa.common.finance.Invoice;

@Service
public class PaymentService {

	@Autowired
	private Environment env;

	@Autowired
	private BankLocator bl;

	@Async
	public void pay(Invoice invoice) {
		try {
			IBank client = bl.getBasicHttpBinding_IBank();
			WireResult result = client.wireTransfer(env.getProperty("bank.login"),
					env.getProperty("bank.password"), invoice.getAccountNr(),
					invoice.getTitle(), invoice.getAmount());
		} catch (ServiceException e) {
			// ignore
			e.printStackTrace();
		} catch (RemoteException e) {
			// ignore
			e.printStackTrace();
		}

	}
}
