package org.lansir.beautifulgirls.action;

import org.lansir.beautifulgirls.annotation.AkAPI;
import org.lansir.beautifulgirls.annotation.AkPOST;
import org.lansir.beautifulgirls.annotation.AkParam;
import org.lansir.beautifulgirls.model.PictureSeriesResult;
import org.lansir.beautifulgirls.network.UrlConstants;

public interface PictureSeriesAction {
	@AkPOST
	@AkAPI(url=UrlConstants.GET_NORMAL_PICTURESERIES)
	public PictureSeriesResult getPictureSeries(
			@AkParam("cid") int cid,
			@AkParam("page") int page,
			@AkParam("limit") int limit,
			@AkParam("status") int status,
			@AkParam("type") int type);
}