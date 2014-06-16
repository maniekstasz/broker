package soa.premisebroker.quartz.job;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lowagie.text.DocumentException;

import soa.common.finance.Invoice;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.finance.Biller;
import soa.premisebroker.finance.Invoicer;
import soa.premisebroker.finance.MonthlyBillData;
import soa.premisebroker.model.Bill;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.BillRepository;
import soa.premisebroker.repository.PriceDictionaryRepository;

public class InvoiceSendJob extends QuartzJobBean {

	@Autowired
	private BidderRepository bidderRepository;

	@Autowired
	private BidderRequester bidderRequester;

	@Autowired
	private Biller biller;

	@Autowired
	private Invoicer invoicer;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private PriceDictionaryRepository priceDictionaryRepository;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		Date previosJobDate = context.getPreviousFireTime();
		if (previosJobDate == null)
			previosJobDate = new Date();
		List<MonthlyBillData> billsData = bidderRepository
				.getDataForMonthlyBill(previosJobDate);
		List<Bill> bills = biller.getMonthlyBills(billsData);
		List<Invoice> invoices = invoicer.getInvoices(bills, "Monthly invoice");
		for (int i = 0; i < billsData.size(); i++) {
			if (billsData.get(i).getUri() != null)
				bidderRequester.sendInvoice(billsData.get(i).getUri(),
						invoices.get(i));
			else
				try {
					bidderRequester.sendEmailWithInvoice(billsData.get(i)
							.getEmail(), invoices.get(i));
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
