/*
 * FileName: NettyBaseExceptionType.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description: netty异常编码
 */
package com.jsptpd.netty.code;

/**
 * 〈netty异常编码〉<br>
 * 〈记录统一异常编号〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public enum NettyBaseExceptionType {
    /**
     * 所需组件缺失异常
     */
    E0001("E0001","组件缺失异常");
    /**
     * 异常编码
     */
    private String code;
    /**
     * 异常描述
     */
    private String description;

    NettyBaseExceptionType(String code, String description){
        this.code = code;
        this.description = description;
    }
    /**
     * Getters、Setters
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
