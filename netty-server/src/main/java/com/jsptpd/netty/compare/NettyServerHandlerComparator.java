/*
 * FileName: NettyServerHandlerComparator.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty服务端请求处理类排序
 */
package com.jsptpd.netty.compare;

import com.jsptpd.netty.annotation.NettyServerHandler;
import com.jsptpd.netty.intf.NettyServerMessageHandler;
import java.util.Comparator;

/**
 * 〈Netty服务端请求处理类排序〉<br>
 * 〈主要用于排序请求处理类〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyServerHandlerComparator implements Comparator<NettyServerMessageHandler> {
    /**
     * 比较大小
     * @param o1 处理拦截类1
     * @param o2 处理拦截类2
     * @return 比较结果
     */
    @Override
    public int compare(NettyServerMessageHandler o1, NettyServerMessageHandler o2) {
        NettyServerHandler serverHandler1 = o1.getClass().getAnnotation(NettyServerHandler.class);
        NettyServerHandler serverHandler2 = o2.getClass().getAnnotation(NettyServerHandler.class);
        if(serverHandler1 != null && serverHandler2 != null){
            if(serverHandler1.order() > serverHandler2.order()){
                return 1;
            }else if(serverHandler1.order() < serverHandler2.order()){
                return -1;
            }
        }
        return 0;
    }
}
