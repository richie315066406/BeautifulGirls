package org.lansir.beautifulgirls.model;

import java.util.List;

public class CategoryResult extends Result{
	private List<Category> categories;
	
	public CategoryResult() {
		super();
	}

	public CategoryResult(List<Category> categories) {
		super();
		this.categories = categories;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	
}
