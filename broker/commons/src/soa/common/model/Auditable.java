package soa.common.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
public class Auditable extends AbstractPersistable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7157301856126851092L;

	@ManyToOne(optional = false)
	@JoinColumn(name = "created_by")
	@CreatedBy
	private User createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	@CreatedDate
	private Date createdDate;

	@ManyToOne(optional = false)
	@JoinColumn(name = "last_modified_by")
	@LastModifiedBy
	private User lastModifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date", nullable = false)
	@LastModifiedDate
	private Date lastModifiedDate;

	public User getCreatedBy() {

		return createdBy;
	}

	public void setCreatedBy(final User createdBy) {

		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getLastModifiedBy() {

		return lastModifiedBy;
	}

	public void setLastModifiedBy(final User lastModifiedBy) {

		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(final Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
