
package org.lansir.beautifulgirls.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Dummy Mem Cache Impl
 */
public class MemCacheDummyImpl<K, V> implements MemCache<K, V> {
    protected MemCacheDummyImpl(int maxSize) {
    }

    @Override
    public V get(K key) {
        return null;  // defaults
    }

    @Override
    public V put(K key, V value) {
        return null;  // defaults
    }

    @Override
    public V remove(K key) {
        return null;  // defaults
    }

    @Override
    public void clear() {
        // defaults
    }

    @Override
    public Map<K, V> snapshot() {
        return new HashMap<K, V>();  // defaults
    }
}
