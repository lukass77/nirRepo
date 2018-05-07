package com.nir.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * User: NirB
 * Date: 3/30/13
 * Time: 2:17 AM
 */
public class MyAopAppMain {

    public static void main(String [] args){

        ApplicationContext context = new ClassPathXmlApplicationContext("com/nir/aop/beans.xml");
        MyService myServiceBean = context.getBean("myServiceBean", MyService.class);

        List<String> strings = myServiceBean.myLogic();


    }
}
