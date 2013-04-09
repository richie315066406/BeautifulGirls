package org.lansir.beautifulgirls.model;

public class PictureSeries {
	private Integer id;
	private String name;
	private Long uploadtime;
	private Integer click;
	private Integer download;
	private Integer count;
	private String image;
	private Integer uin;
	private String nickname;
	public PictureSeries(){
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(Long uploadtime) {
		this.uploadtime = uploadtime;
	}
	public Integer getClick() {
		return click;
	}
	public void setClick(Integer click) {
		this.click = click;
	}
	public Integer getDownload() {
		return download;
	}
	public void setDownload(Integer download) {
		this.download = download;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getUin() {
		return uin;
	}
	public void setUin(Integer uin) {
		this.uin = uin;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
