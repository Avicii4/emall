package com.emall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Guava缓存
 *
 * @author Harry Chou
 * @date 2019/5/25
 */
@Slf4j
public class TokenCache {

    public static final String TOKEN_PREFIX="token_";

    // 设置本地缓存初始容量、最大容量和有效期
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000)
            .maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                // 默认的数据加载实现，在key为空时调用此方法加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                value = null;
            }
            return value;
        } catch (Exception e) {
            log.error("local cache gets error", e);
        }
        return null;
    }

}
