package soa.common.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AbstractOffer extends AbstractPersistable<Long>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5930938077986015488L;
	

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinTable(name = "offers_pic_paths_map")
	private List<PicPath> picPaths;


	@NotNull
	@Column(length=1024)
	private String description;
	
	@NotNull
	@CreatedDate
	@Column(name="created_date")
	private Date createdDate;
	
	@NotNull
	@LastModifiedDate
	@Column(name="last_modified_date")
	private Date lastModifiedDate;
	

	public List<PicPath> getPicPaths() {
		return picPaths;
	}

	public void setPicPaths(List<PicPath> picPaths) {
		this.picPaths = picPaths;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

}
