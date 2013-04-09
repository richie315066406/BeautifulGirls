package org.lansir.beautifulgirls.adapter;

import java.util.List;

import android.widget.BaseAdapter;

public abstract class ListAdapter<T> extends BaseAdapter {
	protected List<T> mListObjects;

	public ListAdapter(List<T> mListObjects) {
		super();
		this.mListObjects = mListObjects;
	}

	public List<T> getListObjects() {
		return mListObjects;
	}

	public void setListObjects(List<T> mListObjects) {
		this.mListObjects = mListObjects;
	}

	@Override
	public int getCount() {
		return mListObjects.size();
	}

	@Override
	public T getItem(int position) {
		return mListObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mListObjects.get(position).hashCode();
	}

}
