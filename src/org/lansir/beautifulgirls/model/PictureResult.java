package org.lansir.beautifulgirls.model;

import java.util.List;

public class PictureResult extends Result {
	private List<Picture> pictures;
	
	public PictureResult() {
		super();
	}

	public PictureResult(List<Picture> pictures) {
		super();
		this.pictures = pictures;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}
	
	
}
