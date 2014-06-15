package soa.premisebroker.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="users_favourite_offers",uniqueConstraints={@UniqueConstraint(columnNames={"user_id", "offer_id"})})
@EntityListeners({ AuditingEntityListener.class })
public class FavouriteOffer extends AbstractPersistable<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2079788174521070984L;

	@CreatedBy
	@ManyToOne(optional=false)
	private User user;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	private Offer offer;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

}
