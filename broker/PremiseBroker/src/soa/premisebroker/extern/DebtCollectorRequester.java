package soa.premisebroker.extern;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.tempuri.DebtCollectorLocator;
import org.tempuri.IDebtCollector;

import soa.premisebroker.model.Address;
import soa.premisebroker.model.DebtCollectorDto;
import soa.premisebroker.quartz.job.DebtCollectorSendJob;

@Service
public class DebtCollectorRequester {

	@Autowired
	private DebtCollectorLocator debtCollectorLocator;

	@Autowired
	private Environment env;

	@Async
	public void sendDebts(DebtCollectorDto debtData) {
		IDebtCollector client;
		try {
			client = debtCollectorLocator.getBasicHttpBinding_IDebtCollector();
			client.debtCollectionRegister(env
					.getProperty("debtCollector.login"), env
					.getProperty("debtCollector.password"), debtData
					.getAmount(), debtData.getFirstName(), debtData
					.getLastName(), debtData.getAddress().getCity(), debtData
					.getAddress().getStreet(), debtData.getAddress().getZip(),
					debtData.getAddress().getHomeNr());
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
