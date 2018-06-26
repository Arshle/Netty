/*
 * FileName: NettyRequestHandler.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty请求处理句柄
 */
package com.jsptpd.netty.handler;

import com.jsptpd.netty.intf.NettyServerMessageHandler;
import com.jsptpd.netty.model.NettyRequest;
import com.jsptpd.netty.scanner.NettyServerHandlerScanner;
import com.jsptpd.netty.writer.NettyResponseWriter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.SocketAddress;

/**
 * 〈Netty请求处理句柄〉<br>
 * 〈处理Netty请求消息〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyRequestHandler extends SimpleChannelInboundHandler<NettyRequest> {

    private Logger logger = LoggerFactory.getLogger(NettyRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyRequest msg){
        logger.info("接收Netty请求:" + msg);
        for(NettyServerMessageHandler handler : NettyServerHandlerScanner.serverHandlers){
            try {
                handler.handleRequest(msg,new NettyResponseWriter(ctx));
            } catch (Exception e) {
                handler.handleException(e);
            }
        }
    }
    /**
     * 通道建立连接回调方法
     * @param ctx 处理链上下午
     * @throws Exception 异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        SocketAddress address = ctx.channel().remoteAddress();
        if(address != null){
            logger.info("Netty建立与客户端连接|客户端地址:" + address);
        }
    }
    /**
     * 通道关闭
     * @param ctx 处理链上下文
     * @throws Exception 异常
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SocketAddress address = ctx.channel().remoteAddress();
        if(address != null){
            logger.info("Netty关闭客户端连接|客户端地址:" + address);
        }
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
        logger.error("Netty处理消息异常:" + cause.getMessage());
        //如果消息在第一次接受就存在异常，转交给开发第一个处理器进行处理
        if(NettyServerHandlerScanner.serverHandlers.size() > 0){
            NettyServerHandlerScanner.serverHandlers.get(0).handleException(cause);
        }
    }
}
