package org.lansir.beautifulgirls.ui;

import org.lansir.beautifulgirls.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends BaseActivity {
	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new HomeFragment();
		// set the Above View
		setContentView(R.layout.activity_content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();


		 // set the Behind View
		 setBehindContentView(R.layout.menu_frame);
		 getSupportFragmentManager().beginTransaction()
		 .replace(R.id.menu_frame, new MenuFragment()).commit();
		 getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
}
