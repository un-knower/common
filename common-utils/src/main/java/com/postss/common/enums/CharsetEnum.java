package com.postss.common.enums;

import java.nio.charset.Charset;

public enum CharsetEnum {

    UTF_8("UTF-8", java.nio.charset.Charset.forName("UTF-8")), ISO_8859_1("ISO-8859-1",
            java.nio.charset.Charset.forName("ISO-8859-1"));

    private String name;
    private java.nio.charset.Charset charset;

    private CharsetEnum(String name, Charset charset) {
        this.name = name;
        this.charset = charset;
    }

    public String getName() {
        return name;
    }

    public java.nio.charset.Charset getCharset() {
        return this.charset;
    }

}