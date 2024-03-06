package com.later.horizon.common.helper;

import org.springframework.context.ApplicationContext;

public class BeanHelper {

    private static ApplicationContext ApplicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return BeanHelper.ApplicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return BeanHelper.ApplicationContext.getBean(name, clazz);
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        BeanHelper.ApplicationContext = applicationContext;
    }

}
