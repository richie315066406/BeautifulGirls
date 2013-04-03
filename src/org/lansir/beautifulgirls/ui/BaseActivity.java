package org.lansir.beautifulgirls.ui;

import org.lansir.beautifulgirls.R;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

public abstract class BaseActivity extends SlidingFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// If Android Beam APIs are available, set up the Beam easter egg as the
		// default Beam
		// content. This can be overridden by subclasses.

		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (this instanceof HomeActivity) {
				return false;
			}

			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Sets the icon color using some fancy blending mode trickery.
	 */
	protected void setActionBarColor(int color) {
		if (color == 0) {
			color = 0xffffffff;
		}

		final Resources res = getResources();
		Drawable maskDrawable = res.getDrawable(R.drawable.actionbar_icon_mask);
		if (!(maskDrawable instanceof BitmapDrawable)) {
			return;
		}

		Bitmap maskBitmap = ((BitmapDrawable) maskDrawable).getBitmap();
		final int width = maskBitmap.getWidth();
		final int height = maskBitmap.getHeight();

		Bitmap outBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(outBitmap);
		canvas.drawBitmap(maskBitmap, 0, 0, null);

		Paint maskedPaint = new Paint();
		maskedPaint.setColor(color);
		maskedPaint
				.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

		canvas.drawRect(0, 0, width, height, maskedPaint);

		BitmapDrawable outDrawable = new BitmapDrawable(res, outBitmap);
		getSupportActionBar().setIcon(outDrawable);
	}

	/**
	 * Takes a given intent and either starts a new activity to handle it (the
	 * default behavior), or creates/updates a fragment (in the case of a
	 * multi-pane activity) that can handle the intent.
	 * 
	 * Must be called from the main (UI) thread.
	 */
	public void openActivityOrFragment(Intent intent) {
		// Default implementation simply calls startActivity
		startActivity(intent);
	}

	/**
	 * Converts an intent into a {@link Bundle} suitable for use as fragment
	 * arguments.
	 */
	public static Bundle intentToFragmentArguments(Intent intent) {
		Bundle arguments = new Bundle();
		if (intent == null) {
			return arguments;
		}

		final Uri data = intent.getData();
		if (data != null) {
			arguments.putParcelable("_uri", data);
		}

		final Bundle extras = intent.getExtras();
		if (extras != null) {
			arguments.putAll(intent.getExtras());
		}

		return arguments;
	}

	/**
	 * Converts a fragment arguments bundle into an intent.
	 */
	public static Intent fragmentArgumentsToIntent(Bundle arguments) {
		Intent intent = new Intent();
		if (arguments == null) {
			return intent;
		}

		final Uri data = arguments.getParcelable("_uri");
		if (data != null) {
			intent.setData(data);
		}

		intent.putExtras(arguments);
		intent.removeExtra("_uri");
		return intent;
	}

}
