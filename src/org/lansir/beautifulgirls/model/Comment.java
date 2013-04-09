package org.lansir.beautifulgirls.model;

public class Comment {
	private Integer id;
	private String commentator;
	private Long commenttime;
	private String content;
	
	
	
	public Comment() {
		super();
	}
	
	
	public Comment(Integer id,String commentator, Long commenttime, String content) {
		super();
		this.id = id;
		this.commentator = commentator;
		this.commenttime = commenttime;
		this.content = content;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommentator() {
		return commentator;
	}
	public void setCommentator(String commentator) {
		this.commentator = commentator;
	}
	public Long getCommenttime() {
		return commenttime;
	}
	public void setCommenttime(Long commenttime) {
		this.commenttime = commenttime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
