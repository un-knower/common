package com.postss.common.util;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.postss.common.base.entity.RequestEntity;
import com.postss.common.util.WebResourceUtil.RequestMethod;

/**
 * 网络资源工具类
 * 推荐使用{@link com.postss.common.util.HttpClientUtil}}
 * @author jwSun
 * @date 2017年6月14日 下午4:18:56
 */
@SuppressWarnings("deprecation")
public class WebUtil {

    /**
     * 获得url资源
     * @date 2017年6月23日 下午3:36:03
     * @param requestEntity 请求参数实体
     * @return ResponseEntity 返回参数实体
     */
    public static <T> ResponseEntity<T> getURLResponse(RequestEntity<T> requestEntity) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            connection = getConnection(requestEntity.getUrl().toString());
            setHeader(connection, requestEntity.getHeaders().toSingleValueMap());

            if (RequestMethod.POST.toString().equalsIgnoreCase(requestEntity.getMethod().toString())) {
                beforePost(connection, requestEntity);
            } else if (RequestMethod.GET.toString().equalsIgnoreCase(requestEntity.getMethod().toString())) {
                beforeGet(connection, requestEntity);
            } else {
                throw new RuntimeException();
            }
            return readStream(connection.getInputStream(), connection.getResponseCode(), requestEntity.getCharset());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static <T> void beforeGet(HttpURLConnection connection, T paramStr) throws Exception {
        connection.setRequestMethod(RequestMethod.GET.toString());
    }

    private static <T> void beforePost(HttpURLConnection connection, RequestEntity<T> requestEntity) {
        DataOutputStream dataOutputStream = null;
        try {
            connection.setRequestMethod(RequestMethod.POST.toString());
            T params = requestEntity.getBody();
            if (params != null) {
                dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.write(params.toString().getBytes(requestEntity.getCharset()));
                dataOutputStream.flush();
                dataOutputStream.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(dataOutputStream);
        }
    }

    private static void setHeader(HttpURLConnection connection, Map<String, String> headers) {
        if (null != headers) {
            Iterator<String> iterator = headers.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next().toString();
                String value = headers.get(key);
                connection.setRequestProperty(key, value);
            }
        }
    }

    /**
     * 流转化为文本
     * @author jwSun
     * @param <T>
     * @param <T>
     * @date 2017年4月20日 下午6:43:17
     * @param inputStream 输入流
     * @param charset 字符编码
     * @return
     */
    @SuppressWarnings({ "static-access", "unchecked" })
    private static <T> ResponseEntity<T> readStream(InputStream inputStream, int responseCode, String charset) {
        StringBuffer sb = new StringBuffer();
        BufferedInputStream bufferedInputStream = null;
        try {
            int len = 0;
            byte[] b = new byte[1024];
            sb.setLength(0);
            bufferedInputStream = new BufferedInputStream(inputStream);
            while ((len = bufferedInputStream.read(b)) != -1) {
                sb.append(new String(b, 0, len, charset));
            }
            new ResponseEntity<T>(HttpStatus.valueOf(responseCode));
            return new ResponseEntity<T>(HttpStatus.valueOf(responseCode)).ok((T) sb.toString());
        } catch (Exception e) {
            try {
                return new ResponseEntity<T>(HttpStatus.valueOf(responseCode));
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }

    private static HttpURLConnection getConnection(String urlString) {
        if (urlString == null || urlString == "") {
            throw new RuntimeException("urlString is null");
        }
        if (urlString.indexOf("http") == -1) {
            urlString = "http://" + urlString;
        }
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            if (urlString.indexOf("https://") != -1) {
                connection = getHttpsConnection(urlString, url);
            }
            if (connection == null) {
                connection = (HttpURLConnection) url.openConnection();
            }
            connection.setDoInput(true);// 可以使用conn.getOutputStream().write()
            connection.setDoOutput(true);// 可以使用conn.getInputStream().read()
            connection.setUseCaches(false);// 请求不能使用缓存
            connection.setInstanceFollowRedirects(true);// 设置能自动执行重定向
        } catch (Exception e) {
        }

        return connection;
    }

    private static HttpURLConnection getHttpsConnection(String urlString, URL url) {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            //connection.setSSLSocketFactory(sslSocketFactory);
        } catch (Exception e) {

        }
        return connection;
    }

}
