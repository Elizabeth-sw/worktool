package com.shuoda.worktool.utils;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import com.shuoda.worktool.common.Constants;

/**
 * HttpUtils.java
 * 
 * Created by sunwei on 2017年2月21日.
 */
public class HttpUtils {
    

    public static void printResponse(HttpResponse httpResponse) throws ParseException, IOException {
        // 获取响应消息实体
        HttpEntity entity = httpResponse.getEntity();
        // 响应状态
        System.out.println("status:" + httpResponse.getStatusLine());
        System.out.println("headers:");
        HeaderIterator iterator = httpResponse.headerIterator();
        while (iterator.hasNext()) {
            System.out.println("\t" + iterator.next());
        }
        // 判断响应实体是否为空
        if (entity != null) {
            String responseString = EntityUtils.toString(entity);
            System.out.println("response length:" + responseString.length());
            System.out.println("response content:" + responseString.replace("\r\n", ""));
        }
    }

    public static void setCookieStore(String userName, HttpResponse httpResponse) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String sessionId = setCookie.substring("ASP.NET_SessionId=".length(), setCookie.indexOf(";"));
        System.out.println("ASP.NET_SessionId:" + sessionId);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("ASP.NET_SessionId", sessionId);
        cookie.setVersion(0);
        cookie.setDomain("zxxk.com");
        cookie.setPath("/");
        cookieStore.addCookie(cookie);
        
        Constants.cookieStoreMap.put(userName, cookieStore);
    }
}
