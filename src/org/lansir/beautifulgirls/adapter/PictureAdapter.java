package org.lansir.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.model.Picture;
import org.lansir.beautifulgirls.network.UrlConstants;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PictureAdapter extends ListAdapter<Picture> {
	private LayoutInflater mInflater;
	private DisplayImageOptions options = null;
	private ImageLoader imageLoader = null;

	public PictureAdapter() {
		super(new ArrayList<Picture>());
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public PictureAdapter(LayoutInflater mInflater) {
		this();
		this.mInflater = mInflater;

	}

	public PictureAdapter(List<Picture> mListPictures,
			LayoutInflater mInflater) {
		this();
		this.setListObjects(mListPictures);
		this.mInflater = mInflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_item_picture, null);
			viewHolder = new ViewHolder();
			viewHolder.ivImage = (ImageView) convertView
					.findViewById(R.id.ivPictureImage);
			viewHolder.tvPictureGood = (TextView) convertView.findViewById(R.id.tvPictureGood);
			viewHolder.tvPictureBad = (TextView) convertView.findViewById(R.id.tvPictureBad);
			viewHolder.tvPictureDownload = (TextView) convertView.findViewById(R.id.tvPictureDownload);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Picture picture = getItem(position);
		imageLoader.displayImage(UrlConstants.HOST + picture.getImage(), viewHolder.ivImage, options);
		viewHolder.tvPictureGood.setText(String.valueOf(picture.getGood()));
		viewHolder.tvPictureBad.setText(String.valueOf(picture.getBad()));
		viewHolder.tvPictureDownload.setText(String.valueOf(picture.getDownload()));
		
		return convertView;
	}

	private class ViewHolder {
		public ImageView ivImage = null;
		public TextView tvPictureGood;
		public TextView tvPictureBad;
		public TextView tvPictureDownload;
	}
}
