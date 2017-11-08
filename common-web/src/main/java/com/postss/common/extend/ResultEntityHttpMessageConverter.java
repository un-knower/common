package com.postss.common.extend;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import com.postss.common.base.entity.ResultEntity;
import com.postss.common.enums.CharsetEnum;
import com.postss.common.util.ResponseUtil;

/**
 * ResultEntity返回转换器
 * @className ResultEntityHttpMessageConverter
 * @author jwSun
 * @date 2017年7月4日 上午11:29:05
 * @version 1.0.0
 */
public class ResultEntityHttpMessageConverter extends AbstractHttpMessageConverter<ResultEntity> {

    /**默认编码-UTF-8**/
    public static final Charset DEFAULT_CHARSET = CharsetEnum.UTF_8.getCharset();

    /**使用编码**/
    private final Charset defaultCharset;

    public ResultEntityHttpMessageConverter() {
        this(DEFAULT_CHARSET);
    }

    public ResultEntityHttpMessageConverter(Charset defaultCharset) {
        super();
        this.defaultCharset = defaultCharset;
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return ResultEntity.class == clazz;
    }

    @Override
    protected ResultEntity readInternal(Class<? extends ResultEntity> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override
    protected void writeInternal(ResultEntity t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        String result = null;
        if (t != null) {
            result = ResponseUtil.codeResolveJson(t);
        }
        StreamUtils.copy(result, charset, outputMessage.getBody());
    }

    @SuppressWarnings("deprecation")
    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        } else {
            return this.defaultCharset;
        }
    }

}