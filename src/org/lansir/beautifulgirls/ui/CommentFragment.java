package org.lansir.beautifulgirls.ui;

import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.action.CommentAction;
import org.lansir.beautifulgirls.adapter.CommentAdapter;
import org.lansir.beautifulgirls.common.Constants;
import org.lansir.beautifulgirls.common.Constants.Extra;
import org.lansir.beautifulgirls.exception.AkException;
import org.lansir.beautifulgirls.model.Comment;
import org.lansir.beautifulgirls.model.CommentResult;
import org.lansir.beautifulgirls.proxy.Akita;
import org.lansir.beautifulgirls.ui.phone.CreateCommentActivity;
import org.lansir.beautifulgirls.utils.DialogAsyncTask;
import org.lansir.beautifulgirls.utils.FragmentUtil;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class CommentFragment extends SherlockFragment {
	private PullToRefreshListView plvComment;
	private CommentAdapter mCommentAdapter;
	private View vCommentError;
	private View vCommentEmpty;
	private View vLoadingView;
	private Integer iPid = null;
	private Integer iCurrentPage = 0;
	private boolean bFinalPage = false;
	private boolean bFirstEnter = true;
	private CommentAsyncTask mCommentAsyncTask = null;
	private Button btnCreateComment;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_comment, null);
		plvComment = (PullToRefreshListView) view.findViewById(R.id.pLvComment);
		vCommentError = view.findViewById(R.id.comment_error);
		vCommentEmpty = view.findViewById(R.id.comment_emptyView);
		vLoadingView = view.findViewById(R.id.loadView);
		btnCreateComment = (Button) view.findViewById(R.id.btnCreateComment);
		
		mCommentAdapter = new CommentAdapter(inflater);
		plvComment.setAdapter(mCommentAdapter);
		return view;
	}
	
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		iPid = getArguments().getInt(Extra.PICTURE_ID);
		if (iPid != null) {
			btnCreateComment.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(getSherlockActivity(), CreateCommentActivity.class);
					intent.putExtra(Extra.PICTURE_ID, iPid);
					startActivityForResult(intent,0);
					FragmentUtil.finish(getSherlockActivity());
				}
			});
			getComments();
			plvComment.setOnScrollListener(new OnScrollListener(){
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
							getComments();
						} else {
							if(bFinalPage && !bFinalTips){
								bFinalTips = true;
								Toast.makeText(getSherlockActivity(), R.string.last_page, Toast.LENGTH_SHORT).show();
							}
								
						}
					}
				}
				
			});
		}
	}
	
	
	private void getComments() {
		if (mCommentAsyncTask == null || mCommentAsyncTask.getStatus() == Status.FINISHED) {
			mCommentAsyncTask = new CommentAsyncTask(getSherlockActivity());
			mCommentAsyncTask.fireOnParallel();
		}
	}
	
	private void regetComments(){
		iCurrentPage = 0;
		bFinalPage = false;
		bFirstEnter = false;
		if (mCommentAsyncTask == null || mCommentAsyncTask.getStatus() == Status.FINISHED) {
			mCommentAsyncTask = new CommentAsyncTask(getSherlockActivity());
			mCommentAsyncTask.fireOnParallel();
		}
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogUtil.v("onActivityResult:" + resultCode);
		if(resultCode == Activity.RESULT_OK){
			regetComments();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}




	private void showErrorView() {
		vCommentError.setVisibility(View.VISIBLE);
		plvComment.setVisibility(View.GONE);
	}

	private void showEmptyView() {
		LogUtil.v("showEmptyViewl");
		vCommentEmpty.setVisibility(View.VISIBLE);
		plvComment.setVisibility(View.GONE);
	}

	private void showLoadView() {
		vLoadingView.setVisibility(View.VISIBLE);
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
		plvComment.setVisibility(View.GONE);
	}

	private void showProgressView() {
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(true);
	}

	private void hideProgressView() {
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
	}

	private void hideLoadView() {
		LogUtil.v("hideLoadView");
		vLoadingView.setVisibility(View.GONE);
		getSherlockActivity().setSupportProgressBarIndeterminateVisibility(false);
		plvComment.setVisibility(View.VISIBLE);
	}

	private class CommentAsyncTask extends DialogAsyncTask<CommentResult> {

		public CommentAsyncTask(SherlockFragmentActivity context) {
			super(context);
		}

		@Override
		protected CommentResult onDoAsync() throws AkException {
			CommentAction commentAction = Akita.createAPI(CommentAction.class);
			return commentAction.getComments(iPid, iCurrentPage, Constants.PAGE_SIZE, 0);
		}

		@Override
		protected void onUIAfter(CommentResult t) throws AkException {
			if (t != null && t.getRetCode() == 0) {
				LogUtil.v("CommentResult not null");
				List<Comment> mListComments = mCommentAdapter.getListObjects();
				if (t.getComments().size() < Constants.PAGE_SIZE)
					bFinalPage = true;
				mListComments.addAll(t.getComments());
				if (bFirstEnter) {
					hideLoadView();
					bFirstEnter = false;
				} else {
					hideProgressView();
				}
				if (mListComments.size() != 0) {
					mCommentAdapter.setListObjects(mListComments);
					mCommentAdapter.notifyDataSetChanged();
				}else{
					showEmptyView();
				}
			} else {
				LogUtil.v("CommentResult is null");
			}

		}

		@Override
		protected void onHandleAkException(AkException mAkException) {
			showErrorView();
		}

		@Override
		protected void onUIBefore() throws AkException {
			iCurrentPage++;
			if (bFirstEnter) {
				showLoadView();
			} else {
				showProgressView();
			}
		}

	}
}
