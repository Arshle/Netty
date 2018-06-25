/*
 * FileName: NettyRequestHandler.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty请求处理句柄
 */
package com.jsptpd.netty.handler;

import com.jsptpd.netty.model.NettyRequest;
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
        logger.info("处理链接受Netty请求:" + msg);

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
            logger.info("Netty连接建立|remoteAddress:" + address);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("Netty处理消息异常",cause);
    }
}
