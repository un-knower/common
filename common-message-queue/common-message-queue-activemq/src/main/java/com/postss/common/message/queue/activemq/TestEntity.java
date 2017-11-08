package com.postss.common.message.queue.activemq;

public class TestEntity {

	public void testVoid(String params, Integer lalala) {
		System.out.println("testVoid(String params, Integer lalala) {\t:\t" + "Integer:" + lalala + "String:" + params);
	}

	public void testVoid(String params) {
		System.out.println("testVoid(String params)\t:\t" + params);
	}

	public void testVoid(Integer[] params, String[] strs) {
		System.out.println("Integer[]");
		for (Integer param : params) {
			System.out.println(param);
		}
		System.out.println("String[]");
		for (String str : strs) {
			System.out.println(str);
		}
	}

	public void testVoid(Integer params, String[] strs) {
		System.out.println("Integer");

		System.out.println(params);

		System.out.println("String[]");
		for (String str : strs) {
			System.out.println(str);
		}
	}

	public void testVoid() {
		System.out.println("testVoid()\t:\t");
	}

}
