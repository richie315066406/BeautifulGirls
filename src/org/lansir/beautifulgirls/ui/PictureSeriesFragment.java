package org.lansir.beautifulgirls.ui;

import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.action.PictureSeriesAction;
import org.lansir.beautifulgirls.adapter.PictureSeriesAdapter;
import org.lansir.beautifulgirls.common.Constants;
import org.lansir.beautifulgirls.common.Constants.Extra;
import org.lansir.beautifulgirls.exception.AkException;
import org.lansir.beautifulgirls.model.PictureSeries;
import org.lansir.beautifulgirls.model.PictureSeriesResult;
import org.lansir.beautifulgirls.proxy.Akita;
import org.lansir.beautifulgirls.ui.phone.PictureActivity;
import org.lansir.beautifulgirls.utils.DialogAsyncTask;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class PictureSeriesFragment extends SherlockFragment {
	private GridView mGridView;
	private PictureSeriesAdapter mPictureSeriesAdapter;
	private PictureSeriesAsyncTask pictureSeriesAsyncTask;
	private Integer iCid;
	private Integer iCurrentPage = 0;
	private boolean bFirstEnter = true;
	private boolean bFinalPage = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mGridView = (GridView) inflater.inflate(R.layout.list_grid, null);
		return mGridView;
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPictureSeriesAdapter = new PictureSeriesAdapter(getSherlockActivity().getLayoutInflater());
		mGridView.setAdapter(mPictureSeriesAdapter);
		Bundle bundle = getArguments();
		iCid = bundle.getInt(Extra.CATEGORY_ID);
		if (iCid != null) {
			getPictureSeries();
			mGridView.setOnScrollListener(new OnScrollListener() {
				private int lastItem = 0;
				private boolean bFinalTips = false;

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
					lastItem = firstVisibleItem + visibleItemCount;
				}

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					if (scrollState != OnScrollListener.SCROLL_STATE_IDLE) {
						if (lastItem >= (view.getAdapter().getCount()) && !bFinalPage) {
							getPictureSeries();
						} else {
							if(bFinalPage && !bFinalTips){
								Toast.makeText(getSherlockActivity(), R.string.last_page, Toast.LENGTH_SHORT).show();
								bFinalTips = true;
							}
						}
					}
				}

			});
			mGridView.setOnItemClickListener(new OnPictureSeriesItemClickListener());
		}
	}



	private void getPictureSeries() {
		if (pictureSeriesAsyncTask == null || pictureSeriesAsyncTask.getStatus() == Status.FINISHED) {
			pictureSeriesAsyncTask = new PictureSeriesAsyncTask(getSherlockActivity(),!bFirstEnter);
			if(bFirstEnter)
				bFirstEnter = false;
			pictureSeriesAsyncTask.fireOnParallel();
		}
	}

	private class PictureSeriesAsyncTask extends DialogAsyncTask<PictureSeriesResult> {
		
		public PictureSeriesAsyncTask(SherlockFragmentActivity context, Boolean actionFlag) {
			super(context, actionFlag);
		}

		@Override
		protected PictureSeriesResult onDoAsync() throws AkException {
			PictureSeriesAction pictureSeriesAction = Akita.createAPI(PictureSeriesAction.class);
			PictureSeriesResult result = pictureSeriesAction.getPictureSeries(iCid, iCurrentPage, Constants.PAGE_SIZE,
					0, 0);
			return result;
		}

		@Override
		protected void onUIAfter(PictureSeriesResult t) throws AkException {
			if (t != null && t.getRetCode() == 0) {
				LogUtil.v("PictureSeriesResult not null");
				List<PictureSeries> mListPictureSeries = mPictureSeriesAdapter.getListObjects();
				if (t.getPictureSeries().size() < Constants.PAGE_SIZE)
					bFinalPage = true;
				mListPictureSeries.addAll(t.getPictureSeries());
				mPictureSeriesAdapter.setListObjects(mListPictureSeries);
				mPictureSeriesAdapter.notifyDataSetChanged();
			} else {
				LogUtil.v("PictureSeriesResult is null");
			}

		}

		@Override
		protected void onHandleAkException(AkException mAkException) {
			super.onHandleAkException(mAkException);
		}

		@Override
		protected void onUIBefore() throws AkException {
			iCurrentPage++;
		}

	}
	
	private class OnPictureSeriesItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id){
			PictureSeries pictureSeries = (PictureSeries) parent.getItemAtPosition(position);
			Intent intent = new Intent(getSherlockActivity(), PictureActivity.class);
			intent.putExtra(Intent.EXTRA_TITLE, pictureSeries.getName());
			intent.putExtra(Extra.PICTURE_SERIES_ID, pictureSeries.getId());
			startActivity(intent);			
		}
		
	}
}
