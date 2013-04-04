package org.lansir.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.model.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {
	private List<Category> mListCategory;
	private LayoutInflater mInflater;
	
	public CategoryAdapter(LayoutInflater mInflater) {
		super();
		this.mInflater = mInflater;
		this.mListCategory = new ArrayList<Category>();
	}
	
	public CategoryAdapter(List<Category> mListCategory,
			LayoutInflater mInflater) {
		super();
		this.mListCategory = mListCategory;
		this.mInflater = mInflater;
	}

	public List<Category> getListCategory() {
		return mListCategory;
	}

	public void setListCategory(List<Category> mListCategory) {
		this.mListCategory = mListCategory;
	}

	public LayoutInflater getmInflater() {
		return mInflater;
	}

	public void setmInflater(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}

	@Override
	public int getCount() {
		return mListCategory.size();
	}

	@Override
	public Category getItem(int position) {
		return mListCategory.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mListCategory.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.grid_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvText = (TextView) convertView.findViewById(R.id.tvGridItem);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvText.setText(getItem(position).getName());
		convertView.setBackgroundColor(getItem(position).getColor());
		return convertView;
	}

	private class ViewHolder{
		public TextView tvText = null;
	}
}
