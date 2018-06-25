/*
 * FileName: NettyRequest.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty请求实体类
 */
package com.jsptpd.netty.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈Netty请求实体类〉<br>
 * 〈封装Netty客户端到服务端的请求〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyRequest {
    /**
     * 请求头字节数组长度
     */
    private int headerLength;
    /**
     * 请求头
     */
    private Map<String,String> headers = new HashMap<>(1);
    /**
     * 数据长度
     */
    private int dataLength;
    /**
     * 请求数据
     */
    private byte[] data;

    public NettyRequest(byte[] data){
        this(null,data);
    }

    public NettyRequest(Map<String,String> headers,byte[] data){
        if(headers != null && headers.size() > 0){
            try {
                this.headers = headers;
                ObjectMapper mapper = new ObjectMapper();
                byte[] bytes = mapper.writeValueAsString(headers).getBytes(NettyConstants.CHARSET_UTF8);
                this.headerLength = bytes.length;
            } catch (Exception e) {
                this.headerLength = 0;
                this.headers = null;
            }
        }
        if(data != null && data.length > 0){
            this.data = data;
            this.dataLength = data.length;
        }
    }
    /**
     * 构造netty请求
     * @param data 请求数据
     * @return netty请求
     */
    public static NettyRequest valueOf(byte[] data){
        return valueOf(null,data);
    }
    /**
     * 构造netty请求
     * @param headers 请求头
     * @param data 数据
     * @return netty请求
     */
    public static NettyRequest valueOf(Map<String,String> headers,byte[] data){
        return new NettyRequest(headers, data);
    }
    /**
     * 获取请求头
     * @param name 请求头名称
     * @return 请求头内容
     */
    public String getHeader(String name){
        return this.headers.get(name);
    }
    /**
     * 设置请求头
     * @param name 请求头名称
     * @param value 请求头内容
     */
    public void setHeader(String name,String value){
        if(StringUtils.isNotEmpty(name)
                && StringUtils.isNotEmpty(value)){
            this.headers.put(name,value);
        }
    }
    /**
     * Getters、Setters
     */
    public int getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(int headerLength) {
        this.headerLength = headerLength;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "jackson write error.";
        }
    }
}
