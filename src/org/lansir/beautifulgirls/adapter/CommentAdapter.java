package org.lansir.beautifulgirls.adapter;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.model.Comment;
import org.lansir.beautifulgirls.utils.DateUtil;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommentAdapter extends ListAdapter<Comment> {
	private LayoutInflater mInflater;

	public CommentAdapter() {
		super(new ArrayList<Comment>());
	}

	public CommentAdapter(LayoutInflater mInflater) {
		this();
		this.mInflater = mInflater;

	}

	public CommentAdapter(List<Comment> mListComments,
			LayoutInflater mInflater) {
		this();
		this.setListObjects(mListComments);
		this.mInflater = mInflater;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.fragment_comment_item, null);
			viewHolder = new ViewHolder();
			viewHolder.tvCommentator = (TextView) convertView.findViewById(R.id.tvCommentator);
			viewHolder.tvCommentTime = (TextView) convertView.findViewById(R.id.tvCommentTime);
			viewHolder.tvCommentContent = (TextView) convertView.findViewById(R.id.tvCommentContent);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tvCommentator.setText(getItem(position).getCommentator());
		viewHolder.tvCommentTime.setText(DateUtil.getSimpleDatetime(getItem(position).getCommenttime()));
		viewHolder.tvCommentContent.setText(getItem(position).getContent());
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tvCommentator;
		public TextView tvCommentTime;
		public TextView tvCommentContent;
	}
}
