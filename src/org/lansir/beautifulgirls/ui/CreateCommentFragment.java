package org.lansir.beautifulgirls.ui;

import org.lansir.beautifulgirls.R;
import org.lansir.beautifulgirls.action.CommentAction;
import org.lansir.beautifulgirls.common.Constants.Extra;
import org.lansir.beautifulgirls.exception.AkException;
import org.lansir.beautifulgirls.model.Result;
import org.lansir.beautifulgirls.proxy.Akita;
import org.lansir.beautifulgirls.utils.DialogAsyncTask;
import org.lansir.beautifulgirls.utils.FragmentUtil;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class CreateCommentFragment extends SherlockFragment {
	private EditText etCommentContent;
	private TextView tvCommentCount;
	private Integer iPid = null;
	private final int TEXT_MAX_LENGTH = 140;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_create_comment, null);
		etCommentContent = (EditText)view.findViewById(R.id.etCommentContent);
		tvCommentCount = (TextView)view.findViewById(R.id.tvCommentCount);
		etCommentContent.addTextChangedListener(new TextWatcher(){
		    public void beforeTextChanged(CharSequence s, int start,
		                                  int count, int after){
		    	
		    }
		    public void onTextChanged(CharSequence s, int start, int before, int count){
		    	tvCommentCount.setText(String.valueOf(TEXT_MAX_LENGTH-s.length()));
		    }

		    public void afterTextChanged(Editable s){
		    	
		    }
			
		});
		return view;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iPid = getArguments().getInt(Extra.PICTURE_ID);
		this.setHasOptionsMenu(true);
	}



	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.menu_create_comment, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_item_send_comment:
			if(etCommentContent.getText().toString().length() > TEXT_MAX_LENGTH){
				Toast.makeText(getSherlockActivity(), String.format(getResources().getString(R.string.comment_exceed_max_length), TEXT_MAX_LENGTH), Toast.LENGTH_SHORT).show();
			}else{
				SendCommentAsyncTask sendCommentAsyncTask = new SendCommentAsyncTask(getSherlockActivity());
				sendCommentAsyncTask.fireOnParallel();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

	private class SendCommentAsyncTask extends DialogAsyncTask<Result>{

		public SendCommentAsyncTask(SherlockFragmentActivity context) {
			super(context);
		}

		@Override
		protected void onUIBefore() throws AkException {
			
		}

		@Override
		protected Result onDoAsync() throws AkException {
			CommentAction commentAction = Akita.createAPI(CommentAction.class);
			return commentAction.creteComment("anonymous", iPid, etCommentContent.getText().toString());
		}

		@Override
		protected void onUIAfter(Result t) throws AkException {
			if(t.getRetCode() == 0){
				Toast.makeText(getSherlockActivity(),getResources().getString(R.string.send_success), Toast.LENGTH_SHORT).show();
				FragmentUtil.finish(getSherlockActivity());
			}else{
				Toast.makeText(getSherlockActivity(),getResources().getString(R.string.send_failure),Toast.LENGTH_SHORT).show();
			}
		}
		
	}

}
