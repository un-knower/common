package com.postss.common.util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.postss.common.base.entity.HttpRequestBuilder;
import com.postss.common.base.entity.RequestEntity;
import com.postss.common.constant.Constant.CHARSET;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.system.exception.ConnectionException;

/**
 * HttpClient工具类
 * @author jwSun
 * @date 2017年6月14日 上午11:00:34
 */
public class HttpClientUtil {

    private static Logger log = LoggerUtil.getLogger(HttpClientUtil.class);
    //连接池
    private static final PoolingHttpClientConnectionManager poolManager;
    //请求重试处理
    private static final HttpRequestRetryHandler httpRequestRetryHandler;
    //请求配置
    private static final RequestConfig requestConfig;

    // 最大连接数
    public final static int MAX_TOTAL_CONNECTIONS = 100;
    // 获取连接的最大等待时间
    public final static int WAIT_TIMEOUT = 30000;// 30s
    // 每个路由最大连接数
    public final static int MAX_ROUTE_CONNECTIONS = 50;
    // 连接超时时间
    public final static int CONNECT_TIMEOUT = 10000;// 10s
    // 读取超时时间
    public final static int READ_TIMEOUT = 10000;// 10s
    //允许跳转
    public final static boolean REDIRECTS_ENABLED = true;

    public static PoolingHttpClientConnectionManager getConnectionManager() {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
                .register("http", plainsf).register("https", sslsf).build();
        PoolingHttpClientConnectionManager poolManager = new PoolingHttpClientConnectionManager(registry);

        // 设置最大连接数
        poolManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        // 设置获取连接的最大等待时间
        poolManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
        return poolManager;
    }

    public static HttpRequestRetryHandler getHttpRequestRetryHandler() {
        return new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 5) {// 如果已经重试了5次，就放弃                    
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试                    
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常                    
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时                    
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达                    
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝                    
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常                    
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };
    }

    public static RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setRedirectsEnabled(REDIRECTS_ENABLED).build();
    }

    static {
        poolManager = getConnectionManager();
        httpRequestRetryHandler = getHttpRequestRetryHandler();
        requestConfig = getRequestConfig();
    }

    public static CloseableHttpClient getNewHttpClient() {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolManager)
                .setRetryHandler(httpRequestRetryHandler).setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    private static CloseableHttpClient httpClient = getNewHttpClient();

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * WebUtil快速转HttpclientUtil
     * @author jwSun
     * @date 2017年6月14日 下午4:18:17
     * @param requestEntity
     * @return
     */
    public static ResponseEntity<String> execute(RequestEntity<String> requestEntity) {
        CloseableHttpClient httpClient = getHttpClient();
        ResponseEntity<String> responseEntity = execute(requestEntity, httpClient);
        return responseEntity;
    }

    /**
     * 原生HttpClient使用方法
     * @author jwSun
     * @date 2017年6月14日 下午4:21:29
     * @param request
     * @return
     */
    public static ResponseEntity<String> execute(HttpRequestBase request) {
        CloseableHttpClient httpClient = getHttpClient();
        ResponseEntity<String> responseEntity = execute(request, httpClient);
        return responseEntity;
    }

    /**
     * 推荐使用
     * ResponseEntity<String> get = HttpClientUtil.execute(HttpRequestBuilder.get("url").addParameter("key", "value"));
     * ResponseEntity<String> post = HttpClientUtil.execute(HttpRequestBuilder.post("url").addParameter("key", "value"))
     * @author jwSun
     * @date 2017年6月15日 上午10:41:05
     * @param httpRequestBuilder
     * @return
     */
    public static ResponseEntity<String> execute(HttpRequestBuilder httpRequestBuilder) {
        return execute(httpRequestBuilder.build());
    }

    /**
     * WebUtil快速转HttpclientUtil
     * @author jwSun
     * @date 2017年6月14日 下午4:18:17
     * @param requestEntity
     * @return
     */
    public static ResponseEntity<String> execute(RequestEntity<String> requestEntity, HttpClient httpClient) {
        HttpRequestBase request;
        HttpMethod method = requestEntity.getMethod();
        if (method == HttpMethod.GET) {
            request = new HttpGet(requestEntity.getUrl());
        } else if (method == HttpMethod.POST) {
            HttpPost httpPost = new HttpPost(requestEntity.getUrl());
            StringEntity stringEntity = new StringEntity(requestEntity.getBody(), requestEntity.getCharset());
            stringEntity.setContentType(requestEntity.getHeaders().getContentType().toString());
            httpPost.setEntity(stringEntity);
            request = httpPost;
        } else {
            throw new RuntimeException("暂时只支持 get 与 post");
        }
        return execute(request, httpClient);
    }

    /**
     * 原生HttpClient使用方法
     * @author jwSun
     * @date 2017年6月14日 下午4:21:29
     * @param request
     * @return
     */
    public static ResponseEntity<String> execute(HttpRequestBase request, HttpClient httpClient) {
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();
            String body = EntityUtils.toString(responseEntity, CHARSET.UTF_8);
            EntityUtils.consume(response.getEntity());
            return ResponseEntity.status(response.getStatusLine().getStatusCode()).body(body);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            throw new ConnectionException(request.toString(), e);
        } finally {
            request.releaseConnection();
        }
    }

    /**
     * 推荐使用
     * ResponseEntity<String> get = HttpClientUtil.execute(HttpRequestBuilder.get("url").addParameter("key", "value"));
     * ResponseEntity<String> post = HttpClientUtil.execute(HttpRequestBuilder.post("url").addParameter("key", "value"))
     * @author jwSun
     * @date 2017年6月15日 上午10:41:05
     * @param httpRequestBuilder
     * @return
     */
    public static ResponseEntity<String> execute(HttpRequestBuilder httpRequestBuilder, HttpClient httpClient) {
        return execute(httpRequestBuilder.build(), httpClient);
    }
}
