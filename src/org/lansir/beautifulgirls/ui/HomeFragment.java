package org.lansir.beautifulgirls.ui;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.action.CategoryAction;
import org.lansir.beautifulgirls.adapter.CategoryAdapter;
import org.lansir.beautifulgirls.exception.AkException;
import org.lansir.beautifulgirls.model.Category;
import org.lansir.beautifulgirls.model.CategoryResult;
import org.lansir.beautifulgirls.proxy.Akita;
import org.lansir.beautifulgirls.ui.phone.PictureSeriesActivity;
import org.lansir.beautifulgirls.utils.JsonMapper;
import org.lansir.beautifulgirls.utils.LogUtil;
import org.lansir.beautifulgirls.utils.SimpleAsyncTask;
import org.lansir.beautifulgirls.utils.StringUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockFragment;

public class HomeFragment extends SherlockFragment {
	private GridView mGridView = null;
	private CategoryAdapter mCategoryAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_grid, null);
		this.mGridView = (GridView) view.findViewById(R.id.gridView);
		
		List<Category> listCategories = initCategoresFromAsset();
		if(listCategories != null)
			mCategoryAdapter = new CategoryAdapter(listCategories,inflater);
		else
			mCategoryAdapter = new CategoryAdapter(inflater);
		mGridView.setAdapter(mCategoryAdapter);
		CategoryAsyncTask asyncTask = new CategoryAsyncTask();
		asyncTask.execute(0);
		return view;
	}
	
	private List<Category> initCategoresFromAsset(){
		InputStream inputStream;
		try {
			inputStream = getSherlockActivity().getAssets().open("categories.txt");
			String strCategories = StringUtil.inputStream2String(inputStream);
			CategoryResult categoryResult = (CategoryResult) JsonMapper.json2pojo(strCategories, CategoryResult.class);
			List<Category> mListCategory = categoryResult.getCategories();
			initCategoriesColor(mListCategory);
			return mListCategory;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<Category> initCategoriesColor(List<Category> listCategories){
		if(listCategories == null)
			return null;
		int[] colors = getSherlockActivity().getResources()
				.getIntArray(R.array.color_array);
		Random ramdon = new Random();
		int initIndex = Math.abs(ramdon.nextInt())
				% (colors.length - 10);
		for (int i = 0; i < listCategories.size(); i++) {
			listCategories.get(i).setColor(colors[initIndex + i]);
		}
		return listCategories;
	}

	private class CategoryAsyncTask extends SimpleAsyncTask<CategoryResult> {

		@Override
		protected void onUIBefore() throws AkException {

		}

		@Override
		protected CategoryResult onDoAsync() throws AkException {
			CategoryAction categoryAction = Akita
					.createAPI(CategoryAction.class);
			CategoryResult result = categoryAction.getNormalCategories();
			return result;
		}

		@Override
		protected void onUIAfter(CategoryResult t) throws AkException {
			if (t != null && t.getRetCode() == 0) {
				List<Category> mListCategory = t.getCategories();
				initCategoriesColor(mListCategory);
				LogUtil.v("mListCategory size:" + mListCategory.size());
				mCategoryAdapter.setListCategory(mListCategory);
				mCategoryAdapter.notifyDataSetChanged();
			}else{
				LogUtil.v("CategoryResult is null");
			}
			
		}

		@Override
		protected void onHandleAkException(AkException mAkException) {
			super.onHandleAkException(mAkException);
		}

	}
	
	public class OnCategoryItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			Category category = (Category) parent.getItemAtPosition(position);
			Intent intent = new Intent(getSherlockActivity(), PictureSeriesActivity.class);
			intent.putExtra(Intent.EXTRA_TITLE, category.getName());
			intent.putExtra("CategoryId", category.getId());
			startActivity(intent);			
		}
	}
}
