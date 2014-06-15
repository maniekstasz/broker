package soa.biddersystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
	public void handleInvoiceReceive(@RequestBody Invoice invoice){
		paymentService.pay(invoice);
	}
	
	@RequestMapping(value="/temp", method=RequestMethod.GET)
	@ResponseBody
	public void handleInvoiceReceive2(){
		Invoice invoice = new Invoice("424234", 234.0,"asdfa",null);
		paymentService.pay(invoice);
	}


}
