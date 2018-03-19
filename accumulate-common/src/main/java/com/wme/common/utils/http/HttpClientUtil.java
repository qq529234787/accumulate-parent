package com.wme.common.utils.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title: accumulate-master
 * @Auther: ming
 * @Date: 2017/10/21
 * @Version: 1.0
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static class HttpClientHolder {
        private static CloseableHttpClient httpClient = createHttpClient(3000, 1000, CookieSpecs.IGNORE_COOKIES, 2000, 100, null);
    }

    private static CloseableHttpClient createDefaultHttpClient() {
        return HttpClientHolder.httpClient;
    }

    public static String get(String url) throws Exception  {
        Asserts.notNull(url, "url can't be null");
        String result = "";
        CloseableHttpClient client = createDefaultHttpClient();
        CloseableHttpResponse httpResult = null;
        HttpGet method= new HttpGet(url);
        try {
            httpResult = client.execute(method);
            result = getResponseBody(httpResult);
        } catch(Exception e) {

        } finally{
            try {
                EntityUtils.consume(httpResult.getEntity());
            } catch(Exception e) {
                //
            }
        }
        return result;
    }

    public static String getWithCookie(String url, List<BasicClientCookie> cookies) throws Exception {
        Asserts.notNull(url, "url can't be null");
        String result = "";
        CloseableHttpClient client = createHttpClient(1, 1, CookieSpecs.DEFAULT, 2000, 100, cookies);
        CloseableHttpResponse httpResult = null;
        HttpGet method= new HttpGet(url);
        try {
            httpResult = client.execute(method);
            result = getResponseBody(httpResult);
        } catch(Exception e) {

        } finally{
            try {
                EntityUtils.consume(httpResult.getEntity());
                client.close();
            } catch(Exception e) {
                //
            }
        }
        return result;
    }

    /**
     * 以ContentType=application/json的格式post发送json参数请求
     * @param url
     * @param jsonParams
     * @return
     * @throws Exception
     */
    public static String postJSON(String url, String jsonParams) throws Exception{
        Asserts.notNull(url, "url can't be null");
        String result = "";
        CloseableHttpClient client = createDefaultHttpClient();
        CloseableHttpResponse httpResult = null;
        try {
            HttpPost method= new HttpPost(url);
            if(StringUtils.isNotEmpty(jsonParams)) {
                StringEntity entity = new StringEntity(jsonParams, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            httpResult = client.execute(method);
            result = getResponseBody(httpResult);
        } catch(Exception e) {

        } finally{
            try {
                EntityUtils.consume(httpResult.getEntity());
            } catch(Exception e) {
                //
            }
        }
        return result;
    }

    public static String post(String url, Map<String, String> params) throws Exception {
        Asserts.notNull(url, "url can't be null");
        String result = "";
        CloseableHttpClient client = createDefaultHttpClient();
        CloseableHttpResponse httpResult = null;
        try {
            HttpPost method= new HttpPost(url);
            if(params!=null && !params.isEmpty()) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                method.setEntity(uefEntity);
            }
            httpResult = client.execute(method);
            result = getResponseBody(httpResult);
        } catch(Exception e) {

        } finally{
            try {
                EntityUtils.consume(httpResult.getEntity());
            } catch(Exception e) {
                //
            }
        }
        return result;
    }

    /**
     * post带stream的
     * @param url
     * @param params
     * @param files
     * @return
     * @throws Exception
     */
    public static String postInputStream(String url, Map<String, String> params,Map<String,InputStream> files) throws Exception {
        Asserts.notNull(url, "url can't be null");
        String result = "";
        CloseableHttpClient client = createDefaultHttpClient();
        CloseableHttpResponse httpResult = null;

        try {
            HttpPost method = initPostMethod(url,params,files);
            httpResult = client.execute(method);
            result = getResponseBody(httpResult);
        } finally {
            try {
                EntityUtils.consume(httpResult.getEntity());
            } catch (Exception e) {
                logger.error("postInputStream post err ... ", e);
            }
        }
        return result;
    }
    private static HttpPost initPostMethod(String url,Map<String, String> params,Map<String,InputStream> files) throws  Exception{
        Asserts.notNull(url, "url can't be null");
        HttpPost method = new HttpPost(url);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (params!=null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), entry.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            }
        }

        if (files!=null && !files.isEmpty()) {
            Set<Map.Entry<String, InputStream>> entries = files.entrySet();
            for (Map.Entry<String, InputStream> entry : entries) {
                builder.addPart(entry.getKey(), new InputStreamBody(entry.getValue(), ""));
            }
        }

        HttpEntity entity = builder.build();
        method.setEntity(entity);
        return  method;
    }

    public static String postWithCookie(String url, Map<String, String> params, List<BasicClientCookie> cookies) throws Exception {
        Asserts.notNull(url, "url can't be null");
        String result = "";
        CloseableHttpClient client = createHttpClient(1, 1, CookieSpecs.DEFAULT, 2000, 100, cookies);
        CloseableHttpResponse httpResult = null;
        try {
            HttpPost method= new HttpPost(url);
            if(params!=null && !params.isEmpty()) {
                List<NameValuePair> formparams = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
                method.setEntity(uefEntity);
            }
            httpResult = client.execute(method);
            result = getResponseBody(httpResult);
        } catch(Exception e) {

        } finally{
            try {
                EntityUtils.consume(httpResult.getEntity());
                client.close();
            } catch(Exception e) {
                //
            }

        }
        return result;
    }

    public static String getResponseBody(CloseableHttpResponse response) throws Exception {
        String responseBody = "";
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            long len = entity.getContentLength();
            if (len != -1 && len < 2048) {
                responseBody = EntityUtils.toString(entity, "UTF-8");
            }
            else {
                InputStream instream = entity.getContent();
                try {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(instream, writer, "UTF-8");
                    responseBody = writer.toString();

                } finally {
                    instream.close();
                }
            }
        }
        return responseBody;
    }

    public static CloseableHttpClient createHttpClient(int connectTimeOut, int soTimeOut, String ignoreCookies, int maxTotal, int maxPerRoute, List<BasicClientCookie> cookies) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(soTimeOut)
                .setConnectTimeout(connectTimeOut)
                .setConnectionRequestTimeout(500)
                .setCookieSpec(ignoreCookies)
                .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        cm.setDefaultSocketConfig(socketConfig);
        Header header = new BasicHeader(HttpHeaders.CONNECTION, "Keep-Alive");
        CookieStore cookieStore = new BasicCookieStore();
        if (cookies!=null && !cookies.isEmpty() && !ignoreCookies.equals(CookieSpecs.IGNORE_COOKIES)) {
            for (BasicClientCookie cookie : cookies) {
                cookieStore.addCookie(cookie);
            }
        }
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {

                    @Override
                    public long getKeepAliveDuration(
                            org.apache.http.HttpResponse response,
                            HttpContext context) {
                        return 30000;
                    }
                })
                .setDefaultHeaders(Arrays.asList(header))
                .setDefaultCookieStore(cookieStore)
                .build();

        return client;
    }



    public static void main(String[] a) {
//		CookieStore cookieStore = new BasicCookieStore();
//        BasicClientCookie cookie = new BasicClientCookie("gengbo", "hahaha");
//        cookie.setDomain("terp.mall.autohome.com.cn");
//        cookie.setPath("/");
//        cookieStore.addCookie(cookie);
        final CloseableHttpClient client = createDefaultHttpClient();
        try {
            ExecutorService executor = Executors.newFixedThreadPool(8);
            for (int i = 0; i < 300; i++) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        HttpGet method = null;
                        String responseBody = "";
                        HttpEntity entity = null;
                        CloseableHttpResponse response = null;
                        try {
                            method = new HttpGet("http://www.baidu.com");
                            response = client.execute(method);
//                            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//                            formparams.add(new BasicNameValuePair("username", "admin"));
//                            formparams.add(new BasicNameValuePair("password", "123456"));
//                            HttpPost me = new HttpPost("http://terp.mall.autohome.com.cn:8080/user/login.jtml?userId=1&name=admin");
//                            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
//                            me.setEntity(uefEntity);
                            System.out.println("ok");
//                            try{
//                                entity = response.getEntity();
//                                if (entity != null) {
//                                    long len = entity.getContentLength();
//                                    if (len != -1 && len < 2048) {
//                                        responseBody = EntityUtils.toString(entity);
//                                    }
//                                    else {
//                                        InputStream instream = entity.getContent();
//                                        try {
//                                            StringWriter writer = new StringWriter();
//                                            IOUtils.copy(instream, writer, "UTF-8");
//                                            responseBody = writer.toString();
//
//                                        } finally {
//                                            instream.close();
//                                        }
//                                    }
//                                    System.out.println(responseBody);
//                                }
//                            }
//                            finally{
//                                EntityUtils.consume(entity);
//                                response.close();
////                                client.close();
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally{
//                            if(method!=null){
//                                method.releaseConnection();
//                            }
                            try {
                                EntityUtils.consume(response.getEntity());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
