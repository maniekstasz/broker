package soa.premisebroker.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import soa.common.model.AbstractReservation.ReservationStatus;
import soa.premisebroker.finance.MonthlyBillData;

public class BidderRepositoryImpl implements BidderRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<MonthlyBillData> getDataForMonthlyBill(Date date) {
		Query query = entityManager
				.createNativeQuery(
						"select  bidder.id as id, user.mail as email, wh.uri as uri,"
								+ " (select count(*) from offers where bidder_id = bidder.id) as offersCnt,"
								+ " (select count(*) from premise_reservations res left join premises premise on res.premise_id = premise.id"
								+ " left join offers offer on premise.offer_id = offer.id "
								+ " where res.status = :status and res.createdDate > :date and offer.bidder_id = bidder.id ) as resCnt"
								+ " from bidders bidder left join users user on bidder.createdBy_id = user.id left join bidder_web_hook wh on  bidder.id = wh.bidder_id");
		
		
		query.setParameter("status", ReservationStatus.AWAITANING);
		query.setParameter("date", date);
		List<Object[]> data = query.getResultList();
		List<MonthlyBillData> billsData = new ArrayList<MonthlyBillData>(data.size());
		for(Object object[]: data)
			billsData.add(new MonthlyBillData(object));
//		return (List<MonthlyBillData>) query.getResultList();
		return billsData;
	}
}
