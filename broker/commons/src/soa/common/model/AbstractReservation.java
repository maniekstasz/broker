package soa.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class AbstractReservation<U extends AbstractUser> extends Auditable<U > {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3879832757693849919L;

	public enum ReservationStatus {
		ACCEPTED, DECLINED, CANCELED, AWAITANING
	};

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(name = "reserved_from")
	private Date reservedFrom;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Column(name = "reserved_to")
	private Date reservedTo;

	@NotNull
	private ReservationStatus status = ReservationStatus.AWAITANING;

	@Column(name = "user_notes")
	private String userNotes;

	@Column(name = "bidder_notes")
	private String bidderNotes;

//	@NotNull
//	private Double price;
//
//	public Double getPrice() {
//		return price;
//	}
//
//	public void setPrice(Double price) {
//		this.price = price;
//	}

	public Date getReservedFrom() {
		return reservedFrom;
	}

	public void setReservedFrom(Date from) {
		this.reservedFrom = from;
	}

	public Date getReservedTo() {
		return reservedTo;
	}

	public void setReservedTo(Date to) {
		this.reservedTo = to;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public String getUserNotes() {
		return userNotes;
	}

	public void setUserNotes(String userNotes) {
		this.userNotes = userNotes;
	}

	public String getBidderNotes() {
		return bidderNotes;
	}

	public void setBidderNotes(String bidderNotes) {
		this.bidderNotes = bidderNotes;
	}

}
