/*
 * FileName: NettyServerMessageHandler.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty请求消息统一处理接口
 */
package com.jsptpd.netty.intf;

import com.jsptpd.netty.model.NettyRequest;
import com.jsptpd.netty.writer.NettyResponseWriter;

/**
 * 〈Netty请求消息统一处理接口〉<br>
 * 〈用于提供开发统一处理消息〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public interface NettyServerMessageHandler extends NettyMessageHandler {
    /**
     * 处理Netty请求
     * @param request netty请求
     * @param writer 响应写操作
     */
    void handleRequest(NettyRequest request, NettyResponseWriter writer);
}
