package soa.biddersystem.controller;

import soa.common.webservices.response.ReservationRequestResponse;

public class ReservationDeclinedException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4675918989448239818L; 
	
	private ReservationRequestResponse reservationRequestResponse;

	public ReservationDeclinedException(
			ReservationRequestResponse reservationRequestResponse) {
		super();
		this.reservationRequestResponse = reservationRequestResponse;
	}

	public ReservationRequestResponse getReservationRequestResponse() {
		return reservationRequestResponse;
	}

	public void setReservationRequestResponse(
			ReservationRequestResponse reservationRequestResponse) {
		this.reservationRequestResponse = reservationRequestResponse;
	}
	
	

}
