package com.postss.common.base.entity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.message.HeaderGroup;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

import com.postss.common.enums.CharsetEnum;
import com.postss.common.log.entity.Logger;
import com.postss.common.log.util.LoggerUtil;
import com.postss.common.util.StringUtil;

public class HttpRequestBuilder {

    private static Logger log = LoggerUtil.getLogger(HttpRequestBuilder.class);

    private String method;
    private Charset charset;
    private ProtocolVersion version;
    private URI uri;
    private HeaderGroup headergroup;
    private HttpEntity entity;
    private List<NameValuePair> parameters;
    private RequestConfig config;

    HttpRequestBuilder(final String method) {
        super();
        this.charset = Consts.UTF_8;
        this.method = method;
    }

    HttpRequestBuilder(final String method, final URI uri) {
        super();
        this.method = method;
        this.charset = Consts.UTF_8;
        this.uri = uri;
    }

    HttpRequestBuilder(final String method, final String uri) {
        super();
        this.method = method;
        this.uri = uri != null ? URI.create(uri) : null;
        this.charset = Consts.UTF_8;
    }

    HttpRequestBuilder() {
        this(null);
    }

    public static HttpRequestBuilder create(final String method) {
        Args.notBlank(method, "HTTP method");
        return new HttpRequestBuilder(method);
    }

    public static HttpRequestBuilder get() {
        return new HttpRequestBuilder(HttpGet.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder get(final URI uri) {
        return new HttpRequestBuilder(HttpGet.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder get(final String uri) {
        return new HttpRequestBuilder(HttpGet.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder head() {
        return new HttpRequestBuilder(HttpHead.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder head(final URI uri) {
        return new HttpRequestBuilder(HttpHead.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder head(final String uri) {
        return new HttpRequestBuilder(HttpHead.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder patch() {
        return new HttpRequestBuilder(HttpPatch.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder patch(final URI uri) {
        return new HttpRequestBuilder(HttpPatch.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder patch(final String uri) {
        return new HttpRequestBuilder(HttpPatch.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder post() {
        return new HttpRequestBuilder(HttpPost.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder post(final URI uri) {
        return new HttpRequestBuilder(HttpPost.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder post(final String uri) {
        return new HttpRequestBuilder(HttpPost.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder put() {
        return new HttpRequestBuilder(HttpPut.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder put(final URI uri) {
        return new HttpRequestBuilder(HttpPut.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder put(final String uri) {
        return new HttpRequestBuilder(HttpPut.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder delete() {
        return new HttpRequestBuilder(HttpDelete.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder delete(final URI uri) {
        return new HttpRequestBuilder(HttpDelete.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder delete(final String uri) {
        return new HttpRequestBuilder(HttpDelete.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder trace() {
        return new HttpRequestBuilder(HttpTrace.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder trace(final URI uri) {
        return new HttpRequestBuilder(HttpTrace.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder trace(final String uri) {
        return new HttpRequestBuilder(HttpTrace.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder options() {
        return new HttpRequestBuilder(HttpOptions.METHOD_NAME);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder options(final URI uri) {
        return new HttpRequestBuilder(HttpOptions.METHOD_NAME, uri);
    }

    /**
     * @since 4.4
     */
    public static HttpRequestBuilder options(final String uri) {
        return new HttpRequestBuilder(HttpOptions.METHOD_NAME, uri);
    }

    public static HttpRequestBuilder copy(final HttpRequest request) {
        Args.notNull(request, "HTTP request");
        return new HttpRequestBuilder().doCopy(request);
    }

    private HttpRequestBuilder doCopy(final HttpRequest request) {
        if (request == null) {
            return this;
        }
        method = request.getRequestLine().getMethod();
        version = request.getRequestLine().getProtocolVersion();

        if (headergroup == null) {
            headergroup = new HeaderGroup();
        }
        headergroup.clear();
        headergroup.setHeaders(request.getAllHeaders());

        parameters = null;
        entity = null;

        if (request instanceof HttpEntityEnclosingRequest) {
            final HttpEntity originalEntity = ((HttpEntityEnclosingRequest) request).getEntity();
            final ContentType contentType = ContentType.get(originalEntity);
            if (contentType != null
                    && contentType.getMimeType().equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType())) {
                try {
                    final List<NameValuePair> formParams = URLEncodedUtils.parse(originalEntity);
                    if (!formParams.isEmpty()) {
                        parameters = formParams;
                    }
                } catch (final IOException ignore) {
                }
            } else {
                entity = originalEntity;
            }
        }

        if (request instanceof HttpUriRequest) {
            uri = ((HttpUriRequest) request).getURI();
        } else {
            uri = URI.create(request.getRequestLine().getUri());
        }

        if (request instanceof Configurable) {
            config = ((Configurable) request).getConfig();
        } else {
            config = null;
        }
        return this;
    }

    /**
     * @since 4.4
     */
    public HttpRequestBuilder setCharset(final CharsetEnum charset) {
        this.charset = charset.getCharset();
        return this;
    }

    /**
     * @since 4.4
     */
    public Charset getCharset() {
        return charset;
    }

    public String getMethod() {
        return method;
    }

    public ProtocolVersion getVersion() {
        return version;
    }

    public HttpRequestBuilder setVersion(final ProtocolVersion version) {
        this.version = version;
        return this;
    }

    public URI getUri() {
        return uri;
    }

    public HttpRequestBuilder setUri(final URI uri) {
        this.uri = uri;
        return this;
    }

    public HttpRequestBuilder setUri(final String uri) {
        this.uri = uri != null ? URI.create(uri) : null;
        return this;
    }

    public Header getFirstHeader(final String name) {
        return headergroup != null ? headergroup.getFirstHeader(name) : null;
    }

    public Header getLastHeader(final String name) {
        return headergroup != null ? headergroup.getLastHeader(name) : null;
    }

    public Header[] getHeaders(final String name) {
        return headergroup != null ? headergroup.getHeaders(name) : null;
    }

    public HttpRequestBuilder addHeader(final Header header) {
        if (headergroup == null) {
            headergroup = new HeaderGroup();
        }
        headergroup.addHeader(header);
        return this;
    }

    public HttpRequestBuilder addHeader(final String name, final String value) {
        if (headergroup == null) {
            headergroup = new HeaderGroup();
        }
        this.headergroup.addHeader(new BasicHeader(name, value));
        return this;
    }

    public HttpRequestBuilder removeHeader(final Header header) {
        if (headergroup == null) {
            headergroup = new HeaderGroup();
        }
        headergroup.removeHeader(header);
        return this;
    }

    public HttpRequestBuilder removeHeaders(final String name) {
        if (name == null || headergroup == null) {
            return this;
        }
        for (final HeaderIterator i = headergroup.iterator(); i.hasNext();) {
            final Header header = i.nextHeader();
            if (name.equalsIgnoreCase(header.getName())) {
                i.remove();
            }
        }
        return this;
    }

    public HttpRequestBuilder setHeader(final Header header) {
        if (headergroup == null) {
            headergroup = new HeaderGroup();
        }
        this.headergroup.updateHeader(header);
        return this;
    }

    public HttpRequestBuilder setHeader(final String name, final String value) {
        if (headergroup == null) {
            headergroup = new HeaderGroup();
        }
        this.headergroup.updateHeader(new BasicHeader(name, value));
        return this;
    }

    public HttpEntity getEntity() {
        return entity;
    }

    public HttpRequestBuilder setEntity(final HttpEntity entity) {
        this.entity = entity;
        return this;
    }

    public List<NameValuePair> getParameters() {
        return parameters != null ? new ArrayList<NameValuePair>(parameters) : new ArrayList<NameValuePair>();
    }

    public HttpRequestBuilder addParameter(final NameValuePair nvp) {
        Args.notNull(nvp, "Name value pair");
        if (parameters == null) {
            parameters = new LinkedList<NameValuePair>();
        }
        parameters.add(nvp);
        return this;
    }

    public HttpRequestBuilder addParameter(final String name, final String value) {
        return addParameter(name, value, Boolean.TRUE);
    }

    public HttpRequestBuilder addParameter(final String name, final String value, Boolean canEmpty) {
        if ((!canEmpty) && (!StringUtil.notEmpty(value))) {
            if (log.isDebugEnabled()) {
                log.debug("{} cant empty and value is empty. ignore", name);
                return this;
            }
        }
        return addParameter(new BasicNameValuePair(name, value));
    }

    public HttpRequestBuilder addParameters(final NameValuePair... nvps) {
        for (final NameValuePair nvp : nvps) {
            addParameter(nvp);
        }
        return this;
    }

    public RequestConfig getConfig() {
        return config;
    }

    public HttpRequestBuilder setConfig(final RequestConfig config) {
        this.config = config;
        return this;
    }

    public HttpRequestBase build() {
        final HttpRequestBase result;
        URI uriNotNull = this.uri != null ? this.uri : URI.create("/");
        HttpEntity entityCopy = this.entity;
        if (parameters != null && !parameters.isEmpty()) {
            if (entityCopy == null && (HttpPost.METHOD_NAME.equalsIgnoreCase(method)
                    || HttpPut.METHOD_NAME.equalsIgnoreCase(method))) {
                entityCopy = new UrlEncodedFormEntity(parameters, charset != null ? charset : HTTP.DEF_CONTENT_CHARSET);
            } else {
                try {
                    uriNotNull = new URIBuilder(uriNotNull).setCharset(this.charset).addParameters(parameters).build();
                } catch (final URISyntaxException ex) {
                    // should never happen
                }
            }
        }
        if (entityCopy == null) {
            result = new InternalRequest(method);
        } else {
            final InternalEntityEclosingRequest request = new InternalEntityEclosingRequest(method);
            request.setEntity(entityCopy);
            result = request;
        }
        result.setProtocolVersion(this.version);
        result.setURI(uriNotNull);
        if (this.headergroup != null) {
            result.setHeaders(this.headergroup.getAllHeaders());
        }
        result.setConfig(this.config);
        return result;
    }

    static class InternalRequest extends HttpRequestBase {

        private final String method;

        InternalRequest(final String method) {
            super();
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }

    }

    static class InternalEntityEclosingRequest extends HttpEntityEnclosingRequestBase {

        private final String method;

        InternalEntityEclosingRequest(final String method) {
            super();
            this.method = method;
        }

        @Override
        public String getMethod() {
            return this.method;
        }

    }
}
