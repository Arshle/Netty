/*
 * FileName: NettyResponse.java
 * Author:   Arshle
 * Date:     2018年06月25日
 * Description: Netty响应实体类
 */
package com.jsptpd.netty.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsptpd.netty.constants.NettyConstants;
import com.jsptpd.netty.utils.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈Netty响应实体类〉<br>
 * 〈封装服务端到客户端响应数据〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyResponse {
    /**
     * 响应头字节数组长度
     */
    private int headerLength;
    /**
     * 响应头
     */
    private Map<String,String> headers = new HashMap<>(1);
    /**
     * 数据长度
     */
    private int dataLength;
    /**
     * 响应数据
     */
    private byte[] data;

    public NettyResponse(byte[] data){
        this(null,data);
    }

    public NettyResponse(Map<String,String> headers,byte[] data){
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
     * 构造netty响应
     * @param data 响应数据
     * @return netty响应
     */
    public static NettyResponse valueOf(byte[] data){
        return valueOf(null,data);
    }
    /**
     * 构造netty响应
     * @param headers 响应头
     * @param data 数据
     * @return netty响应
     */
    public static NettyResponse valueOf(Map<String,String> headers,byte[] data){
        return new NettyResponse(headers, data);
    }
    /**
     * 获取响应头
     * @param name 响应头名称
     * @return 响应头内容
     */
    public String getHeader(String name){
        return this.headers.get(name);
    }
    /**
     * 设置响应头
     * @param name 响应头名称
     * @param value 响应头内容
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
