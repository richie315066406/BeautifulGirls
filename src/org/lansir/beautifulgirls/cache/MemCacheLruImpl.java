package org.lansir.beautifulgirls.cache;

import java.util.Map;

import android.util.LruCache;


/**
 * Caution: use this impl only API Level >= 12
 */
public class MemCacheLruImpl<K, V> implements MemCache<K, V> {

    private LruCache<K, V> mCache = null;

    protected MemCacheLruImpl(int maxSize){
        mCache = new LruCache<K, V>(maxSize);

    }
    
    public V get(K key){
        return mCache.get(key);
    }
    
    public V put(K key, V value) {
        return mCache.put(key, value);
    }
    
    public V remove(K key) {
        return mCache.remove(key);
    }

    @Override
    public void clear() {
        mCache.evictAll();
    }

    public Map<K, V> snapshot() {
        return mCache.snapshot();
    }
}
