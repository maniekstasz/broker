package soa.common.model;

import java.io.Serializable;
import java.util.Date;

import soa.common.model.AbstractReservation.ReservationStatus;

public class PremiseReservationDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4252440985345360514L;
	
	
	private  Date reservedFrom;
	private  Date reservedTo;
	private  ReservationStatus status;
	private  String userNotes;
	private  String bidderNotes;
	private  Long premiseId;
	private Long foreignId;
	
	public PremiseReservationDto(Date reservedFrom, Date reservedTo,
			ReservationStatus status, String userNotes, String bidderNotes, Long premiseId, Long foreignId) {
		super();
		this.reservedFrom = reservedFrom;
		this.reservedTo = reservedTo;
		this.status = status;
		this.userNotes = userNotes;
		this.bidderNotes = bidderNotes;
		this.premiseId = premiseId;
		this.foreignId = foreignId;
	}
	
	public PremiseReservationDto(){
		
	}

	public Date getReservedFrom() {
		return reservedFrom;
	}

	public Date getReservedTo() {
		return reservedTo;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public String getUserNotes() {
		return userNotes;
	}

	public String getBidderNotes() {
		return bidderNotes;
	}

	public Long getPremiseId() {
		return premiseId;
	}

	public void setReservedFrom(Date reservedFrom) {
		this.reservedFrom = reservedFrom;
	}

	public void setReservedTo(Date reservedTo) {
		this.reservedTo = reservedTo;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}

	public void setBidderNotes(String bidderNotes) {
		this.bidderNotes = bidderNotes;
	}

	public void setPremiseId(Long premiseId) {
		this.premiseId = premiseId;
	}

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}
	
}
