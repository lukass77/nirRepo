package com.rit.integration.gateway.ifc;

import org.springframework.cache.Cache;

/**
 * Created by nirbo on 12/2/2015.
 */
public interface CacheService<K,V> {

    V getEntityByKey(K key);
    void putEntity(K key, V value);
    V remvoeEntityByKey(K key);
    Cache getCacheByName(String name);
    boolean isEmpty();


    Cache getEventsCache();
}
