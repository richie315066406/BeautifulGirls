package org.lansir.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.model.Category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CategoryAdapter extends ListAdapter<Category> {
	private LayoutInflater mInflater;
	
	public CategoryAdapter(LayoutInflater mInflater) {
		super(new ArrayList<Category>());
		this.mInflater = mInflater;
		
	}
	
	public CategoryAdapter(List<Category> mListCategory,
			LayoutInflater mInflater) {
		super(mListCategory);
		this.mInflater = mInflater;
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
