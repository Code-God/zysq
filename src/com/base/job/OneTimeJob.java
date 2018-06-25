/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : OneTimeJob.java
 * Create   : jimsu@tekview.com, Jan 23, 2008/1:40:58 PM
 */
package com.base.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.base.log.LogUtil;

/**
 * 一次性任务的执行(不需要StatefulJob)
 * 
 * @author jimsu
 * 
 */
public class OneTimeJob implements Job {

	protected static Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);

	public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
		// 解析参数
		JobDetail jd = jobexecutioncontext.getJobDetail();
		if (jd == null)
			return;
		logger.debug("-----" + jd.getFullName() + "开始执行任务");
		if (jd.getJobDataMap() == null)
			return;
		// 真正的业务处理
		try {
			Object param = jd.getJobDataMap().get(ScheduleUtil.TaskCoreParamKey);
			SimpleTask task = (SimpleTask) jd.getJobDataMap().get(ScheduleUtil.TaskInterfaceKey);
			Date nextFireTime = jobexecutioncontext.getNextFireTime();
			task.execute(param, nextFireTime);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		logger.debug("-----" + jd.getFullName() + "任务结束");
	}
}
