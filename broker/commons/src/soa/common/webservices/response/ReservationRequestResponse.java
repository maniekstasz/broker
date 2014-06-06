package soa.common.webservices.response;

import soa.common.model.AbstractReservation.ReservationStatus;

public class ReservationRequestResponse {

	private ReservationStatus status;
	private String bidderNotes;

	public ReservationRequestResponse(ReservationStatus status,
			String bidderNotes) {
		super();
		this.status = status;
		this.bidderNotes = bidderNotes;
	}
	
	public ReservationRequestResponse(){
		
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public String getBidderNotes() {
		return bidderNotes;
	}

	public void setBidderNotes(String bidderNotes) {
		this.bidderNotes = bidderNotes;
	}
}
