/*
 * Copyright 2013-2023 by Langna Tech. Co. Ltd. All Rights Reserved.
 */
package com.langnatech.core.exception;
/**
 * UnauthorizedAccessException是一个自定义异常，继承了RuntimeException，
 * 和BaseRuntimeException的区别在于该类和国际化配置没有关系，对于message
 * 参数没有限制，可自定义中文描述。
 * 
 * @author NanBo
 * @version V0.0.1-SNAPSHOT 日期：2013-10-12
 * @since 0.0.1-SNAPSHOT
 */
public class UnauthorizedAccessException extends RuntimeException{
	private static final long serialVersionUID = -7876336675027078532L;

	/**
     * UnauthorizedAccessException默认构造器，可直接实例化无需传参数。
     */
    public UnauthorizedAccessException(){
        super();
    }
    
    /**
     * UnauthorizedAccessException的有参数构造器，传入的message参数就作为抛出异常的描述信息。<br/>
     * 详细描述：UnauthorizedAccessException进行实例化时传入的message参数，该参数会替换父类的异常message信息。</br>
     * 使用方式：throw new UnauthorizedAccessException(message)，message参数可自由定义描述信息。
     * @param message 会自动替换父类的异常message信息。
     */
    public UnauthorizedAccessException(String message){
        super(message);
    }
    
    /**
     * UnauthorizedAccessException的有参数构造器，直接抛出传入的异常类型。<br/>
     * 详细描述：根据传入的异常类型，通过调用父类的方法直接抛出该异常，内部的信息没有做任何处理，仅仅只是异常类变为UnauthorizedAccessException。</br>
     * 使用方式：throw new UnauthorizedAccessException(e)，e为catch中捕获到的异常对象。
     * @param cause 所有错误和异常对象的超类。
     */
    public UnauthorizedAccessException(Throwable cause){
        super(cause);
    }
    
    /**
     * UnauthorizedAccessException的有参数构造器，根据message和cause参数抛出新的异常信息。<br/>
     * 详细描述：UnauthorizedAccessException进行实例化时传入的message和cause参数，会通过调用父类的方法，把异常抛出。</br>
     * 使用方式：throw new BaseRuntimeException(message,cause)。
     * @param message 会自动替换父类的异常message信息。
     * @param cause 所有错误和异常对象的超类。
     */
    public UnauthorizedAccessException(String message, Throwable cause){
        super(message, cause);
    }
}