package com.postss.common.base.enums;

import java.nio.charset.Charset;

public enum CharsetEnum {

    UTF_8(java.nio.charset.Charset.forName("UTF-8")), ISO_8859_1(java.nio.charset.Charset.forName("ISO-8859-1"));

    private java.nio.charset.Charset charset;

    private CharsetEnum(Charset charset) {
        this.charset = charset;
    }

    public java.nio.charset.Charset getCharset() {
        return this.charset;
    }

}
