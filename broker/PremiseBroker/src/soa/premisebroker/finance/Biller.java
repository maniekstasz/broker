package soa.premisebroker.finance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.Bill;
import soa.premisebroker.model.BillItem;
import soa.premisebroker.model.PriceDictionary;
import soa.premisebroker.model.PriceDictionary.Product;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.BillRepository;
import soa.premisebroker.repository.PriceDictionaryRepository;

public class Biller {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private BidderRepository bidderRepository;

	@Autowired
	private PriceDictionaryRepository priceDictionaryRepository;

	public Bill getBillForBidderVerification(Bidder bidder) {
		BillItem billItem = new BillItem("Account verification", new Double(1));
		Bill bill = new Bill(bidder, Arrays.asList(billItem));
		bill = billRepository.save(bill);
		return bill;
	}

	public List<Bill> getMonthlyBills(List<MonthlyBillData> billsData) {
		PriceDictionary resDict = priceDictionaryRepository
				.findByProduct(Product.RESERVATION);
		PriceDictionary offerDict = priceDictionaryRepository
				.findByProduct(Product.OFFER);
		List<Bill> bills = new ArrayList<Bill>(billsData.size());
		for (MonthlyBillData billData : billsData) {
			BillItem resItem = new BillItem(billData.getResCnt()
					+ " rezerwacji", billData.getResCnt()*resDict.getPrice());
			BillItem offerItem = new BillItem(billData.getOffersCnt()
					+ " ofert", billData.getOffersCnt()*offerDict.getPrice());
			Bidder bidder = bidderRepository.findOne(billData.getBidderId());
			Bill bill = new Bill(bidder, Arrays.asList(resItem, offerItem));
			bills.add(bill);
			bill = billRepository.save(bill);
		}
		return bills;
	}

}
