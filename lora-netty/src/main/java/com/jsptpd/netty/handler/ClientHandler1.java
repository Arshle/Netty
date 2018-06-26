/*
 * FileName: ClientHandler1.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description:
 */
package com.jsptpd.netty.handler;

import com.jsptpd.netty.annotation.NettyClientHandler;
import com.jsptpd.netty.intf.NettyClientMessageHandler;
import com.jsptpd.netty.model.NettyResponse;
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
@NettyClientHandler(order = 1)
public class ClientHandler1 implements NettyClientMessageHandler {

    private Logger logger = LoggerFactory.getLogger(ClientHandler1.class);

    @Override
    public void handleResponse(NettyResponse response) {
        logger.info("clientHandler1接收消息:" + response);
    }

    @Override
    public void handleException(Throwable cause) {

    }
}
