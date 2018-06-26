/*
 * FileName: NettyBaseException.java
 * Author:   Arshle
 * Date:     2018年06月15日
 * Description: netty异常
 */
package com.jsptpd.netty.exception;

/**
 * 〈netty异常〉<br>
 * 〈统一异常编码和异常内容〉
 *
 * @author Arshle
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本]（可选）
 */
public class NettyBaseException extends RuntimeException {

    private static final long serialVersionUID = -8075746284920487318L;
    /**
     * 异常编码
     */
    private String code;

    public NettyBaseException(){}

    public NettyBaseException(String code){
        this.code = code;
    }

    public NettyBaseException(String code, String message){
        super(message);
        this.code = code;
    }
}
