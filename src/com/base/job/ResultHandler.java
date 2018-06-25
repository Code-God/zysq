/********************************************
 * Copyright 2008 Tekview Technology Co.,Ltd. 
 * All right reserved.
 * Create Date     : Mar 18, 2008
 * Create Author   : jimsu
 * File Name       : ResultHandler.java
 * Last Update Date: 
 * Change Log      :
 ******************************************/
package com.base.job;

/**
 * 轮询结果处理接口
 * 
 * @since ocean v5
 * @author jimsu
 */
public interface ResultHandler {

	/**
	 * 初始化该handler
	 * 
	 * @param param
	 */
	public void init(Object param);

	/**
	 * 结果处理函数
	 * 
	 * @param args 传入的参数 [0]task信息 [1]任务处理后的结果
	 */
	public void handle(Object[] args);
}
