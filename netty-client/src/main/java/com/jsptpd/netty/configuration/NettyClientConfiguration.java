/*
 * FileName: NettyClientConfiguration.java
 * Author:   Arshle
 * Date:     2018年06月26日
 * Description: Netty客户端配置
 */
package com.jsptpd.netty.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 〈Netty客户端配置〉<br>
 * 〈用于配置客户端〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
@Component("nettyClientConfiguration")
public class NettyClientConfiguration {
    /**
     * 服务端连接地址
     */
    @Value("${netty.client.server.address:localhost}")
    private String serverAddress;
    /**
     * 服务端口号
     */
    @Value("${netty.client.server.port:18000}")
    private int serverPort;
    /**
     * 连接队列长度
     */
    @Value("${netty.client.backLog:1024}")
    private int backLog;
    /**
     * 禁用nagle算法,提升tcp传输效率
     */
    @Value("${netty.client.tcpNoDelay:true}")
    private boolean tcpNoDelay;
    /**
     * 是否保持心跳
     */
    @Value("${netty.client.keepAlive:true}")
    private boolean keepAlive;
    /**
     * 连接超时时间
     */
    @Value("${netty.client.connect.timeout.ms:10000}")
    private int connectTimeout;
    /**
     * 每次循环写操作的最大数量
     */
    @Value("${netty.client.write.spin.count:16}")
    private int writeSpinCount;
    /**
     * 是否自动读取数据
     */
    @Value("${netty.client.auto.read:true}")
    private boolean autoRead;
    /**
     * 是否单线程执行pipeline
     */
    @Value("${netty.client.single.event.executor.per.group:true}")
    private boolean singleEventExecutorPerGroup;
    /**
     * 是否可以重复使用端口
     */
    @Value("${netty.client.reuse.addr:false}")
    private boolean reuseAddr;
    /**
     * 关闭socket的延时时间，默认关闭
     */
    @Value("${netty.client.linger:-1}")
    private int linger;
    /**
     * 远端关闭时是否保持本端连接
     */
    @Value("${netty.client.allow.half.closure:false}")
    private boolean allowHalfClosure;
    /**
     * 是否解码请求
     */
    @Value("${netty.client.decode.response:true}")
    private boolean decodeResponse;
    /**
     * 是否编码响应
     */
    @Value("${netty.client.encode.request:true}")
    private boolean encodeRequest;
    /**
     * Getters、Setters
     */
    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
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

    public boolean isDecodeResponse() {
        return decodeResponse;
    }

    public void setDecodeResponse(boolean decodeResponse) {
        this.decodeResponse = decodeResponse;
    }

    public boolean isEncodeRequest() {
        return encodeRequest;
    }

    public void setEncodeRequest(boolean encodeRequest) {
        this.encodeRequest = encodeRequest;
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
