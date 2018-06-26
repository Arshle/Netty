/*
 * FileName: NettyRequestEncoder.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty请求入站编码器
 */
package com.jsptpd.netty.encoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.model.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈Netty请求入站编码器〉<br>
 * 〈将Netty请求编码成二进制字节数组〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyRequestEncoder extends MessageToByteEncoder<NettyRequest> {

    private Logger logger = LoggerFactory.getLogger(NettyRequestEncoder.class);
    /**
     * 编码
     * @param ctx 处理链上下文
     * @param msg Netty请求
     * @param out 二进制字节数组
     */
    @SuppressWarnings("Duplicates")
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyRequest msg, ByteBuf out) {
        ObjectMapper mapper = new ObjectMapper();
        int headerLength = 0;
        byte[] headerBytes = null;
        try {
            if(msg != null){
                //先写包头
                out.writeInt(NettyConstants.HEAD_FLAG);
                //响应头数据长度及内容
                if(msg.getHeaders() != null
                        && msg.getHeaders().size() > 0){
                    headerBytes = mapper.writeValueAsString(
                            msg.getHeaders()).getBytes(
                            NettyConstants.CHARSET_UTF8);
                    headerLength = headerBytes.length;
                }
                out.writeInt(headerLength);
                if(headerBytes != null){
                    out.writeBytes(headerBytes);
                }
                //响应数据长度
                out.writeInt(msg.getDataLength());
                //响应数据内容
                out.writeBytes(msg.getData());
            }
        } catch (Exception e) {
            logger.error("Netty请求编码异常",e);
        }
    }
}
