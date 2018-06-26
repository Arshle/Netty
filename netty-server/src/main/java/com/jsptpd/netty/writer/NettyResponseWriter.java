/*
 * FileName: NettyResponseWriter.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty响应写操作
 */
package com.jsptpd.netty.writer;

import com.jsptpd.netty.configuration.NettyServerConfiguration;
import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.model.NettyResponse;
import com.jsptpd.netty.utils.SpringUtils;
import com.jsptpd.netty.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈Netty响应写操作〉<br>
 * 〈用于回复客户端响应〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyResponseWriter {

    private Logger logger = LoggerFactory.getLogger(NettyResponseWriter.class);
    /**
     * 处理链上下文
     */
    private ChannelHandlerContext context;
    /**
     * 服务端配置
     */
    private NettyServerConfiguration serverConfig = SpringUtils.getBean(NettyServerConfiguration.class);

    public NettyResponseWriter(ChannelHandlerContext context){
        this.context = context;
    }
    /**
     * 向客户端通道写字符串数据
     * @param data 数据
     */
    public void writeString(String data){
        try {
            if(StringUtils.isEmpty(data)){
                return;
            }
            writeBytes(data.getBytes(NettyConstants.CHARSET_UTF8));
        } catch (Exception e) {
            logger.error("发送响应字符串异常",e);
        }
    }
    /**
     * 向客户端通道写字节数组
     * @param data 数据
     */
    public void writeBytes(byte[] data){
        try {
            if(data == null){
                return;
            }
            //netty.server.encode.response如果设置为true则对response编码
            //对应客户端必须设置netty.client.decode.response为true
            //否则会导致协议不一致
            if(serverConfig.isEncodeResponse()){
                writeNettyResponse(NettyResponse.valueOf(data));
            }else{
                ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer(data.length);
                buffer.writeBytes(data);
                context.writeAndFlush(buffer);
            }
        } catch (Exception e) {
            logger.error("发送字节数组响应异常",e);
        }
    }
    /**
     * 向客户端通道写响应数据
     * @param response 响应数据
     */
    public void writeNettyResponse(NettyResponse response){
        if(response == null || response.getData() == null){
            return;
        }
        if(!serverConfig.isEncodeResponse()){
            logger.warn("netty.server.encode.response is false,please change the config to true.");
            return;
        }
        context.writeAndFlush(response);
    }
}
