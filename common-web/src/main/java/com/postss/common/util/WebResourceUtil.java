package com.postss.common.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import com.postss.common.constant.Constant;

/**
 * 网络资源工具
 * 推荐使用{@link HttpClientUtil}}
 * @author jwSun
 * @date 2017年2月5日 下午4:49:04
 */
@Deprecated
public class WebResourceUtil {

    public enum RequestMethod {
        GET, POST
    }

    private static SSLSocketFactory sslSocketFactory;

    /**
     * 上传文件
     * @author jwSun
     * @date 2017年5月2日 下午2:00:49
     * @param urlString
     * @param file
     * @param charset
     * @return
     */
    public static String getURLResponse(String urlString, MultipartFile file, String charset) {
        //List<String> list = new ArrayList<String>();
        HttpURLConnection connection = null;
        OutputStream outputStream = null;
        try {
            String BOUNDARY = "---------666666"; // 定义数据分隔线  
            connection = getConnection(urlString);
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            outputStream = new DataOutputStream(connection.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线  
            //int leng = list.size();
            //for (int i = 0; i < leng; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file" + "\";filename=\"" + file.getOriginalFilename()
                    + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] data = sb.toString().getBytes();
            outputStream.write(data);
            DataInputStream in = new DataInputStream(file.getInputStream());
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
            outputStream.write("\r\n".getBytes()); //多个文件时，二个文件之间加入这个  
            in.close();
            //}
            outputStream.write(end_data);
            outputStream.flush();
            outputStream.close();
            return readStream(connection.getInputStream(), charset);
        } catch (Exception e) {

        } finally {
            IOUtils.closeQuietly(outputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /*static {
        try {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContextInstance = SSLContext.getInstance("SSL", "SunJSSE");
            sslContextInstance.init(null, tm, new java.security.SecureRandom());
            sslSocketFactory = sslContextInstance.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static SSLSocketFactory getSSLSocketFactory() {
        return sslSocketFactory;
    }

    /**
     * 获取指定URL页面或资源
     * @author sjw
     * @date 2016年9月29日 下午5:11:14
     * @param urlString url地址
     */
    public static String getURLResponse(String urlString) {
        return getURLResponse(urlString, null, RequestMethod.GET, Constant.CHARSET.UTF_8, null);
    }

    /**
     * 获取指定URL页面或资源
     * @author sjw
     * @date 2016年9月29日 下午5:11:58
     * @param urlString
     *            url地址
     * @param method
     *            访问方法:GET/POST
     * @return
     */
    public static String getURLResponse(String urlString, Map<String, String> params) {
        return getURLResponse(urlString, params, RequestMethod.GET, Constant.CHARSET.UTF_8, null);
    }

    /**
     * 获取指定URL页面或资源
     * @author sjw
     * @date 2016年9月29日 下午5:11:58
     * @param urlString
     *            url地址
     * @param method
     *            访问方法:GET/POST
     * @return
     */
    public static String getURLResponse(String urlString, Map<String, String> params, RequestMethod method) {
        return getURLResponse(urlString, params, method, Constant.CHARSET.UTF_8, null);
    }

    /**
     * 获取指定URL页面或资源
     * @author sjw
     * @date 2016年9月29日 下午5:11:58
     * @param urlString
     *            url地址
     * @param method
     *            访问方法:GET/POST
     * @return
     */
    public static String getURLResponse(String urlString, Map<String, String> params, RequestMethod method,
            String charset) {
        return getURLResponse(urlString, params, method, charset, null);
    }

    /**
     * 获取指定URL页面或资源
     * @author sjw
     * @date 2016年9月29日 下午5:13:06
     * @param urlString
     *            url地址
     * @param method
     *            访问方法:GET/POST
     * @param charset
     *            访问到的资源编码
     * @return
     */
    public static String getURLResponse(String urlString, RequestMethod method, String charset) {
        return getURLResponse(urlString, null, method, charset, null);
    }

    /**
     * 获取指定URL页面或资源
     * @param urlString
     *            URL
     * @param method
     *            "GET"/"POST"
     * @param headers
     *            Map<String,String> header参数
     * @param params
     *            Map<String,String> 传递参数
     * @author sjw
     */
    public static String getURLResponse(String urlString, Map<String, String> params, RequestMethod method,
            String charset, Map<String, String> headers) {

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            if (charset == null)
                charset = SystemUtil.FILE_ENCODING;

            String paramStr = getParams(params);

            if ("GET".equalsIgnoreCase(method.toString())) {
                if (!"".equals(paramStr)) {
                    urlString = urlString + "?" + paramStr;
                }
            }

            connection = getConnection(urlString);
            setHeader(connection, headers);

            if (RequestMethod.POST.toString().equalsIgnoreCase(method.toString())) {
                beforePost(connection, paramStr);
            } else if (RequestMethod.GET.toString().equalsIgnoreCase(method.toString())) {
                beforeGet(connection, paramStr);
            } else {
                return "";
            }

            return readStream(connection.getInputStream(), charset);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static void beforeGet(HttpURLConnection connection, String paramStr) throws Exception {
        connection.setRequestMethod(RequestMethod.GET.toString());
    }

    private static void beforePost(HttpURLConnection connection, String paramStr) {
        DataOutputStream dataOutputStream = null;
        try {
            connection.setRequestMethod(RequestMethod.POST.toString());
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (paramStr != null) {
                dataOutputStream = new DataOutputStream(connection.getOutputStream());
                dataOutputStream.writeBytes(paramStr);
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

    private static String getParams(Map<String, String> params) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        if (null != params) {
            Iterator<Entry<String, String>> mapIterator = params.entrySet().iterator();
            while (mapIterator.hasNext()) {
                Entry<String, String> mapEntry = mapIterator.next();
                if ("".equals(stringBuffer.toString())) {
                    stringBuffer.append(
                            mapEntry.getKey() + "=" + URLEncoder.encode(mapEntry.getValue(), Constant.CHARSET.UTF_8));
                } else {
                    stringBuffer.append("&" + mapEntry.getKey() + "="
                            + URLEncoder.encode(mapEntry.getValue(), Constant.CHARSET.UTF_8));
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 流转化为文本
     * @author jwSun
     * @date 2017年4月20日 下午6:43:17
     * @param inputStream
     * @param charset
     * @return
     */
    private static String readStream(InputStream inputStream, String charset) {
        StringBuffer sb = new StringBuffer();
        try {
            int len = 0;
            byte[] b = new byte[1024];
            sb.setLength(0);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            while ((len = bufferedInputStream.read(b)) != -1) {
                sb.append(new String(b, 0, len, charset));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
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

    public static HttpURLConnection getHttpsConnection(String urlString, URL url) {
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslSocketFactory);
        } catch (Exception e) {

        }
        return connection;
    }
}
