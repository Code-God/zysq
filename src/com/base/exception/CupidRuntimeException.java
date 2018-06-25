/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project: Ocean
 */
package com.base.exception;

/**
 * 运行时异常基类
 * 
 * @author Jacky
 * @version 5.0
 * 
 */
public class CupidRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9141279329385338251L;

	/**
	 * 抛出到客户端的异常需要一个errorcode错误码
	 */
	private int errorCode;

	/**
	 * 抛出到客户端的异常需要的参数
	 */
	private Object[] params;
	
	/**
	 * 需要显示到界面上的参数，考虑到大部分错误信息之需要一个参数，故加了这个简易参数，如果有多个参数请使用数组params
	 */
     private String errorParam;
     
	/**
	 * @param message 异常错误信息
	 */
	public CupidRuntimeException(String message) {
		super(message);
	}
	
	/**
	 * 
	 * @param message
	 * @param params
	 */
	public CupidRuntimeException(String message,String errorParam) {
		super(message);
		this.errorParam = errorParam;
	}

	/**
	 * @param errorCode 错误码
	 */
	public CupidRuntimeException(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @param errorCode 错误码
	 * @param params 异常参数
	 */
	public CupidRuntimeException(int errorCode, Object[] params) {
		this.errorCode = errorCode;
		this.params = params;
	}
	
	/**
	 * @param errorCode 错误码
	 * @param params 异常参数
	 */
	public CupidRuntimeException(String message, Object[] params) {
		super(message);
		this.params = params;
	}

	/**
	 * @param message 异常简要信息
	 * @param cause 根异常
	 */
	public CupidRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public Object[] getParams() {
		return params;
	}
	
	public void setParams(Object[] params) {
		this.params = params;
	}

	
	public String getErrorParam() {
		return errorParam;
	}

	
	public void setErrorParam(String errorParam) {
		this.errorParam = errorParam;
	}
}