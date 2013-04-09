package org.lansir.beautifulgirls.cache;

import java.io.File;
import java.net.URLEncoder;

import org.lansir.beautifulgirls.utils.FileUtil;
import org.lansir.beautifulgirls.utils.HashUtil;
import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * 在SD卡中存储文件夹的实现 
 * 要求key.length >= 4
 */
public abstract class FilesCacheSDFoldersImpl<V> implements FilesCache<V> {
    protected static final String TAG = "FilesCacheSDFoldersImpl";

    private static final String CACHE_SIZE_KEY = "cacheSizeInMB";
    private static final String PREF_PREFIX = "filescachesd_";
    /**
     * 当evict时，判断多少小时前的文件夹会被删除
     */
    private static final int CACHE_EVICT_HOURS = 48;
    /**
     * 默认Cache Size MB
     */
    private static final int DEFAULT_CACHE_SIZE_MB = 150;

    protected String mCacheTag;
    private MemCache<String, V> mSoftBitmapCache;
    protected Context mContext;
    protected int mCacheSizeInMB;

    /**
     * 
     */
    protected FilesCacheSDFoldersImpl(Context context, String cacheTag){
        mContext = context;
        mCacheTag = cacheTag;
        mSoftBitmapCache = new MemCacheSoftRefImpl<String, V>();

        // in Config
        SharedPreferences sp = context.getSharedPreferences(PREF_PREFIX + mCacheTag, 0);
        mCacheSizeInMB = sp.getInt(CACHE_SIZE_KEY, DEFAULT_CACHE_SIZE_MB);
    }

    protected abstract V xform(String fileAbsoPathAndName);
    protected abstract void output(String fileAbsoPath, String fileName, V v);
    
	private String mapRule(String key) {
        // because the chinese char in url will break the md5.
        return HashUtil.md5(URLEncoder.encode(key));
    }

    private String getSpecifiedCacheFileName(String hashedKey) {
        return hashedKey + ".cache";
    }

    private String getSepcifiedCacheDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Android/data/" + mContext.getPackageName() + "/cache/" + mCacheTag + "/";
    }

    private String getSpecifiedCacheFilePath(String hashedKey) {
        String path = getSepcifiedCacheDir() + hashedKey.substring(0, 2)
                + "/" + hashedKey.substring(2, 4) + "/";
        return path;
    }
    
    @Override
    public V get(String key) {
        V bm = mSoftBitmapCache.get(key);
        if (bm != null) {
            return bm;
        } else {
            bm = doLoad(mapRule(key));
            mSoftBitmapCache.put(key, bm);
            return bm;
        }
    }

    private V doLoad(String hashedKey) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {    // We can read and write the media

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {    // We can only read the media

        } else {    // Something else is wrong. It may be one of many other states, but all we need
                    // to know is we can neither read nor write
              return null;
        }

        String pathAndName = getSpecifiedCacheFilePath(hashedKey)
                + getSpecifiedCacheFileName(hashedKey);
        File f = new File(pathAndName);
        if (f.exists()) {
            return xform(pathAndName);
        } else {
            return null;
        }
    }

    @Override
    public V put(String key, V value) {
        if (value != null) {
            V oldV = remove(key);
            doSave(mapRule(key), value);
            mSoftBitmapCache.put(key, value);
            return oldV;
        }
        return null;
    }

    /**
     * @param hashedKey
     * @param value
     */
    private void doSave(String hashedKey, V value) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) { // We can read and write the media

        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) { // We can only read the media
            LogUtil.v("need WRITE_EXTERNAL_STORAGE permission, otherwise part of cache can be used.");
            return;
        } else { // Something else is wrong. It may be one of many other states, but all we need
                 // to know is we can neither read nor write
            return;
        }

        // doSave V to the sd cache filesystem
        String path = getSpecifiedCacheFilePath(hashedKey);
        output(path, getSpecifiedCacheFileName(hashedKey), value);
    }

    @Override
    public V remove(String key) {
        return doDelete(mapRule(key));
    }

    private V doDelete(String hashedKey) {
        V oldV = null;
        String pathAndName = getSpecifiedCacheFilePath(hashedKey)
                + getSpecifiedCacheFileName(hashedKey);
        File f = new File(pathAndName);
        if (f.exists()) {
            oldV = doLoad(hashedKey);
            f.delete();
        }
        return oldV;
    }

    /**
     * When current size > mCacheSizeInMB, then remove some data.
     * note: maybe time-consuming
     */
    @Override
    public void evict() {
        double size = FileUtil.getFileSizeMB(new File(getSepcifiedCacheDir()));
        if (size > mCacheSizeInMB) {
            // junk it
            File cacheDir = new File(getSepcifiedCacheDir());
            File[] dirs = cacheDir.listFiles();
            for (File dir : dirs) {
                if (dir.isDirectory()) {
                    File[] dirs2 = dir.listFiles();
                    for (File dir2 : dirs2) {
                        if (dir2.isDirectory()) {
                            long diff = System.currentTimeMillis() - dir2.lastModified();
                            if (diff > CACHE_EVICT_HOURS * 60 * 60 * 1000) {
                                FileUtil.deleteFileOrDir(dir2);
                            }
                        }
                    }
                }
            }
        }
    }

    public double getCacheCurrentSizeMB() {
        return FileUtil.getFileSizeMB(new File(getSepcifiedCacheDir()));
    }

    @Override
    public void clearCache() {
        try{
            File cacheDir = new File(getSepcifiedCacheDir());
            FileUtil.deleteFileOrDir(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCacheSize(int cacheSizeInMB) {
        mCacheSizeInMB = cacheSizeInMB;
        SharedPreferences sp = mContext.getSharedPreferences(PREF_PREFIX + mCacheTag, 0);
        if (mCacheSizeInMB != sp.getInt(CACHE_SIZE_KEY, DEFAULT_CACHE_SIZE_MB)) {
            sp.edit().putInt(CACHE_SIZE_KEY, mCacheSizeInMB).apply();
        }
    }
}
