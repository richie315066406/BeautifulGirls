package org.lansir.beautifulgirls.ui.phone;

import org.lansir.beautifulgirls.ui.BaseSinglePaneActivity;
import org.lansir.beautifulgirls.ui.PictureSeriesFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PictureSeriesActivity extends BaseSinglePaneActivity {

	
	@Override
	protected Fragment onCreatePane() {
		return new PictureSeriesFragment();
	}

}
