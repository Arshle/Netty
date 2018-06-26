/*
 * FileName: NettyResponseHandler.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty响应处理
 */
package com.jsptpd.netty.handler;

import com.jsptpd.netty.model.NettyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈Netty响应处理〉<br>
 * 〈用于处理Netty响应〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyResponseHandler extends SimpleChannelInboundHandler<NettyResponse> {

    private Logger logger = LoggerFactory.getLogger(NettyResponseHandler.class);
    /**
     * 接受响应消息
     * @param ctx 处理链上下文
     * @param msg 响应
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyResponse msg){
        logger.info("接收Netty服务端响应:" + msg);
    }
    /**
     * 异常捕获
     * @param ctx 处理链上下文
     * @param cause 异常
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("Netty处理消息异常",cause);
    }
}
