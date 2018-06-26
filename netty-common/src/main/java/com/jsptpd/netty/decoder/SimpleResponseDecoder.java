/*
 * FileName: SimpleResponseDecoder.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: 响应解码类,适用于仅传输二进制字节数组数据
 */
package com.jsptpd.netty.decoder;

import com.jsptpd.netty.model.NettyResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.List;

/**
 * 〈响应解码类,适用于仅传输二进制字节数组数据〉<br>
 * 〈将二进制字节数组解码成NettyResponse〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class SimpleResponseDecoder extends ByteToMessageDecoder {

    private Logger logger = LoggerFactory.getLogger(SimpleResponseDecoder.class);
    /**
     * 解码
     * @param ctx 处理链上下文
     * @param in 入站字节数组
     * @param out 解码后的类
     */
    @SuppressWarnings("Duplicates")
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        while (true){
            int dataLength = in.readableBytes();
            if(dataLength > 0){
                byte[] data = new byte[dataLength];
                in.readBytes(data);
                try {
                    logger.info("解码Netty响应数据|length:" + data.length + "|data:" + Arrays.toString(data));
                    out.add(NettyResponse.valueOf(data));
                } catch (Exception e) {
                    logger.error("解码Netty响应异常",e);
                }
            }else{
                break;
            }
        }
    }
}
