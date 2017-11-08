package com.postss.common.kylin.client;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.postss.common.base.entity.HttpRequestBuilder;
import com.postss.common.enums.CharsetEnum;
import com.postss.common.kylin.entity.KylinAuth;
import com.postss.common.kylin.exception.KylinAuthenticationException;
import com.postss.common.kylin.exception.KylinConnectionException;
import com.postss.common.kylin.exception.KylinParamsException;
import com.postss.common.kylin.exception.KylinUnKnowException;
import com.postss.common.kylin.search.KylinBodySearch;
import com.postss.common.kylin.search.KylinSearch;
import com.postss.common.kylin.search.SimpleKylinSearch;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.ConvertUtil;
import com.postss.common.util.HttpClientUtil;
import com.postss.common.util.ReflectUtil;
import com.postss.common.util.StringUtil;
import com.postss.common.util.WebRestApiUtil;

/**
 * 默认kylin客户端操作
 * @className SimpleKylinClient
 * @author jwSun
 * @date 2017年8月22日 下午7:12:06
 * @version 1.0.0
 */
public class SimpleKylinClient implements Closeable, KylinClient {

    private Logger log = LoggerUtil.getLogger(getClass());

    private final CloseableHttpClient httpClient;
    private final KylinAuth kylinAuth;
    /**http://url:port/kylin**/
    private final String kylinUrl;
    private final String API = "/api";
    private final String AUTHENTICATION = "/user/authentication";
    private final String QUERY = "/query";
    private final KylinSearch checkAuthSearch;

    public SimpleKylinClient(String kylinUrl, KylinAuth kylinAuth, CloseableHttpClient httpClient) {
        super();
        this.kylinUrl = kylinUrl;
        this.httpClient = httpClient;
        this.kylinAuth = kylinAuth;
        this.checkAuthSearch = new SimpleKylinSearch().setMethod(HttpMethod.GET).setUrl(AUTHENTICATION);
        doAuthentication();
    }

    public SimpleKylinClient(String kylinUrl, KylinAuth kylinAuth) {
        this(kylinUrl, kylinAuth, HttpClientUtil.getNewHttpClient());
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public KylinAuth getKylinAuth() {
        return kylinAuth;
    }

    public String getKylinUrl() {
        return kylinUrl;
    }

    @Override
    public void close() throws IOException {
        IOUtils.closeQuietly(httpClient);
    }

    @Override
    public String toString() {
        return "KylinClient [httpClient=" + httpClient + ", kylinAuth=" + kylinAuth + ", kylinUrl=" + kylinUrl
                + ", API=" + API + ", AUTHENTICATION=" + AUTHENTICATION + "]";
    }

    @Override
    public String executeQueryBody(KylinBodySearch kylinBodySearch) {
        StringEntity stringEntity = new StringEntity(kylinBodySearch.searchJson(), CharsetEnum.UTF_8.getCharset());
        stringEntity.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        ResponseEntity<String> responseBody = HttpClientUtil
                .execute(HttpRequestBuilder.post(kylinUrl + API + QUERY).setEntity(stringEntity), getHttpClient());
        Callback callback = checkResult(responseBody);
        if (callback == Callback.RESTART) {
            return executeQueryBody(kylinBodySearch);
        } else if (callback == Callback.RETURN) {
            return responseBody.getBody();
        } else {
            throw new KylinUnKnowException("responseBody is : " + responseBody);
        }
    }

    @Override
    public synchronized boolean checkAuthentication() {
        String response = executeQueryJson(checkAuthSearch);
        if (StringUtil.notEmpty(response) && (!response.equals("{}"))) {
            log.info("checkAuthentication : response {} , is authorized", response);
            return true;
        } else {
            log.info("checkAuthentication : response {} , is unauthorized", response);
            return false;
        }
    }

    @Override
    public synchronized boolean doAuthentication() {
        ResponseEntity<String> responseBody = HttpClientUtil.execute(
                HttpRequestBuilder.post(kylinUrl + API + AUTHENTICATION).setHeader(kylinAuth.getAuthenticationHeader()),
                httpClient);
        if (responseBody.getStatusCode() != HttpStatus.OK) {
            throw new KylinAuthenticationException(String.valueOf(responseBody.getStatusCodeValue()));
        } else {
            log.info("kylin connection and authentication ok");
            return true;
        }
    }

    @Override
    public String executeQueryJson(KylinSearch kylinSearch) {
        if (!kylinSearch.checkParams()) {
            throw new KylinParamsException();
        }
        HttpRequestBuilder httpRequestBuilder = null;

        Field[] fields = kylinSearch.getClass().getDeclaredFields();
        String realUrl = kylinUrl + API
                + WebRestApiUtil.getRealUrl(kylinSearch.getUrl(), ConvertUtil.beanConvertToMap(kylinSearch));
        if (kylinSearch.getMethod() == HttpMethod.GET) {
            httpRequestBuilder = HttpRequestBuilder.get(realUrl);
        } else {
            httpRequestBuilder = HttpRequestBuilder.post(realUrl);
        }
        Set<String> filterNames = kylinSearch.getFilterName();
        for (Field field : fields) {
            if (filterNames != null && filterNames.contains(field.getName())) {
                continue;
            }
            Method readMethod = ReflectUtil.getReadMethod(kylinSearch.getClass(), field.getName());
            if (readMethod != null) {
                Object value;
                try {
                    value = readMethod.invoke(kylinSearch);
                } catch (Exception e) {
                    continue;
                }
                if (value != null) {
                    httpRequestBuilder.addParameter(field.getName(), String.valueOf(value));
                }
            }
        }
        ResponseEntity<String> responseBody = HttpClientUtil.execute(httpRequestBuilder, httpClient);
        Callback callback = checkResult(responseBody);
        if (callback == Callback.RESTART) {
            return executeQueryJson(kylinSearch);
        } else if (callback == Callback.RETURN) {
            return responseBody.getBody();
        } else {
            throw new KylinUnKnowException("responseBody is : " + responseBody);
        }

    }

    private Callback checkResult(ResponseEntity<String> responseBody) {
        if (responseBody.getStatusCode() != HttpStatus.OK) {
            if (responseBody.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                if (!checkAuthentication()) {
                    log.info("kylin UNAUTHORIZED ,now doAuthentication");
                    if (doAuthentication()) {
                        log.info("restart executeQuery");
                        return Callback.RESTART;
                    }
                } else {
                    log.error("UNAUTHORIZED");
                    throw new KylinAuthenticationException(String.valueOf(responseBody.getStatusCodeValue()));
                }
            } else if (responseBody.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new KylinConnectionException(responseBody.getStatusCode(), responseBody.getBody());
            } else {
                log.error("error,responseBody is " + responseBody);
            }
        } else {
            return Callback.RETURN;
        }
        throw new KylinUnKnowException("responseBody is : " + responseBody);
    }

    enum Callback {
        RESTART, RETURN, THROW;
    }

}
