package soa.biddersystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import soa.common.model.PicPath;

public class OfferDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1920967894921781659L;

	public OfferDto(Offer offer, boolean addPicPaths) {
		this.description = offer.getDescription();
		if (offer.getPicPaths() != null && addPicPaths) {
			this.picPaths = new ArrayList<PicPathDto>(offer.getPicPaths()
					.size());
			for (PicPath picPath : offer.getPicPaths())
				this.picPaths.add(new PicPathDto(picPath.getPicPath()));
		}
	}

	public OfferDto() {
	}

	private String description;
	private List<PicPathDto> picPaths;


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<PicPathDto> getPicPaths() {
		return picPaths;
	}

	public void setPicPaths(List<PicPathDto> picPaths) {
		this.picPaths = picPaths;
	}
}
