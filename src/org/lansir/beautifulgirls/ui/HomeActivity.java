package org.lansir.beautifulgirls.ui;

import org.lansir.beautifulgirls.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends BaseActivity {
	private Fragment mContent;
	private SlidingMenu mSlidingMenu;
//	private SlidingActivityHelper mHelper = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		mHelper = new SlidingActivityHelper(this);
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
		
		mSlidingMenu = new SlidingMenu(this);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.35f);
		mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
		mSlidingMenu.setMenu(R.layout.menu_frame);
//		mHelper.setSlidingActionBarEnabled(slidingActionBarEnabled)
		 // set the Behind View
//		 setBehindContentView(R.layout.menu_frame);
		 getSupportFragmentManager().beginTransaction()
		 .replace(R.id.menu_frame, new MenuFragment()).commit();
		 mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		 
		 getSupportActionBar().setHomeButtonEnabled(false);
		 getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		 getSupportActionBar().setDisplayUseLogoEnabled(true);
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
		mSlidingMenu.showContent();
	}

	@Override
	public void onBackPressed() {
		if(mSlidingMenu.isMenuShowing()){
			mSlidingMenu.showContent();
		}else{
			mSlidingMenu.showMenu();
		}
	}
	
	public void switchHome(){
		mSlidingMenu.showContent();
	}
	
	
}
