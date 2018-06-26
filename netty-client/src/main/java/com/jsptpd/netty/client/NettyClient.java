/*
 * FileName: NettyClient.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty客户端
 */
package com.jsptpd.netty.client;

import com.jsptpd.netty.configuration.NettyClientConfiguration;
import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.decoder.NettyResponseDecoder;
import com.jsptpd.netty.decoder.SimpleResponseDecoder;
import com.jsptpd.netty.encoder.NettyRequestEncoder;
import com.jsptpd.netty.handler.NettyResponseHandler;
import com.jsptpd.netty.model.NettyRequest;
import com.jsptpd.netty.utils.SpringUtils;
import com.jsptpd.netty.utils.StringUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetSocketAddress;

/**
 * 〈Netty客户端〉<br>
 * 〈封装连接服务端操作，发送数据等〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyClient {

    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
    /**
     * 服务类
     */
    private static Bootstrap bootstrap = new Bootstrap();
    /**
     * worker线程池
     */
    private static EventLoopGroup worker = new NioEventLoopGroup();
    /**
     * 客户端配置
     */
    private static NettyClientConfiguration clientConfig = SpringUtils.getBean(NettyClientConfiguration.class);
    /**
     * 连接通道
     */
    private static Channel channel;

    static{
        //worker线程池
        bootstrap.group(worker);
        //设置通道工厂
        bootstrap.channel(NioSocketChannel.class);
        //初始化通道
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch){
                //请求编码器
                if(clientConfig.isEncodeRequest()){
                    ch.pipeline().addLast("requestEncoder",new NettyRequestEncoder());
                }
                //响应解码器
                if(clientConfig.isDecodeResponse()){
                    ch.pipeline().addLast("responseDecoder",new NettyResponseDecoder());
                }else{
                    ch.pipeline().addLast("responseDecoder",new SimpleResponseDecoder());
                }
                //响应处理拦截器
                ch.pipeline().addLast("responseHandler", new NettyResponseHandler());
            }
        });
        //发送队列长度
        bootstrap.option(ChannelOption.SO_BACKLOG,clientConfig.getBackLog());
        //立即发送TCP包
        bootstrap.option(ChannelOption.TCP_NODELAY,clientConfig.isTcpNoDelay());
        //检测与客户端的心跳
        bootstrap.option(ChannelOption.SO_KEEPALIVE, clientConfig.isKeepAlive());
        //连接超时时间
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeout());
        //循环写操作最大数量
        bootstrap.option(ChannelOption.WRITE_SPIN_COUNT, clientConfig.getWriteSpinCount());
        //自动读取
        bootstrap.option(ChannelOption.AUTO_READ, clientConfig.isAutoRead());
        //单线程处理拦截器
        bootstrap.option(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP, clientConfig.isSingleEventExecutorPerGroup());
        //占用同一端口号
        bootstrap.option(ChannelOption.SO_REUSEADDR,clientConfig.isReuseAddr());
        //关闭socket延时时间
        bootstrap.option(ChannelOption.SO_LINGER, clientConfig.getLinger());
        //允许服务端在客户端关闭的情况下保持连接
        bootstrap.option(ChannelOption.ALLOW_HALF_CLOSURE, clientConfig.isAllowHalfClosure());
    }
    /**
     * 连接到Netty服务端
     */
    public static void connect(){
        try {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress(clientConfig.getServerAddress(), clientConfig.getServerPort())).sync();
            future.addListener(new ChannelFutureListener() {
                /**
                 * 建立连接完成
                 * @param future future对象
                 */
                @Override
                public void operationComplete(ChannelFuture future){
                    logger.info("Netty连接建立,server:" + clientConfig.getServerAddress() + ":" + clientConfig.getServerPort());
                    channel = future.channel();
                }
            });
        } catch (Exception e) {
            logger.error("Netty建立连接异常",e);
        }
    }
    /**
     * 关闭Netty客户端连接
     */
    public static void shutdown(){
        worker.shutdownGracefully();
    }
    /**
     * 获取连接通道
     * @return Netty连接通道
     */
    public static Channel getChannel(){
        return channel;
    }
    /**
     * 发送请求数据
     * @param data 请求数据
     */
    public static void sendString(String data){
        try {
            if(StringUtils.isEmpty(data)){
                return;
            }
            sendBytes(data.getBytes(NettyConstants.CHARSET_UTF8));
        } catch (Exception e) {
            logger.error("发送字符串请求异常",e);
        }
    }
    /**
     * 发送请求数据
     * @param data 请求数据
     */
    public static void sendBytes(byte[] data){
        try {
            if(data == null){
                return;
            }
            //编解码客户端和服务端协议要保持一致
            //若客户端将配置netty.client.encode.request设置为true
            //服务端解码时要将netty.server.decode.request也设置为true
            //否则会导致客户端和服务端编解码不一致问题
            if(clientConfig.isEncodeRequest()){
                sendNettyRequest(NettyRequest.valueOf(data));
            }else{
                ByteBuf buffer = Unpooled.buffer(data.length);
                buffer.writeBytes(data);
                channel.writeAndFlush(data);
            }
        } catch (Exception e) {
            logger.error("发送字节数组异常",e);
        }
    }
    /**
     * 发送Netty请求
     * @param request 请求数据
     */
    public static void sendNettyRequest(NettyRequest request){
        if(request == null || request.getData() == null){
            return;
        }
        //客户端设置不编码nettyRequest,仅发送字节数组数据,无法对编码统一协议
        //因此不允许发送NettyRequest编码消息
        if(!clientConfig.isEncodeRequest()){
            logger.warn("netty.client.encode.request is false,please change the config to true.");
            return;
        }
        channel.writeAndFlush(request);
    }
}
