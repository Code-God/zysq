/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : SimpleTask.java
 * Create   : jimsu@tekview.com, Jan 23, 2008/1:40:58 PM
 */
package com.base.job;

import java.util.Date;

/**
 * 调用者必须实现的任务接口
 * @author jimsu
 *
 */
public abstract class SimpleTask {
	
	/**
	 * 任务对应的结果处理类
	 */
	private ResultHandler handler=null;
	public void setResultHandler(ResultHandler handler){
		this.handler=handler;
	}
	public ResultHandler getResultHandler(){
		return this.handler;
	}
	
	/**
	 * 执行任务
	 * @param param
	 * @param nextFireTime 下次执行时间
	 * @return
	 */
	public Object execute(Object param, Date nextFireTime){
		return null;
	}
	
//	public Object execute(Object param){
//		return null;
//	}
}
