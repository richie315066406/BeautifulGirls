package org.lansir.beautifulgirls.utils;

import org.lansir.beautifulgirls.R;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public abstract class DialogAsyncTask<T> extends SimpleAsyncTask<T> {
	private SherlockFragmentActivity fragmentActivity = null;
	private ProgressDialog progressDialog;
	private boolean bActionFlag;

	public DialogAsyncTask(final SherlockFragmentActivity context) {
		super(context);
		this.fragmentActivity = context;
		progressDialog = ProgressDialog.show(fragmentActivity, null,
				context.getResources().getString(R.string.loading), true, true, new OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						FragmentUtil.finish(fragmentActivity);
					}
				});

	}

	public DialogAsyncTask(final SherlockFragmentActivity context, Boolean actionFlag) {
		super(context);
		this.fragmentActivity = context;
		this.bActionFlag = actionFlag;
		if (actionFlag != null)
			bActionFlag = actionFlag;
		if (!bActionFlag) {
			progressDialog = ProgressDialog.show(fragmentActivity, null,
					context.getResources().getString(R.string.loading), true, true, new OnCancelListener() {

						@Override
						public void onCancel(DialogInterface dialog) {
							FragmentUtil.finish(fragmentActivity);
						}
					});
		}

	}

	@Override
	protected void onUITaskStart() {
		super.onUITaskStart();
		if (!bActionFlag)
			progressDialog.show();
		else
			fragmentActivity.setSupportProgressBarIndeterminateVisibility(true);
	}

	@Override
	protected void onUITaskEnd() {
		super.onUITaskEnd();
		if (!bActionFlag)
			progressDialog.dismiss();
		else
			fragmentActivity.setSupportProgressBarIndeterminateVisibility(false);
	}

}
