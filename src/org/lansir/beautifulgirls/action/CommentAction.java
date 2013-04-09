package org.lansir.beautifulgirls.action;

import org.lansir.beautifulgirls.annotation.AkAPI;
import org.lansir.beautifulgirls.annotation.AkPOST;
import org.lansir.beautifulgirls.annotation.AkParam;
import org.lansir.beautifulgirls.model.CommentResult;
import org.lansir.beautifulgirls.model.Result;
import org.lansir.beautifulgirls.network.UrlConstants;

public interface CommentAction {
	@AkPOST
	@AkAPI(url=UrlConstants.GET_COMMENTS)
	public CommentResult getComments(
			@AkParam("pid") int pid,
			@AkParam("page") int page,
			@AkParam("limit") int limit,
			@AkParam("status") int status);
	
	@AkPOST
	@AkAPI(url=UrlConstants.CREATE_COMMENTS)
	public Result creteComment(
			@AkParam("nickname") String nickname,
			@AkParam("pid") int pid,
			@AkParam("content") String content);
}
