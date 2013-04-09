package org.lansir.beautifulgirls.ui;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.action.PictureAction;
import org.lansir.beautifulgirls.adapter.PictureAdapter;
import org.lansir.beautifulgirls.common.Constants;
import org.lansir.beautifulgirls.common.Constants.Extra;
import org.lansir.beautifulgirls.exception.AkException;
import org.lansir.beautifulgirls.model.Picture;
import org.lansir.beautifulgirls.model.PictureResult;
import org.lansir.beautifulgirls.proxy.Akita;
import org.lansir.beautifulgirls.utils.DialogAsyncTask;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Parcelable;
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

public class PictureFragment extends SherlockFragment {

	private GridView mGridView;
	private PictureAdapter mPictureAdapter;
	private Integer iPsid;
	private Integer iCurrentPage = 0;
	private boolean bFinalPage = false;
	private boolean bFirstEnter = true;
	private PictureAsyncTask mPictureAsyncTask = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mGridView = (GridView) inflater.inflate(R.layout.list_grid, null);
		return mGridView;
	}
	
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mPictureAdapter = new PictureAdapter(getSherlockActivity().getLayoutInflater());
		mGridView.setAdapter(mPictureAdapter);
		Bundle bundle = getArguments();
		iPsid = bundle.getInt(Extra.PICTURE_SERIES_ID);
		if (iPsid != null) {
			getPictures();
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
							getPictures();
						} else {
							if (bFinalPage && !bFinalTips){
								Toast.makeText(getSherlockActivity(), R.string.last_page, Toast.LENGTH_SHORT).show();
								bFinalTips = true;
							}
						}
					}
				}

			});
			mGridView.setOnItemClickListener(new OnPictureItemClickListener());
		}
	}

	private void getPictures() {
		if (mPictureAsyncTask == null || mPictureAsyncTask.getStatus() == Status.FINISHED) {
			mPictureAsyncTask = new PictureAsyncTask(getSherlockActivity(),!bFirstEnter);
			if(bFirstEnter)
				bFirstEnter = false;
			mPictureAsyncTask.fireOnParallel();
		}
	}
	
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK){
			List<Picture> pictures = data.getParcelableArrayListExtra(Extra.PICTURES);
			LogUtil.v("pictures==null" + (pictures==null));
			if(pictures != null){
				mPictureAdapter.setListObjects(pictures);
				mPictureAdapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}



	private class PictureAsyncTask extends DialogAsyncTask<PictureResult> {

		
		public PictureAsyncTask(SherlockFragmentActivity context, Boolean actionFlag) {
			super(context, actionFlag);
		}

		@Override
		protected PictureResult onDoAsync() throws AkException {
			PictureAction pictureAction = Akita.createAPI(PictureAction.class);
			PictureResult result = pictureAction.getPictures(iPsid, iCurrentPage, Constants.PAGE_SIZE, 0, 0);
			return result;
		}

		@Override
		protected void onUIAfter(PictureResult t) throws AkException {
			if (t != null && t.getRetCode() == 0) {
				LogUtil.v("PictureResult not null");
				List<Picture> mListPictures = mPictureAdapter.getListObjects();
				if (t.getPictures().size() < Constants.PAGE_SIZE)
					bFinalPage = true;
				mListPictures.addAll(t.getPictures());
				mPictureAdapter.setListObjects(mListPictures);
				mPictureAdapter.notifyDataSetChanged();
			} else {
				LogUtil.v("PictureResult is null");
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

	private class OnPictureItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			
			Picture picture = (Picture) parent.getItemAtPosition(position);
			Intent intent = new Intent(getSherlockActivity(), PicturePagerActivity.class);
			PictureAdapter pictureAdapter = (PictureAdapter) parent.getAdapter();
			intent.putParcelableArrayListExtra(Extra.PICTURES, (ArrayList<? extends Parcelable>) pictureAdapter.getListObjects());
			intent.putExtra(Intent.EXTRA_TITLE, picture.getName());
			intent.putExtra(Extra.IMAGE_POSITION, position);
			startActivityForResult(intent,0);
		}

	}
}
