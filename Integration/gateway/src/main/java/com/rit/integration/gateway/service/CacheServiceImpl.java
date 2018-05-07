package com.rit.integration.gateway.service;

import com.rit.integration.gateway.ifc.CacheService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by nirbo on 12/8/2015.
 */
@Service
public class CacheServiceImpl<K, V> implements CacheService<K, V> {

    private CacheManager cacheManager;

    private Class V;
    private Object K;
    private String cacheName;
    private Cache eventsCache;
    private static final Logger _logger = Logger.getLogger(CacheService.class);


    public CacheServiceImpl() {
    }

    @PostConstruct
    public void buildCache() {
        eventsCache = cacheManager.getCache("eventsCache");

    }

    @PreDestroy
    public void clearCache() {
        eventsCache.clear();
    }


    @Autowired
    @Qualifier(value = "cacheManager")
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public V getEntityByKey(K key) {
        V result = null;
        Cache.ValueWrapper valueWrapper = this.eventsCache.get(key);
        if (valueWrapper != null) {
            result = (V) valueWrapper.get();
        }
        return result;
    }

    public void putEntity(K key, V value) {
        eventsCache.putIfAbsent(key, value);

    }

    public V remvoeEntityByKey(K key) {
        V entityByKey = this.getEntityByKey(key);
        if (entityByKey != null) {
            eventsCache.evict(key);
        }
        return  entityByKey;



    }

    public Cache getCacheByName(String name) {
        Cache cache = cacheManager.getCache(name);
        return cache;
    }

    public boolean isEmpty() {
        ConcurrentMap cache = (ConcurrentMap) eventsCache.getNativeCache();
        return cache.isEmpty();
    }

    public Cache getEventsCache() {
        return eventsCache;
    }
}



