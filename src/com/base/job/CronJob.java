/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : CronBasicTask.java
 * Create   : jimsu@tekview.com, Jan 23, 2008/9:33:31 AM
 */
package com.base.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * 类似unix下cron table调度方式的任务
 * <li>cronExpression配置说明：
 * <br>秒 0-59,分 0-59,小时 0-23,日期 1-31,月份 1-12,星期 1-7 年（可选）留空,1970-2099
 * <br>0/n表示每n个时间间隔执行一次，1,3,5,7,10表示第1,3,5,7,10时执行一次
 * <li>see:http://airdream.javaeye.com/blog/53472
 * <br>
 * @author jimsu
 *
 */
public class CronJob extends OneTimeJob {
	
	public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		super.execute(jobexecutioncontext);
		logger.debug("CronJob execute finish,"+new Date());
	}
}
