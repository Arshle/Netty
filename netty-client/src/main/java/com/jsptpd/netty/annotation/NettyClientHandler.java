/*
 * FileName: NettyClientHandler.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty客户端处理类
 */
package com.jsptpd.netty.annotation;

import org.springframework.stereotype.Component;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 〈Netty客户端处理类〉<br>
 * 〈用于处理服务端的响应信息〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface NettyClientHandler {
    /**
     * 拦截器顺序,默认都为0
     */
    int order() default 0;
}
