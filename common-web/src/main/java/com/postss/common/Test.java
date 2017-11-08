package com.postss.common;

import org.springframework.util.AntPathMatcher;

public class Test {

    public static void main(String[] args) {
        AntPathMatcher ant = new AntPathMatcher();
        System.out.println(ant.match("/a/**", "/a/b"));
        System.out.println(ant.extractPathWithinPattern("/a/**", "/a/b"));
    }
}
