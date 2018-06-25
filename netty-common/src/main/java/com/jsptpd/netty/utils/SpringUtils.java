/*
 * FileName: SpringUtils.java
 * Author:   Arshle
 * Date:     2018年06月13日
 * Description: Spring容器类
 */
package com.jsptpd.netty.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 〈Spring容器类〉<br>
 * 〈用于从容器中获取bean等操作〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("springUtils")
public class SpringUtils implements ApplicationContextAware {
    /**
     * spring上下文环境
     */
    private static ApplicationContext context;
    /**
     * 注入spring容器
     * @param applicationContext spring上下文环境
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(context == null){
            context = applicationContext;
        }
    }
    /**
     * 从spring上下文中获取bean
     * @param beanName bean的名称
     * @return bean对象
     */
    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
    /**
     * 从spring上下文中获取bean
     * @param beanType bean的类型
     * @param <T> 返回类型
     * @return bean对象
     */
    public static <T> T getBean(Class<T> beanType){
        return context.getBean(beanType);
    }
}
