package soa.premisebroker.quartz.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import soa.premisebroker.extern.DebtCollectorRequester;
import soa.premisebroker.finance.MonthlyBillData;
import soa.premisebroker.model.Bill;
import soa.premisebroker.model.DebtCollectorDto;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.BillRepository;

public class DebtCollectorSendJob extends QuartzJobBean {

	@Autowired
	private BidderRepository bidderRepository;
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private DebtCollectorRequester collectorRequester;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		Date previosJobDate = context.getPreviousFireTime();
		if (previosJobDate == null)
			previosJobDate = new Date();
		List<Bill> bills = billRepository.getDueBills(new Date(), previosJobDate);
		Map<Long, DebtCollectorDto> debtCollectorData = prepareData(bills);
		for(DebtCollectorDto debtData: debtCollectorData.values())
			collectorRequester.sendDebts(debtData);
		
	}
	
	
	
	private Map<Long, DebtCollectorDto> prepareData(List<Bill> bills){
		Map<Long, DebtCollectorDto> debtCollectorData = new HashMap<Long, DebtCollectorDto>();
		for(Bill bill: bills){
			Long bidderId =  bill.getBidder().getId();
			if(!debtCollectorData.containsKey(bidderId))
				debtCollectorData.put(bidderId, new DebtCollectorDto(bill.getBidder(), bill.getAmount()));
			else
				debtCollectorData.get(bidderId).addToAmount(bill.getAmount());
		}
		return debtCollectorData;
	}

}
