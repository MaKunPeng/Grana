package com.grana.user.infrastructure.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类
 * 
 * IOC容器接口有BeanFactory和ApplicationContext。通常建议使用后者，因为它包含了前者的功能。
 * Spring的核心是ApplicationContext.它负责管理 beans 的完整生命周期。我们可以从applicationContext里通过bean名称获取安装的bean。
 * 进行某种操作。不能直接使用ApplicationContext，而需要借助ApplicationContextAware。
 * 
 * @author Aaron Ma
 * @Date 2021-3-2 11:17:25
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {
    @SuppressWarnings("unused")
    private static BeanFactory beanFactory;
    /** Spring应用上下文 */
    private static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
        
    }
    
    /**
     * 根据名称获取Bean
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
    
    /**
     * 根据类型获取Bean
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
