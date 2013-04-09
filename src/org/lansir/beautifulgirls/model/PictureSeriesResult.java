package org.lansir.beautifulgirls.model;

import java.util.List;

public class PictureSeriesResult extends Result {
	private List<PictureSeries> pictureSeries;
	
	public PictureSeriesResult() {
		super();
	}

	public PictureSeriesResult(List<PictureSeries> pictureSeries) {
		super();
		this.pictureSeries = pictureSeries;
	}

	public List<PictureSeries> getPictureSeries() {
		return pictureSeries;
	}

	public void setPictureSeries(List<PictureSeries> pictureSeries) {
		this.pictureSeries = pictureSeries;
	}

}
