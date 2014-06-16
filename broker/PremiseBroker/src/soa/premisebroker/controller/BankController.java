package soa.premisebroker.controller;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import soa.premisebroker.finance.PaymentService;

@Controller
public class BankController {





@Autowired
private PaymentService paymentService;

	@RequestMapping(value = "/wire/notification", method = RequestMethod.POST)
	@ResponseBody
	public void post(@RequestParam("id") String wireId)
			throws ServiceException, RemoteException {
		paymentService.handlePayment(wireId);
	}
}
