package soa.biddersystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import soa.common.model.PicPath;


public class PremiseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5037169516184691028L;
	private Integer personsNumber;

	private List<PicPathDto> picPaths;

	private String description;

	private Double price;
	private String offer;

	public PremiseDto(Premise premise, String offerLocation, boolean addPicPaths){
		setDescription(premise.getDescription());
		setPersonsNumber(premise.getPersonsNumber());
		setPrice(premise.getPrice());
		offer = offerLocation;
		
		if (premise.getPicPaths() != null && addPicPaths) {
			this.picPaths = new ArrayList<PicPathDto>(premise.getPicPaths()
					.size());
			for (PicPath picPath : premise.getPicPaths())
				this.picPaths.add(new PicPathDto(picPath.getPicPath()));
		}
	}
	
	
	public String getOffer(){
		return offer;
	}
	
	public Integer getPersonsNumber() {
		return personsNumber;
	}

	public void setPersonsNumber(Integer personsNumber) {
		this.personsNumber = personsNumber;
	}

	public List<PicPathDto> getPicPaths() {
		return picPaths;
	}

	public void setPicPaths(List<PicPathDto> picPaths) {
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
