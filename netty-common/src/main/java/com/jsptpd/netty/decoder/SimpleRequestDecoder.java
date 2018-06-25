/*
 * FileName: SimpleRequestDecoder.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: 请求解码类,适用于客户端不是Netty的普通socket传输的二进制字节数组
 */
package com.jsptpd.netty.decoder;

import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.model.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * 〈请求解码类,适用于客户端不是Netty的普通socket传输的二进制字节数组〉<br>
 * 〈将二进制字节数组解码成NettyRequest〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class SimpleRequestDecoder extends ByteToMessageDecoder {

    private Logger logger = LoggerFactory.getLogger(SimpleRequestDecoder.class);
    /**
     * 解码
     * @param ctx 处理链上下文
     * @param in 入站字节数组
     * @param out 解码后的类
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        while (true){
            if(in.readableBytes() > 0){
                byte[] data = in.array();
                if(data != null && data.length > 0){
                    try {
                        logger.info("解码Netty请求数据|length:" + data.length + "|data:" + new String(data,NettyConstants.CHARSET_UTF8));
                        out.add(NettyRequest.valueOf(data));
                    } catch (Exception e) {
                        logger.error("解码Netty请求异常",e);
                    }
                }
            }else{
                break;
            }
        }
    }
}
