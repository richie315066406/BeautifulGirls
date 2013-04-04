package org.lansir.beautifulgirls.cache;


/**
 * Key is String
 * V is specified File of file system on sd card
 */
public interface FilesCache<V> {
    
    public V get(String key);

    /**
     * @param key
     * @param value if value is null, no effect and return null
     * @return oldValue or null if has no oldValue
     */
    public V put(String key, V value);
    public V remove(String key);

    /**
     * evict cache data according to the cache size set.
     * @return
     */
    public void evict();

    /**
     * Clear all this cache data
     * Note: this method maybe Time-consuming, you may run it async
     */
    public void clearCache();

    /**
     * get current cache size in MB
     * @return
     */
    public double getCacheCurrentSizeMB();

    /**
     * set normal cache size limit in MB
     * @param cacheSizeInMB
     */
    public void setCacheSize(int cacheSizeInMB);
    
}
