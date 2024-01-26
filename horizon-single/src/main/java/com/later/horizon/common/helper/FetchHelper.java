package com.later.horizon.common.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class FetchHelper implements ApplicationContextAware {

    private static ApplicationContext ApplicationContext;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) ApplicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<?> clazz) throws BeansException {
        return (T) ApplicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return ApplicationContext.getBean(name, clazz);
    }

    private static ApplicationContext getApplicationContext() {
        return FetchHelper.ApplicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        FetchHelper.ApplicationContext = applicationContext;
    }

}
