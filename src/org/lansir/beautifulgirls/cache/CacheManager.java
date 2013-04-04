package org.lansir.beautifulgirls.cache;

import java.io.File;
import java.io.FileOutputStream;

import org.lansir.beautifulgirls.utils.LogUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
public class CacheManager {

    public static <K, V> MemCache<K, V> newMemLruCache(int maxSize) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return new MemCacheLruImpl<K, V>(maxSize);
        } else {
            return new MemCacheDummyImpl<K, V>(maxSize);
        }
    }

    public static <K, V> MemCache<K, V> newMemSoftRefCache() {
        return new MemCacheSoftRefImpl<K, V>();
    }

    /**
     * default reserve data 24 hours (-1)
     * @param context
     * @return
     */
    public static SimpleCache getSimpleCache(Context context) {
        return new SimpleCacheSqliteImpl(context, "simplecache.db", "defaulttable", 1, -1);
    }

    public static SimpleCache getSimpleCache(Context context, String tagName) {
        return new SimpleCacheSqliteImpl(context, "simplecache.db", tagName, 1, -1);
    }

    public static SimpleCache getSimpleCache(Context context, String tagName, int reserveTimeHours) {
        return new SimpleCacheSqliteImpl(
                context, "simplecache.db", tagName, 1, reserveTimeHours * 3600 * 1000);
    }

    /**
     * reserve data 365 days（0）
     * @param context
     * @return
     */
    public static SimpleCache getAppData(Context context) {
        return new SimpleCacheSqliteImpl(context, "appdata.db", "defaulttable", 1, 0);
    }

    /**
     * reserve data 365 days（0）
     * @param context
     * @param tagName
     * @return
     */
    public static SimpleCache getAppData(Context context, String tagName) {
        return new SimpleCacheSqliteImpl(context, "appdata.db", tagName, 1, 0);
    }
    
    public static FilesCache<Bitmap> getImageFilesCache(Context context) {
        return new FilesCacheSDFoldersImpl<Bitmap>(context, "image0") {

            @Override
            protected Bitmap xform(String fileAbsoPath) {
                try {
                    return BitmapFactory.decodeFile(fileAbsoPath);
                }
                catch (OutOfMemoryError ooe) {
                    LogUtil.e(ooe.getMessage());
                }
                return null;
            }

            @Override
            protected void output(String fileAbsoPath, String fileName, Bitmap v) {
                try {
                    File dir = new File(fileAbsoPath);
                    dir.mkdirs();
                    File f = new File(dir, fileName);
                    FileOutputStream fos = new FileOutputStream(f);
                    v.compress(Bitmap.CompressFormat.JPEG, 75, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }


}
