/*
 * FileName: NettyServer.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty服务端启动类
 */
package com.jsptpd.netty.server;

import com.jsptpd.netty.configuration.NettyServerConfiguration;
import com.jsptpd.netty.decoder.NettyRequestDecoder;
import com.jsptpd.netty.decoder.SimpleRequestDecoder;
import com.jsptpd.netty.encoder.NettyResponseEncoder;
import com.jsptpd.netty.handler.NettyRequestHandler;
import com.jsptpd.netty.utils.SpringUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;

/**
 * 〈Netty服务端启动类〉<br>
 * 〈用于接受请求并调用处理链〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyServer {

    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);
    /**
     * 启动Netty服务端
     */
    public static void start(){
        //服务端类
        ServerBootstrap bootstrap = new ServerBootstrap();
        //boss和worker线程池
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            //设置Netty服务端配置
            NettyServerConfiguration serverConfig = SpringUtils.getBean(NettyServerConfiguration.class);
            //设置循环线程组事件
            bootstrap.group(boss, worker);
            //设置通道工厂
            bootstrap.channel(NioServerSocketChannel.class);
            //设置通道pipeline拦截器
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                /**
                 * 初始化通道
                 * @param ch 通道
                 */
                @Override
                protected void initChannel(SocketChannel ch){
                    //设置通道拦截器
                    //根据配置设置请求解码器类型
                    if(serverConfig.isDecodeRequest()){
                        ch.pipeline().addLast("requestDecoder", new NettyRequestDecoder());
                    }else{
                        ch.pipeline().addLast("requestDecoder",new SimpleRequestDecoder());
                    }
                    //响应编码器
                    if(serverConfig.isEncodeResponse()){
                        ch.pipeline().addLast("responseEncoder",new NettyResponseEncoder());
                    }
                    //请求处理类
                    ch.pipeline().addLast("requestHandler",new NettyRequestHandler());
                }
            });
            //立即发送TCP包
            bootstrap.childOption(ChannelOption.TCP_NODELAY,serverConfig.isTcpNoDelay());
            //检测与客户端的心跳
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, serverConfig.isKeepAlive());
            //连接超时时间
            bootstrap.childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, serverConfig.getConnectTimeout());
            //循环写操作最大数量
            bootstrap.childOption(ChannelOption.WRITE_SPIN_COUNT, serverConfig.getWriteSpinCount());
            //自动读取
            bootstrap.childOption(ChannelOption.AUTO_READ, serverConfig.isAutoRead());
            //单线程处理拦截器
            bootstrap.childOption(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP, serverConfig.isSingleEventExecutorPerGroup());
            //占用同一端口号
            bootstrap.childOption(ChannelOption.SO_REUSEADDR,serverConfig.isReuseAddr());
            //关闭socket延时时间
            bootstrap.childOption(ChannelOption.SO_LINGER, serverConfig.getLinger());
            //允许服务端在客户端关闭的情况下保持连接
            bootstrap.childOption(ChannelOption.ALLOW_HALF_CLOSURE, serverConfig.isAllowHalfClosure());
            //绑定端口号
            final int port = serverConfig.getPort();
            ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();
            future.addListener(new ChannelFutureListener() {
                /**
                 * 通道绑定完成触发事件
                 * @param future future类
                 */
                @Override
                public void operationComplete(ChannelFuture future){
                    //是否初始化异常
                    Throwable cause = future.cause();
                    if(cause != null){
                        logger.error("Netty初始化异常",cause);
                    }
                    //绑定端口成功
                    if(future.isSuccess()){
                        logger.info("Netty Server start in port " + port + "...");
                    }
                }
            });
            //关闭future释放资源
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("初始化Netty服务端异常",e);
        } finally {
            //优雅关闭线程池
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
