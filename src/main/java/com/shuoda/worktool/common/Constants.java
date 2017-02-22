package com.shuoda.worktool.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * Constants.java
 * 
 * Created by sunwei on 2017年2月21日.
 */
public class Constants {
    public static Map<String, BasicCookieStore> cookieStoreMap = new ConcurrentHashMap<>();
    public static Map<String, CloseableHttpClient> httpClientMap = new ConcurrentHashMap<>();
}
