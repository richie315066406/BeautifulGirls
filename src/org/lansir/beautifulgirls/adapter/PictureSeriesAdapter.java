package org.lansir.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.model.PictureSeries;
import org.lansir.beautifulgirls.network.UrlConstants;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class PictureSeriesAdapter extends ListAdapter<PictureSeries> {
	private LayoutInflater mInflater;
	private DisplayImageOptions options = null;
	private ImageLoader imageLoader = null;
	private final String TITLE_PICTURE_SERIES = "%s (%d)";

	public PictureSeriesAdapter() {
		super(new ArrayList<PictureSeries>());
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory()
				.cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public PictureSeriesAdapter(LayoutInflater mInflater) {
		this();
		this.mInflater = mInflater;

	}

	public PictureSeriesAdapter(List<PictureSeries> mListPictureSeries,
			LayoutInflater mInflater) {
		this();
		setListObjects(mListPictureSeries);
		this.mInflater = mInflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.grid_item_pictureseries, null);
			viewHolder = new ViewHolder();
			viewHolder.ivImage = (ImageView) convertView
					.findViewById(R.id.ivPictureSeriesImage);
			viewHolder.tvName = (TextView) convertView
					.findViewById(R.id.tvPictureSeriesName);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvName.setText(String.format(TITLE_PICTURE_SERIES, getItem(position).getName(),getItem(position).getCount()));
		imageLoader.displayImage(UrlConstants.HOST + getItem(position).getImage(), viewHolder.ivImage, options);
		
		return convertView;
	}

	private class ViewHolder {
		public TextView tvName = null;
		public ImageView ivImage = null;
	}

}
