package soa.biddersystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import soa.biddersystem.finance.PaymentService;
import soa.common.finance.Invoice;

@Controller
public class FinanceController {
	
	@Autowired
	private PaymentService paymentService;
	
	
	@RequestMapping(value="/invoice", method=RequestMethod.POST)
	@ResponseBody
	public void handleInvoiceReceive(Invoice invoice){
		paymentService.pay(invoice);
	}

}
