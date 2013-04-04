package org.lansir.beautifulgirls.cache;


import java.util.Map;

/**
 * Mem level Cache
 */
public interface MemCache<K, V> {

    public V get(K key);
    public V put(K key, V value);
    public V remove(K key);

    /**
     * Clear all kvs of this MemCache
     */
    public void clear();

    /**
     * Get a snapshot of cache
     */
    public Map<K, V> snapshot();
    
}
