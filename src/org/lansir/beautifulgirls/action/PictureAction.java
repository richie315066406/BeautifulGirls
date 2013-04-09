package org.lansir.beautifulgirls.action;

import org.lansir.beautifulgirls.annotation.AkAPI;
import org.lansir.beautifulgirls.annotation.AkPOST;
import org.lansir.beautifulgirls.annotation.AkParam;
import org.lansir.beautifulgirls.model.PictureResult;
import org.lansir.beautifulgirls.model.Result;
import org.lansir.beautifulgirls.network.UrlConstants;

public interface PictureAction {
	@AkPOST
	@AkAPI(url=UrlConstants.GET_NORMAL_PICTURES)
	public PictureResult getPictures(
			@AkParam("psid") int psid,
			@AkParam("page") int page,
			@AkParam("limit") int limit,
			@AkParam("status") int status,
			@AkParam("type") int type);
	@AkPOST
	@AkAPI(url=UrlConstants.GOOD_PICTURE)
	public Result goodPicture(
			@AkParam("pid") int pid);
	@AkPOST
	@AkAPI(url=UrlConstants.BAD_PICTURE)
	public Result badPicture(
			@AkParam("pid") int pid);
	
	@AkPOST
	@AkAPI(url=UrlConstants.DOWNLOAD_PICTURE)
	public Result downloadPicture(
			@AkParam("pid") int pid);
}
