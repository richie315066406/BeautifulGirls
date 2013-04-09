package org.lansir.beautifulgirls.utils;

import android.app.Activity;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FragmentUtil {
	public static void finish(SherlockFragmentActivity fragmentActivity){
		LogUtil.v("stack count:" + fragmentActivity.getFragmentManager().getBackStackEntryCount());
		if(fragmentActivity.getFragmentManager().getBackStackEntryCount() == 0){
			fragmentActivity.setResult(Activity.RESULT_OK);
			fragmentActivity.finish();
		}else{
			fragmentActivity.setResult(Activity.RESULT_OK);
			fragmentActivity.getFragmentManager().popBackStack();
		}
	}
}
