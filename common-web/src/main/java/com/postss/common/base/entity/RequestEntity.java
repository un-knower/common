package com.postss.common.base.entity;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import com.postss.common.constant.Constant;
import com.postss.common.enums.CharsetEnum;
import com.postss.common.util.StringUtil;

public class RequestEntity<T> extends HttpEntity<T> {

    private final HttpMethod method;

    private final URI url;

    private final Type type;

    private Charset charsetResolve;

    /**
     * Constructor with method and URL but without body nor headers.
     * @param method the method
     * @param url the URL
     */
    public RequestEntity(HttpMethod method, URI url) {
        this(null, null, method, url);
    }

    /**
     * Constructor with method, URL and body but without headers.
     * @param body the body
     * @param method the method
     * @param url the URL
     */
    public RequestEntity(T body, HttpMethod method, URI url) {
        this(body, null, method, url, null);
    }

    /**
     * Constructor with method, URL, body and type but without headers.
     * @param body the body
     * @param method the method
     * @param url the URL
     * @param type the type used for generic type resolution
     * @since 4.3
     */
    public RequestEntity(T body, HttpMethod method, URI url, Type type) {
        this(body, null, method, url, type);
    }

    /**
     * Constructor with method, URL and headers but without body.
     * @param headers the headers
     * @param method the method
     * @param url the URL
     */
    public RequestEntity(MultiValueMap<String, String> headers, HttpMethod method, URI url) {
        this(null, headers, method, url, null);
    }

    /**
     * Constructor with method, URL, headers and body.
     * @param body the body
     * @param headers the headers
     * @param method the method
     * @param url the URL
     */
    public RequestEntity(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url) {
        this(body, headers, method, url, null);
    }

    /**
     * Constructor with method, URL, headers, body and type.
     * @param body the body
     * @param headers the headers
     * @param method the method
     * @param url the URL
     * @param type the type used for generic type resolution
     * @since 4.3
     */
    public RequestEntity(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url, Type type) {
        this(body, headers, method, url, null, CharsetEnum.UTF_8.getCharset());
    }

    public RequestEntity(T body, MultiValueMap<String, String> headers, HttpMethod method, URI url, Type type,
            Charset charsetResolve) {
        super(body, headers);
        this.method = method;
        this.url = url;
        this.type = type;
        this.charsetResolve = charsetResolve;
    }

    public String getCharset() {
        return charsetResolve.toString().replaceAll("_", "-");
    }

    /**
     * Return the HTTP method of the request.
     * @return the HTTP method as an {@code HttpMethod} enum value
     */
    public HttpMethod getMethod() {
        return this.method;
    }

    /**
     * Return the URL of the request.
     * @return the URL as a {@code URI}
     */
    public URI getUrl() {
        return this.url;
    }

    /**
     * Return the type of the request's body.
     * @return the request's body type, or {@code null} if not known
     * @since 4.3
     */
    public Type getType() {
        if (this.type == null) {
            T body = getBody();
            if (body != null) {
                return body.getClass();
            }
        }
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!super.equals(other)) {
            return false;
        }
        RequestEntity<?> otherEntity = (RequestEntity<?>) other;
        return (ObjectUtils.nullSafeEquals(getMethod(), otherEntity.getMethod())
                && ObjectUtils.nullSafeEquals(getUrl(), otherEntity.getUrl()));
    }

    @Override
    public int hashCode() {
        int hashCode = super.hashCode();
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.method);
        hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.url);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("<");
        builder.append(getMethod());
        builder.append(' ');
        builder.append(getUrl());
        builder.append(',');
        T body = getBody();
        HttpHeaders headers = getHeaders();
        if (body != null) {
            builder.append(body);
            if (headers != null) {
                builder.append(',');
            }
        }
        if (headers != null) {
            builder.append(headers);
        }
        builder.append('>');
        return builder.toString();
    }

    // Static builder methods

    /**
     * Create a builder with the given method and url.
     * @param method the HTTP method (GET, POST, etc)
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder method(HttpMethod method, String url) {
        return new DefaultBodyBuilder(method, url);
    }

    /**
     * Create an HTTP GET builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> get(String url) {
        return method(HttpMethod.GET, url);
    }

    public static HeadersBuilder<?> get(String url, Map<String, ?> params) {
        try {
            String paramStr = getParams(params, HttpMethod.GET);
            paramStr = StringUtil.notEmpty(paramStr) ? "?" + paramStr : "";
            return method(HttpMethod.GET, url + paramStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> String getParams(Map<String, T> params, HttpMethod method) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        if (null != params) {
            Iterator<Entry<String, T>> mapIterator = params.entrySet().iterator();
            while (mapIterator.hasNext()) {
                Entry<String, ?> mapEntry = mapIterator.next();

                String val = method == HttpMethod.POST ? mapEntry.getValue().toString()
                        : URLEncoder.encode(mapEntry.getValue().toString(), Constant.CHARSET.UTF_8);
                if ("".equals(stringBuffer.toString())) {
                    stringBuffer.append(mapEntry.getKey() + "=" + val);
                } else {
                    stringBuffer.append("&" + mapEntry.getKey() + "=" + val);
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Create an HTTP HEAD builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> head(String url) {
        return method(HttpMethod.HEAD, url);
    }

    /**
     * Create an HTTP POST builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder post(String url) {
        return method(HttpMethod.POST, url);
    }

    /**
     * Create an HTTP PUT builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder put(String url) {
        return method(HttpMethod.PUT, url);
    }

    /**
     * Create an HTTP PATCH builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static BodyBuilder patch(String url) {
        return method(HttpMethod.PATCH, url);
    }

    /**
     * Create an HTTP DELETE builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> delete(String url) {
        return method(HttpMethod.DELETE, url);
    }

    /**
     * Creates an HTTP OPTIONS builder with the given url.
     * @param url the URL
     * @return the created builder
     */
    public static HeadersBuilder<?> options(String url) {
        return method(HttpMethod.OPTIONS, url);
    }

    /**
     * Defines a builder that adds headers to the request entity.
     * @param <B> the builder subclass
     */
    public interface HeadersBuilder<B extends HeadersBuilder<B>> {

        /**
         * Add the given, single header value under the given name.
         * @param headerName  the header name
         * @param headerValues the header value(s)
         * @return this builder
         * @see HttpHeaders#add(String, String)
         */
        B header(String headerName, String... headerValues);

        /**
         * Set the list of acceptable {@linkplain MediaType media types}, as
         * specified by the {@code Accept} header.
         * @param acceptableMediaTypes the acceptable media types
         */
        B accept(MediaType... acceptableMediaTypes);

        /**
         * Set the list of acceptable {@linkplain Charset charsets}, as specified
         * by the {@code Accept-Charset} header.
         * @param acceptableCharsets the acceptable charsets
         */
        B acceptCharset(Charset... acceptableCharsets);

        /**
         * Set the value of the {@code If-Modified-Since} header.
         * <p>The date should be specified as the number of milliseconds since
         * January 1, 1970 GMT.
         * @param ifModifiedSince the new value of the header
         */
        B ifModifiedSince(long ifModifiedSince);

        /**
         * Set the values of the {@code If-None-Match} header.
         * @param ifNoneMatches the new value of the header
         */
        B ifNoneMatch(String... ifNoneMatches);

        B charset(Charset charset);

        /**
         * Builds the request entity with no body.
         * @return the request entity
         * @see BodyBuilder#body(Object)
         */
        RequestEntity<String> build();
    }

    /**
     * Defines a builder that adds a body to the response entity.
     */
    public interface BodyBuilder extends HeadersBuilder<BodyBuilder> {

        /**
         * Set the length of the body in bytes, as specified by the
         * {@code Content-Length} header.
         * @param contentLength the content length
         * @return this builder
         * @see HttpHeaders#setContentLength(long)
         */
        BodyBuilder contentLength(long contentLength);

        /**
         * Set the {@linkplain MediaType media type} of the body, as specified
         * by the {@code Content-Type} header.
         * @param contentType the content type
         * @return this builder
         * @see HttpHeaders#setContentType(MediaType)
         */
        BodyBuilder contentType(MediaType contentType);

        /**
         * Set the body of the request entity and build the RequestEntity.
         * @param <T> the type of the body
         * @param body the body of the request entity
         * @return the built request entity
         */
        <T> RequestEntity<T> body(T body);

        /**
         * Set the body and type of the request entity and build the RequestEntity.
         * @param <T> the type of the body
         * @param body the body of the request entity
         * @param type the type of the body, useful for generic type resolution
         * @return the built request entity
         * @since 4.3
         */
        <T> RequestEntity<T> body(T body, Type type);
    }

    private static class DefaultBodyBuilder implements BodyBuilder {

        private final HttpMethod method;

        private final String url;

        private Charset charsetResolve;

        private final HttpHeaders headers = new HttpHeaders();

        public DefaultBodyBuilder(HttpMethod method, String url) {
            this.method = method;
            this.url = url;
            this.charsetResolve = CharsetEnum.UTF_8.getCharset();
            this.headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        }

        @Override
        public BodyBuilder header(String headerName, String... headerValues) {
            for (String headerValue : headerValues) {
                this.headers.add(headerName, headerValue);
            }
            return this;
        }

        @Override
        public BodyBuilder accept(MediaType... acceptableMediaTypes) {
            this.headers.setAccept(Arrays.asList(acceptableMediaTypes));
            return this;
        }

        @Override
        public BodyBuilder acceptCharset(Charset... acceptableCharsets) {
            this.headers.setAcceptCharset(Arrays.asList(acceptableCharsets));
            return this;
        }

        @Override
        public BodyBuilder contentLength(long contentLength) {
            this.headers.setContentLength(contentLength);
            return this;
        }

        @Override
        public BodyBuilder contentType(MediaType contentType) {
            this.headers.setContentType(contentType);
            return this;
        }

        @Override
        public BodyBuilder ifModifiedSince(long ifModifiedSince) {
            this.headers.setIfModifiedSince(ifModifiedSince);
            return this;
        }

        @Override
        public BodyBuilder ifNoneMatch(String... ifNoneMatches) {
            this.headers.setIfNoneMatch(Arrays.asList(ifNoneMatches));
            return this;
        }

        @Override
        public RequestEntity<String> build() {
            try {
                return new RequestEntity<String>(null, this.headers, this.method, new URI(this.url), null,
                        this.charsetResolve);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public <T> RequestEntity<T> body(T body) {
            try {
                return new RequestEntity<T>(body, this.headers, this.method, new URI(this.url), null,
                        this.charsetResolve);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public <T> RequestEntity<T> body(T body, Type type) {
            try {
                return new RequestEntity<T>(body, this.headers, this.method, new URI(this.url), type,
                        this.charsetResolve);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public BodyBuilder charset(Charset charset) {
            this.charsetResolve = charset;
            return null;
        }
    }

}
