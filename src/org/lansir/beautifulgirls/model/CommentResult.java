package org.lansir.beautifulgirls.model;

import java.util.List;

public class CommentResult extends Result {
	private List<Comment> comments;

	public CommentResult() {
		super();
	}

	public CommentResult(List<Comment> comments) {
		super();
		this.comments = comments;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
