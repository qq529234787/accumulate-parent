package com.wme.common.utils.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by wangmingne on 2015/5/14.
 */
public class HttpClientExecutor {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientExecutor.class);

    public static String execute(HttpUriRequest request) {
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(5 * 1000);
        requestBuilder = requestBuilder.setSocketTimeout(30 * 1000);

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultRequestConfig(requestBuilder.build());
        try (CloseableHttpClient client = clientBuilder.build()) {
            HttpResponse response = client.execute(request);

            HttpEntity resEntity = response.getEntity();
            InputStream instream = resEntity.getContent();
            try (InputStreamReader reader = new InputStreamReader(instream)) {
                StringBuilder sb = new StringBuilder();
                int c;
                while ((c = reader.read()) > -1)
                    sb.append((char) c);
                String result = sb.toString();
                if (logger.isInfoEnabled()) {
                    logger.info("http response {} : {}",response.getStatusLine().getStatusCode(), result);
                }
                return result;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
