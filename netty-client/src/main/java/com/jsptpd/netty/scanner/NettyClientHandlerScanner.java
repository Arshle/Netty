/*
 * FileName: NettyServerHandlerScanner.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty服务端消息处理拦截器扫描
 */
package com.jsptpd.netty.scanner;

import com.jsptpd.netty.annotation.NettyClientHandler;
import com.jsptpd.netty.code.NettyBaseExceptionType;
import com.jsptpd.netty.exception.NettyBaseException;
import com.jsptpd.netty.intf.NettyClientMessageHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 〈Netty客户端端消息处理拦截器扫描〉<br>
 * 〈用于扫描处理拦截器并统一管理〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("nettyClientHandlerScanner")
public class NettyClientHandlerScanner implements BeanPostProcessor {
    /**
     * 所有响应消息处理类
     */
    public static CopyOnWriteArrayList<NettyClientMessageHandler> clientHandlers = new CopyOnWriteArrayList<>();
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
        NettyClientHandler clientHandler = clazz.getAnnotation(NettyClientHandler.class);
        if(clientHandler != null){
            //获取类所有接口
            Class<?>[] interfaces = clazz.getInterfaces();
            //是否合法的客户端响应处理类
            boolean isValid = false;
            for(Class<?> intf : interfaces){
                //netty响应处理类必须实现NettyServerMessageHandler接口
                if (intf.isAssignableFrom(NettyClientMessageHandler.class)){
                    isValid = true;
                    break;
                }
            }
            if(!isValid){
                throw new NettyBaseException(NettyBaseExceptionType.E0001.getCode(),"nettyClientHandler must implements interface com.jsptpd.netty.intf.NettyClientMessageHandler.");
            }
            //将Netty请求处理类加入列表并排序
            NettyClientMessageHandler messageHandler = (NettyClientMessageHandler)bean;
            clientHandlers.add(messageHandler);
            clientHandlers.sort((o1, o2) -> {
                NettyClientHandler clientHandler1 = o1.getClass().getAnnotation(NettyClientHandler.class);
                NettyClientHandler clientHandler2 = o2.getClass().getAnnotation(NettyClientHandler.class);
                if(clientHandler1 != null && clientHandler2 != null){
                    return Integer.compare(clientHandler1.order(), clientHandler2.order());
                }
                return 0;
            });
        }
        return bean;
    }
}
