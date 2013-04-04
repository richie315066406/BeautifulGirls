package org.lansir.beautifulgirls.action;

import org.lansir.beautifulgirls.annotation.AkAPI;
import org.lansir.beautifulgirls.annotation.AkGET;
import org.lansir.beautifulgirls.model.CategoryResult;
import org.lansir.beautifulgirls.network.UrlConstants;


public interface CategoryAction {
	@AkGET
	@AkAPI(url=UrlConstants.GET_NORMAL_CATEGORY)
	public CategoryResult getNormalCategories();
}
