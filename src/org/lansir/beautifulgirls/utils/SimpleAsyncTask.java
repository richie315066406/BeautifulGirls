package org.lansir.beautifulgirls.utils;

import org.lansir.beautifulgirls.exception.AkException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * Date: 12-4-9
 * Time: 上午11:20
 *
 * @author zhe.yangz
 */
public abstract class SimpleAsyncTask<T> extends AsyncTask<Integer, Integer, T> {
    protected AkException mAkException = null;
    private Context mContext = null;

    /**
     * guarantees the method be invoked on ui thread once time when task start.
     */
    protected void onUITaskStart() {};

    @Override
    protected void onPreExecute() {
        super.onPreExecute();    //defaults

        onUITaskStart();
        try{
            onUIBefore();
        } catch (AkException akException) {
            mAkException = akException;
        }
    }

    public AsyncTask<Integer, Integer, T> fire() {
        return execute(new Integer[] {0});
    }

    public AsyncTask<Integer, Integer, T> fireOnParallel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Integer[]{0});
        } else {
            return execute(new Integer[] {0});
        }
    }

    public AsyncTask<Integer, Integer, T> fire(Context context) {
        mContext = context;
        return fire();
    }

    public AsyncTask<Integer, Integer, T> fireOnParallel(Context context) {
        mContext = context;
        return fireOnParallel();
    }

    @Override
    protected T doInBackground(Integer... integers) {
        try {
            if (mAkException == null) {
                return onDoAsync();
            } else {
                return null;
            }
        } catch (AkException akException) {
            mAkException = akException;
            return null;
        } catch (Exception ex){
        	mAkException = new AkException(ex.getMessage(), ex);
            return null;
        }
    }

    protected abstract void onUIBefore() throws AkException;
    protected abstract T onDoAsync() throws AkException;
    /**
     * it may not be executed if have exception before.
     * @param t
     * @throws AkException
     */
    protected abstract void onUIAfter(T t) throws AkException;

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);    //defaults

        if (mAkException != null) {
            onHandleAkException(mAkException);
        } else {
            try {
                onUIAfter(t);
            } catch (AkException akException) {
                onHandleAkException(mAkException);
            }
        }
        onUITaskEnd();
    }

    @Override
    protected void onCancelled(T t) {
        onUITaskEnd();
    }

    protected void onHandleAkException(AkException mAkException) {
        LogUtil.e(mAkException.toString());

        if (mContext != null) {
            Toast.makeText(mContext, mAkException.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * guarantees the method be invoked on ui thread once time when task quit.
     */
    protected void onUITaskEnd() {};

    /**
     * public of the method publishProgress, but must also be called in doinbackground.
     * @param values
     */
    public void publishProgressPublic(Integer... values) {
        publishProgress(values);
    }

}
