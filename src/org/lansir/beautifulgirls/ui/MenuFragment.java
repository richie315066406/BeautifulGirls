package org.lansir.beautifulgirls.ui;

import org.lansir.beautifulgirls.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class MenuFragment extends SherlockFragment {
	private TextView tvMenuShortcurHome;
	private TextView tvMenuShortcutSetting;
	private TextView tvMenuShortcutFeedback;
	private TextView tvMenuShortcutUpdateCheck;
	private TextView tvMenuShortcutVersionIntroduction;
	private TextView tvMenuShortcutQuit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.slide_menu_content, null);
		tvMenuShortcurHome = (TextView) view.findViewById(R.id.tvMenuShortcurHome);
		tvMenuShortcutSetting = (TextView) view.findViewById(R.id.tvMenuShortcutSetting);
		tvMenuShortcutFeedback = (TextView) view.findViewById(R.id.tvMenuShortcutFeedback);
		tvMenuShortcutUpdateCheck = (TextView) view.findViewById(R.id.tvMenuShortcutUpdateCheck);
		tvMenuShortcutVersionIntroduction = (TextView) view.findViewById(R.id.tvMenuShortcutVersionIntroduction);
		tvMenuShortcutQuit = (TextView) view.findViewById(R.id.tvMenuShortcutQuit);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		tvMenuShortcurHome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				HomeActivity homeActivity = (HomeActivity)getSherlockActivity();
				homeActivity.switchHome();
			}
		});
		
		tvMenuShortcutQuit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getSherlockActivity().finish();
			}
		});
	}


	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;
		
		/*if (getActivity() instanceof FragmentChangeActivity) {
			FragmentChangeActivity fca = (FragmentChangeActivity) getActivity();
			fca.switchContent(fragment);
		} else if (getActivity() instanceof ResponsiveUIActivity) {
			ResponsiveUIActivity ra = (ResponsiveUIActivity) getActivity();
			ra.switchContent(fragment);
		}*/
	}

}
