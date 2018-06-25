/*
 * FileName: NettyRequestDecoder.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty客户端请求解码器，用于客户端与服务端统一的解码
 */
package com.jsptpd.netty.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.model.NettyRequest;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;

/**
 * 〈Netty客户端请求解码器，用于客户端与服务端统一的解码〉<br>
 * 〈将客户端序列化后的NettyRequest解码〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyRequestDecoder extends ByteToMessageDecoder {

    private Logger logger = LoggerFactory.getLogger(NettyRequestDecoder.class);
    /**
     * 解码
     * @param ctx 处理链上下文
     * @param in 入站字节数组
     * @param out 解码后的对象
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out){
        while (true){
            if(in.readableBytes() > 0){
                Map<String,String> headers = null;
                //记录数据包开始位置
                int beginIndex;
                try {
                    while(true) {
                        //包头开始游标点
                        beginIndex = in.readerIndex();
                        //标记初始读游标位置
                        in.markReaderIndex();
                        //如果找到包头则结束循环
                        if (in.readInt() == NettyConstants.HEAD_FLAG) {
                            break;
                        }
                        //未读到包头标识略过一个字节
                        in.resetReaderIndex();
                        in.readByte();
                    }
                    //请求头长度
                    int headerLength = in.readInt();
                    if(headerLength > 0){
                        //数据包没到齐,直接缓存
                        if(in.readableBytes() < headerLength){
                            in.readerIndex(beginIndex);
                            return;
                        }
                        //读取请求头字节数组
                        byte[] headerBytes = new byte[headerLength];
                        in.readBytes(headerBytes);
                        String headerJson = new String(headerBytes,NettyConstants.CHARSET_UTF8);
                        ObjectMapper mapper = new ObjectMapper();
                        headers = mapper.readValue(headerJson, Map.class);
                    }
                    int dataLength = in.readInt();
                    //数据长度异常，关闭通道
                    if(dataLength < 0){
                        ctx.channel().close();
                    }
                    //数据包没到齐，直接缓存
                    if(in.readableBytes() < dataLength){
                        in.readerIndex(beginIndex);
                        return;
                    }
                    byte[] data = new byte[dataLength];
                    in.readBytes(data);
                    //解析出请求对象，像后面handler传递
                    NettyRequest request = NettyRequest.valueOf(headers, data);
                    out.add(request);
                    logger.info("Netty请求解码完成|request:" + request);
                } catch (Exception e) {
                    logger.error("解析Netty请求异常",e);
                }
            }else{
                break;
            }
        }
    }
}
