package soa.common.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

@MappedSuperclass
public class AbstractPremise extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8406542761268395927L;
	
	
	@NotNull
	@Column(name = "persons_number")
	private Integer personsNumber;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinTable(name = "premises_pic_paths_map")
	private List<PicPath> picPaths;

	@NotNull
	private String description;

	@NotNull
	private Double price;

	public Integer getPersonsNumber() {
		return personsNumber;
	}

	public void setPersonsNumber(Integer personsNumber) {
		this.personsNumber = personsNumber;
	}

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
