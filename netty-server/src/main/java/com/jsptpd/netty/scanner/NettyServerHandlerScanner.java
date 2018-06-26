/*
 * FileName: NettyServerHandlerScanner.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty服务端消息处理拦截器扫描
 */
package com.jsptpd.netty.scanner;

import com.jsptpd.netty.annotation.NettyServerHandler;
import com.jsptpd.netty.code.NettyBaseExceptionType;
import com.jsptpd.netty.compare.NettyServerHandlerComparator;
import com.jsptpd.netty.exception.NettyBaseException;
import com.jsptpd.netty.intf.NettyServerMessageHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 〈Netty服务端消息处理拦截器扫描〉<br>
 * 〈用于扫描处理拦截器并统一管理〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("nettyServerHandlerScanner")
public class NettyServerHandlerScanner implements BeanPostProcessor {
    /**
     * 用于netty请求处理器的排序
     */
    private Comparator<NettyServerMessageHandler> comparator = new NettyServerHandlerComparator();
    /**
     * 所有请求消息处理类
     */
    public static CopyOnWriteArrayList<NettyServerMessageHandler> serverHandlers = new CopyOnWriteArrayList<>();
    /**
     * spring装载bean后续操作
     * @param bean spring管理的bean
     * @param beanName bean名称
     * @return 处理后的bean
     * @throws BeansException 异常
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //获取bean的class类型
        Class<?> clazz = bean.getClass();
        //找到在class上的处理类注解
        NettyServerHandler serverHandler = clazz.getAnnotation(NettyServerHandler.class);
        if(serverHandler != null){
            //获取类所有接口
            Class<?>[] interfaces = clazz.getInterfaces();
            //是否合法的服务端请求处理类
            boolean isValid = false;
            for(Class<?> intf : interfaces){
                //netty请求处理类必须实现NettyServerMessageHandler接口
                if (intf.isAssignableFrom(NettyServerMessageHandler.class)){
                    isValid = true;
                    break;
                }
            }
            if(!isValid){
                throw new NettyBaseException(NettyBaseExceptionType.E0001.getCode(),"nettyServerHandler must implements interface com.jsptpd.netty.intf.NettyServerMessageHandler.");
            }
            //将Netty请求处理类加入列表并排序
            NettyServerMessageHandler messageHandler = (NettyServerMessageHandler)bean;
            serverHandlers.add(messageHandler);
            serverHandlers.sort(comparator);
        }
        return bean;
    }
}
