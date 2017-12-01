package com.postss.common.constant;

public class T1 {

    private String a;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public T1() {
        super();
        System.out.println(this.getClass().getSimpleName() + "Repository");
    }

}
