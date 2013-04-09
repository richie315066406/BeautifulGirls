package org.lansir.beautifulgirls.ui.phone;

import org.lansir.beautifulgirls.ui.BaseSinglePaneActivity;
import org.lansir.beautifulgirls.ui.PictureSeriesFragment;

import com.actionbarsherlock.view.Window;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PictureSeriesActivity extends BaseSinglePaneActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected Fragment onCreatePane() {
		return new PictureSeriesFragment();
	}

}
