package soa.premisebroker.controller;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.Bank.Receipt;
import org.quartz.Calendar;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tempuri.BankLocator;
import org.tempuri.IBank;

import soa.premisebroker.eventhandlers.PaymentEventHandler;
import soa.premisebroker.finance.PaymentService;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Offer;
import soa.premisebroker.model.Payment;
import soa.premisebroker.model.Payment.PaymentType;
import soa.premisebroker.quartz.job.DebtCollectorSendJob;
import soa.premisebroker.repository.PaymentRepository;

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
