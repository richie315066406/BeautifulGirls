package org.lansir.beautifulgirls.adapter;

import org.lansir.beautifulgirls.model.Category;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class OnCategoryItemClickListener implements OnItemClickListener {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
		Category category = (Category) parent.getItemAtPosition(position);
		
	}
}
