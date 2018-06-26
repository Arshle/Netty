/*
 * FileName: NettyMessageHandler.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty消息处理统一接口
 */
package com.jsptpd.netty.intf;

/**
 * 〈Netty消息处理统一接口〉<br>
 * 〈用于处理Netty消息〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public interface NettyMessageHandler {
    /**
     * 处理异常信息
     * @param cause 异常
     */
    void handleException(Throwable cause);
}
