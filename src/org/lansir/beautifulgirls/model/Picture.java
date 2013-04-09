package org.lansir.beautifulgirls.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable{
	private Integer id;
	private String name;
	private Long uploadtime;
	private String image;
	private Integer download;
	private Integer good;
	private Integer bad;
	private Integer comment;
	
	
	
	public Picture() {
		super();
	}
	
	
	public Picture(Integer id, String name, Long uploadtime, String image, Integer download, Integer good, Integer bad,
			Integer comment) {
		super();
		this.id = id;
		this.name = name;
		this.uploadtime = uploadtime;
		this.image = image;
		this.download = download;
		this.good = good;
		this.bad = bad;
		this.comment = comment;
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getDownload() {
		return download;
	}
	public void setDownload(Integer download) {
		this.download = download;
	}
	public Integer getGood() {
		return good;
	}
	public void setGood(Integer good) {
		this.good = good;
	}
	public Integer getBad() {
		return bad;
	}
	public void setBad(Integer bad) {
		this.bad = bad;
	}
	public Integer getComment() {
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}
	
	/** Parcelable.Creator needs by Android. */
	public static final Parcelable.Creator<Picture> CREATOR = new Parcelable.Creator<Picture>() {

		@Override
		public Picture createFromParcel(Parcel source) {
			return new Picture(source);
		}

		@Override
		public Picture[] newArray(int size) {
			return new Picture[size];
		}
	};
	
	private Picture(final Parcel in) {
		id = in.readInt();
		name = in.readString();
		uploadtime = in.readLong();
		image = in.readString();
		download = in.readInt();
		good = in.readInt();
		bad = in.readInt();
		comment = in.readInt();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeLong(uploadtime);
		dest.writeString(image);
		dest.writeInt(download);
		dest.writeInt(good);
		dest.writeInt(bad);
		dest.writeInt(comment);
	}
	
	
}
