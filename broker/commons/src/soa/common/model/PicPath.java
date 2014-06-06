package soa.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "pic_paths")
public class PicPath extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1406386925039860648L;

	@NotNull
	@Column(name = "pic_path")
	private String picPath;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

}
