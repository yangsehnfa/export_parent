package com.itheima.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CompanyTest {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml").start();
    }
}
