package com.shuoda.worktool.service;

import com.shuoda.worktool.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * PaperDownloadService.java
 * <p>
 * Created by sunwei on 2017年2月21日.
 */
public class PaperDownloadService {
    public void login(String userName, String password) throws IOException {

    }

    public static void main(String[] args) {
        try {

            HttpClientBuilder builder = HttpClients.custom()
                    .disableAutomaticRetries() //关闭自动处理重定向
                    .setRedirectStrategy(new LaxRedirectStrategy());//利用LaxRedirectStrategy处理POST重定向问题

            CloseableHttpClient client = builder.build();

            final List<NameValuePair> parameterList = new ArrayList<>();
            HttpGet loginGet = new HttpGet("https://sso.zxxk.com/login");
            HttpResponse loginGetResponse = client.execute(loginGet);
            Set<String> postInputSet = new HashSet<>();
            postInputSet.add("lt");
            postInputSet.add("execution");
            postInputSet.add("_eventId");
            postInputSet.add("rememberMe");
            postInputSet.add("xkw_d");

            Document loginGetHtml = Jsoup.parse(EntityUtils.toString(loginGetResponse.getEntity(), "utf-8"));
            Element ordinaryLoginForm = loginGetHtml.getElementById("ordinaryLoginForm");
            ordinaryLoginForm.getElementsByTag("input").forEach(new Consumer<Element>() {

                @Override
                public void accept(Element element) {
                    String name = element.attr("name");
                    if (!postInputSet.contains(name)) {
                        return;
                    }

                    if ("xkw_d".equals(name)) {
                        parameterList.add(new BasicNameValuePair(name, element.attr("id")));
                    } else {
                        parameterList.add(new BasicNameValuePair(name, element.attr("value")));
                    }

                }
            });

            HttpPost loginPost = new HttpPost("https://sso.zxxk.com/login");
            parameterList.add(new BasicNameValuePair("username", "18612863982"));
            parameterList.add(new BasicNameValuePair("password", "mimaliuge1"));
            loginPost.setEntity(new UrlEncodedFormEntity(parameterList, "utf-8"));

            HttpResponse loginPostResponse = client.execute(loginPost);
            HttpUtils.printResponse(loginPostResponse);
//            if (loginPostResponse.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY || loginPostResponse.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY) {
//                HttpResponse loginRedirectResponse = null;
//                String location = loginPostResponse.getFirstHeader("Location").getValue();
//
//                do {
//                    HttpGet httpGet = new HttpGet(location);
//                    System.out.println(location);
//                    loginRedirectResponse = client.execute(httpGet);
//                    location = loginRedirectResponse.getFirstHeader("Location").getValue();
//
//                } while (loginRedirectResponse.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_PERMANENTLY || loginRedirectResponse.getStatusLine().getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY);
//
//                HttpUtils.printResponse(loginRedirectResponse);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
