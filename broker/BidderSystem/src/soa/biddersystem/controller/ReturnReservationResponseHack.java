package soa.biddersystem.controller;

import soa.common.webservices.response.ReservationRequestResponse;

public class ReturnReservationResponseHack extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1480205307937177828L;

	private ReservationRequestResponse reservationRequestResponse;

	public ReturnReservationResponseHack(
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
