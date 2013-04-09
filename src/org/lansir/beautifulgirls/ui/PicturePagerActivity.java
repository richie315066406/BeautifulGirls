package org.lansir.beautifulgirls.ui;

import java.util.ArrayList;
import java.util.List;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.action.PictureAction;
import org.lansir.beautifulgirls.common.Constants.Extra;
import org.lansir.beautifulgirls.exception.AkException;
import org.lansir.beautifulgirls.model.Picture;
import org.lansir.beautifulgirls.model.Result;
import org.lansir.beautifulgirls.network.UrlConstants;
import org.lansir.beautifulgirls.proxy.Akita;
import org.lansir.beautifulgirls.ui.phone.CommentActivity;
import org.lansir.beautifulgirls.utils.DialogAsyncTask;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class PicturePagerActivity extends BaseActivity {

	private DisplayImageOptions options;

	private ViewPager mPager;
	private ImagePagerAdapter mImagePagerAdapter;
	private TextView tvCurImageNo;
	private TextView tvTotalImageNo;
	private ImageLoader mImageLoader;
	private ImageButton ibGood;
	private ImageButton ibBad;
	private ImageButton ibComment;
	private ImageButton ibDownload;
	private ImageButton ibTransmit;
	private TextView tvGoodCount;
	private TextView tvBadCount;
	private TextView tvCommentCount;
	private boolean bDirty = false;

	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_pager);

		initViews();
		initStates(savedInstanceState);

	}

	private void initViews() {
		tvCurImageNo = (TextView) findViewById(R.id.tvCurImageNo);
		tvTotalImageNo = (TextView) findViewById(R.id.tvTotalImageNo);
		ibGood = (ImageButton) findViewById(R.id.ibGood);
		ibBad = (ImageButton) findViewById(R.id.ibBad);
		ibComment = (ImageButton) findViewById(R.id.ibComment);
		ibDownload = (ImageButton) findViewById(R.id.ibDownload);
		ibTransmit = (ImageButton) findViewById(R.id.ibTransmit);
		tvGoodCount = (TextView) findViewById(R.id.tvGoodCount);
		tvBadCount = (TextView) findViewById(R.id.tvBadCount);
		tvCommentCount = (TextView) findViewById(R.id.tvCommentCount);
		mPager = (ViewPager) findViewById(R.id.pager);
	}

	private void initStates(Bundle savedInstanceState) {
		Bundle bundle = getIntent().getExtras();
		List<Picture> mPictures = bundle.getParcelableArrayList(Extra.PICTURES);
		int pagerPosition = bundle.getInt(Extra.IMAGE_POSITION, 0);

		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(Extra.IMAGE_POSITION);
		}
		tvCurImageNo.setText(String.valueOf(pagerPosition + 1));
		tvTotalImageNo.setText(String.valueOf(mPictures.size()));
		tvGoodCount.setText(String.valueOf(mPictures.get(pagerPosition).getGood()));
		tvBadCount.setText(String.valueOf(mPictures.get(pagerPosition).getBad()));
		tvCommentCount.setText(String.valueOf(mPictures.get(pagerPosition).getComment()));

		mImageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).resetViewBeforeLoading().cacheOnDisc()
				.imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(300)).build();

		mImagePagerAdapter = new ImagePagerAdapter(mPictures);
		mPager.setAdapter(mImagePagerAdapter);
		mPager.setCurrentItem(pagerPosition);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageSelected(int position) {
				Picture picture = mImagePagerAdapter.getItemAtPosition(position);
				tvCurImageNo.setText(String.valueOf(position + 1));
				tvGoodCount.setText(String.valueOf(picture.getGood()));
				tvBadCount.setText(String.valueOf(picture.getBad()));
				tvCommentCount.setText(String.valueOf(picture.getComment()));
			}

		});
		ibComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Picture picture = mImagePagerAdapter.getItemAtPosition(mPager.getCurrentItem());
				Intent intent = new Intent(PicturePagerActivity.this, CommentActivity.class);
				intent.putExtra(Extra.PICTURE_ID, picture.getId());
				startActivity(intent);
			}
		});

		ibGood.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Picture picture = mImagePagerAdapter.getItemAtPosition(mPager.getCurrentItem());
				PictureAsyncTask pictureAsyncTask = new PictureAsyncTask(PicturePagerActivity.this, JudgePicture.GOOD,
						picture.getId());
				pictureAsyncTask.fireOnParallel();
			}
		});

		ibBad.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Picture picture = mImagePagerAdapter.getItemAtPosition(mPager.getCurrentItem());
				PictureAsyncTask pictureAsyncTask = new PictureAsyncTask(PicturePagerActivity.this, JudgePicture.BAD,
						picture.getId());
				pictureAsyncTask.fireOnParallel();
			}
		});

		ibDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Picture picture = mImagePagerAdapter.getItemAtPosition(mPager.getCurrentItem());
				PictureAsyncTask pictureAsyncTask = new PictureAsyncTask(PicturePagerActivity.this,
						JudgePicture.DOWNLOAD, picture.getId());
				pictureAsyncTask.fireOnParallel();
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(Extra.IMAGE_POSITION, mPager.getCurrentItem());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			backResult();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	

	@Override
	public void onBackPressed() {
		backResult();
		super.onBackPressed();
	}

	private void backResult(){
		if (bDirty) {
			Intent intent = new Intent();
			intent.putParcelableArrayListExtra(Extra.PICTURES,
					(ArrayList<? extends Parcelable>) mImagePagerAdapter.getPictures());
			setResult(Activity.RESULT_OK, intent);
		}
	}

	private class ImagePagerAdapter extends PagerAdapter {

		private List<Picture> mPictures;
		private LayoutInflater inflater;

		ImagePagerAdapter(List<Picture> pictures) {
			this.mPictures = pictures;
			inflater = getLayoutInflater();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View) object);
		}

		@Override
		public void finishUpdate(View container) {
		}

		@Override
		public int getCount() {
			return this.mPictures.size();
		}

		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
			ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
			final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);
			mImageLoader.displayImage(UrlConstants.HOST + mPictures.get(position).getImage(), imageView, options,
					new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							LogUtil.v("onLoadingStarted:imageUri:" + imageUri);
							spinner.setVisibility(View.VISIBLE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
							String message = null;
							switch (failReason.getType()) {
							case IO_ERROR:
								message = "Input/Output error";
								break;
							case DECODING_ERROR:
								message = "Image can't be decoded";
								break;
							case NETWORK_DENIED:
								message = "Downloads are denied";
								break;
							case OUT_OF_MEMORY:
								message = "Out Of Memory error";
								break;
							case UNKNOWN:
								message = "Unknown error";
								break;
							}
							Toast.makeText(PicturePagerActivity.this, message, Toast.LENGTH_SHORT).show();

							spinner.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
							LogUtil.v("onLoadingComplete:imageUri:" + imageUri);
							spinner.setVisibility(View.GONE);
						}
					});

			((ViewPager) view).addView(imageLayout, 0);
			return imageLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view.equals(object);
		}

		@Override
		public void restoreState(Parcelable state, ClassLoader loader) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View container) {
		}

		public Picture getItemAtPosition(int position) {
			return mPictures.get(position);
		}

		public List<Picture> getPictures() {
			return mPictures;
		}

	}

	private enum JudgePicture {
		GOOD, BAD, DOWNLOAD
	}

	private class PictureAsyncTask extends DialogAsyncTask<Result> {
		private JudgePicture judgePicture;
		private int iPid;

		public PictureAsyncTask(SherlockFragmentActivity context, JudgePicture judgePicture, int pid) {
			super(context, true);
			this.judgePicture = judgePicture;
			this.iPid = pid;
		}

		@Override
		protected void onUIBefore() throws AkException {

		}

		@Override
		protected Result onDoAsync() throws AkException {
			Result result = null;
			PictureAction pictureAction = Akita.createAPI(PictureAction.class);
			switch (judgePicture) {
			case GOOD:
				result = pictureAction.goodPicture(iPid);
				break;
			case BAD:
				result = pictureAction.badPicture(iPid);
				break;
			case DOWNLOAD:
				result = pictureAction.downloadPicture(iPid);
				break;
			}
			return result;
		}

		@Override
		protected void onUIAfter(Result t) throws AkException {
			if (t.getRetCode() == 0) {
				Picture picture = mImagePagerAdapter.getItemAtPosition(mPager.getCurrentItem());
				switch (judgePicture) {
				case GOOD:
					
					picture.setGood(picture.getGood() + 1);
					tvGoodCount.setText(String.valueOf(picture.getGood()));
					break;
				case BAD:
					picture.setBad(picture.getBad() + 1);
					tvBadCount.setText(String.valueOf(picture.getBad()));
					break;
				case DOWNLOAD:
					picture.setDownload(picture.getDownload()+1);
					break;
				}
				if (!bDirty)
					bDirty = true;
				Toast.makeText(PicturePagerActivity.this, getResources().getString(R.string.operate_success),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(PicturePagerActivity.this, getResources().getString(R.string.operate_failure),
						Toast.LENGTH_SHORT).show();
			}
		}

	}
}