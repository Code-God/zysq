/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : ScheduleUtil.java
 * Create   : jimsu@tekview.com, Jan 23, 2008/1:24:14 PM
 */
package com.base.job;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * schedule模块的通用处理定义：常量，工具方法等
 * 
 * @author jimsu
 * 
 */
public class ScheduleUtil {

	public static final String TaskCoreParamKey = "taskparam"; // task的参数

	public static final String TaskInterfaceKey = "taskif"; // task接口类

	public static final String GroupName_OneTime = "onetime"; // 一次性的任务

	public static final String GroupName_RoundTask = "roundtask"; // 定期轮询任务

	public static final String GroupName_CronTask = "crontask"; // cron定期轮询任务

	/**
	 * 得到一个任务名，这是与当前时刻有关系的
	 * 
	 * @param t1
	 * @param jobClassSimpleName
	 * @return jobName
	 */
	public static String makeJobName(String jobClassSimpleName, long t1) {
		String jobName = "j" + jobClassSimpleName + t1 + "-" + UUID.randomUUID();
		return jobName;
	}

	/**
	 * trigger的名字
	 * 
	 * @param jobClassSimpleName
	 * @param t1
	 * @return trigger name
	 */
	public static String makeTriggerName(String jobClassSimpleName, long t1) {
		String triName = "t" + jobClassSimpleName + t1 + "-" + UUID.randomUUID();
		;
		return triName;
	}

	/**
	 * 每小时第0分钟0秒执行一次：即每小时执行一次
	 * 
	 * @return
	 */
	public static String getEveryHourCronExp() {
		return "0 0 * * * ?";
	}

	/**
	 * 根据创建一个简单cron表达式，每天执行。精确到时：分：秒<br>
	 * e.g. "0 15 10 ? * *" Fire at 10:15am every day
	 * 
	 * @param nextExcTime
	 * @return
	 */
	public static String createSimpleCronExp(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		int hour = instance.get(Calendar.HOUR_OF_DAY); // 0-23
		int min = instance.get(Calendar.MINUTE);
		int sec = instance.get(Calendar.SECOND);
		String exp = sec + " " + min + " " + hour + " ? * *";
		return exp;
	}
}
