/*
 * FileName: NettyInitializer.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty服务端启动类
 */
package com.jsptpd.netty.initializer;

import com.jsptpd.netty.server.NettyServer;
import com.jsptpd.netty.thread.NettyThreadUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 〈Netty服务端启动类〉<br>
 * 〈用于启动Netty服务端，在spring装载bean完成后〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("nettyInitializer")
public class NettyInitializer implements CommandLineRunner {
    /**
     * 程序启动运行
     * @param args 参数
     */
    @Override
    public void run(String... args){
        //启动netty服务端
        NettyThreadUtils.getThreadPoolExecutor().execute(NettyServer::start);
    }
}
