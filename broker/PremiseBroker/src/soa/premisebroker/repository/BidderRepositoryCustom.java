package soa.premisebroker.repository;

import java.util.Date;
import java.util.List;

import soa.premisebroker.finance.MonthlyBillData;

public interface BidderRepositoryCustom {
	
	public List<MonthlyBillData> getDataForMonthlyBill(Date date);
}
