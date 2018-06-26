/*
 * FileName: LoraServerMessageHandler2.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description:
 */
package com.jsptpd.netty.handler;

import com.jsptpd.netty.annotation.NettyServerHandler;
import com.jsptpd.netty.intf.NettyServerMessageHandler;
import com.jsptpd.netty.model.NettyRequest;
import com.jsptpd.netty.writer.NettyResponseWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 〈〉<br>
 * 〈〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@NettyServerHandler(order = 2)
public class LoraServerMessageHandler2 implements NettyServerMessageHandler {

    private Logger logger = LoggerFactory.getLogger(LoraServerMessageHandler2.class);
    @Override
    public void handleRequest(NettyRequest request, NettyResponseWriter writer) {
        logger.info("lora2接收消息:" + request);
        writer.writeString("收到消息");
        throw new RuntimeException("test Exception");
    }

    @Override
    public void handleException(Throwable cause) {
        logger.error("处理异常",cause);
    }
}
