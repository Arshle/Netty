/*
 * FileName: NettyServerConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty服务端配置类
 */
package com.jsptpd.netty.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 〈Netty服务端配置类〉<br>
 * 〈服务端配置〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("nettyServerConfiguration")
public class NettyServerConfiguration {
    /**
     * 服务端启动端口
     */
    @Value("${netty.server.port:18000}")
    private int port;
    /**
     * 连接队列长度
     */
    @Value("${netty.server.backLog:1024}")
    private int backLog;
    /**
     * 禁用nagle算法,提升tcp传输效率
     */
    @Value("${netty.server.tcpNoDelay:true}")
    private boolean tcpNoDelay;
    /**
     * 是否保持心跳
     */
    @Value("${netty.server.keepAlive:true}")
    private boolean keepAlive;
    /**
     * 连接超时时间
     */
    @Value("${netty.server.connect.timeout.ms:10000}")
    private int connectTimeout;
    /**
     * 每次循环写操作的最大数量
     */
    @Value("${netty.server.write.spin.count:16}")
    private int writeSpinCount;
    /**
     * 是否自动读取数据
     */
    @Value("${netty.server.auto.read:true}")
    private boolean autoRead;
    /**
     * 是否单线程执行pipeline
     */
    @Value("${netty.server.single.event.executor.per.group:true}")
    private boolean singleEventExecutorPerGroup;
    /**
     * 是否可以重复使用端口
     */
    @Value("${netty.server.reuse.addr:false}")
    private boolean reuseAddr;
    /**
     * 关闭socket的延时时间，默认关闭
     */
    @Value("${netty.server.linger:-1}")
    private int linger;
    /**
     * 远端关闭时是否保持本端连接
     */
    @Value("${netty.server.allow.half.closure:false}")
    private boolean allowHalfClosure;
    /**
     * 是否解码请求
     */
    @Value("${netty.server.decode.request:true}")
    private boolean decodeRequest;
    /**
     * 是否编码响应
     */
    @Value("${netty.server.encoder.response:true}")
    private boolean encodeResponse;
    /**
     * Getters、Setters
     */
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBackLog() {
        return backLog;
    }

    public void setBackLog(int backLog) {
        this.backLog = backLog;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getWriteSpinCount() {
        return writeSpinCount;
    }

    public void setWriteSpinCount(int writeSpinCount) {
        this.writeSpinCount = writeSpinCount;
    }

    public boolean isAutoRead() {
        return autoRead;
    }

    public void setAutoRead(boolean autoRead) {
        this.autoRead = autoRead;
    }

    public boolean isSingleEventExecutorPerGroup() {
        return singleEventExecutorPerGroup;
    }

    public void setSingleEventExecutorPerGroup(boolean singleEventExecutorPerGroup) {
        this.singleEventExecutorPerGroup = singleEventExecutorPerGroup;
    }

    public boolean isReuseAddr() {
        return reuseAddr;
    }

    public void setReuseAddr(boolean reuseAddr) {
        this.reuseAddr = reuseAddr;
    }

    public int getLinger() {
        return linger;
    }

    public void setLinger(int linger) {
        this.linger = linger;
    }

    public boolean isAllowHalfClosure() {
        return allowHalfClosure;
    }

    public void setAllowHalfClosure(boolean allowHalfClosure) {
        this.allowHalfClosure = allowHalfClosure;
    }

    public boolean isDecodeRequest() {
        return decodeRequest;
    }

    public void setDecodeRequest(boolean decodeRequest) {
        this.decodeRequest = decodeRequest;
    }

    public boolean isEncodeResponse() {
        return encodeResponse;
    }

    public void setEncodeResponse(boolean encodeResponse) {
        this.encodeResponse = encodeResponse;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "jackson write error.";
        }
    }
}
