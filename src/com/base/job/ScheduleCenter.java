/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : MyScheduler.java
 * Create   : jimsu@tekview.com, Jan 23, 2008/9:18:22 AM
 */
package com.base.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.listeners.BroadcastSchedulerListener;

import com.base.log.LogUtil;

/**
 * 调度模块的核心，入口，单例类 <br>
 * 
 * @author jimsu
 * 
 */
public class ScheduleCenter {

	private Scheduler sch = null;

	private static ScheduleCenter inst = new ScheduleCenter();

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	private ScheduleCenter() {
	}

	public static ScheduleCenter getInstance() {
		return inst;
	}

	/**
	 * 初始化quartz：传入日志logger，必须首先被调用
	 * 
	 * @param initParam 初始化参数，logger
	 */
	public void init() {
		if (sch == null) {
			try {
				sch = StdSchedulerFactory.getDefaultScheduler();
				sch.addSchedulerListener(new BroadcastSchedulerListener() {

					@Override
					public void jobScheduled(Trigger trigger) {
						System.out.println(trigger.getJobName() + "is scheduled...");
						super.jobScheduled(trigger);
					}

					@Override
					public void schedulerShutdown() {
						super.schedulerShutdown();
					}
				});
				sch.start();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	public void shutdown() {
		try {
			sch.shutdown();
		} catch (Exception e) {
			logger.error("ScheduleCenter shutdown failed," + e);
		}
	}

	/**
	 * 执行一个一次性的任务,调用后不能被取消
	 * 
	 * @param task SimpleTask的实现类
	 * @param param 任务的参数,最终会被SimpleTask.execute(param);
	 * @return jobName
	 */
	public String submitOneTimeTask(SimpleTask task, Object param) {
		long now = System.currentTimeMillis();
		String jobName = ScheduleUtil.makeJobName(task.getClass().getSimpleName(), now);
		String groupName = ScheduleUtil.GroupName_OneTime;
		JobDetail jobdetail = new JobDetail(jobName, groupName, OneTimeJob.class);
		jobdetail.getJobDataMap().put(ScheduleUtil.TaskInterfaceKey, task);
		if (param != null) {
			jobdetail.getJobDataMap().put(ScheduleUtil.TaskCoreParamKey, param);
		}
		// 创建trigger
		String triggerName = ScheduleUtil.makeTriggerName(task.getClass().getSimpleName(), now);
		Trigger trigger = TriggerUtils.makeImmediateTrigger(triggerName, 0, 0);
		try {
			sch.scheduleJob(jobdetail, trigger);
		} catch (ObjectAlreadyExistsException e1) {
			logger.error("submitOneTimeTask scheduleJob failed," + e1);
		} catch (Exception e) {
			logger.error("submitOneTimeTask scheduleJob failed," + e);
		}
		return jobName;
	}

	/**
	 * 增加一个定期执行的任务
	 * 
	 * @param task
	 * @param param 定期轮询参数：间隔、参数 如果任务执行时间较长，则任务会排队的，直到上一个任务执行结束后才能进行到下一个任务
	 * @return jobName
	 */
	public String addRoundTask(SimpleTask task, RoundJobParam param) {
		if (task == null || param == null)
			return null;
		long now = System.currentTimeMillis();
		String jobName = ScheduleUtil.makeJobName(task.getClass().getSimpleName(), now);
		String groupName = ScheduleUtil.GroupName_RoundTask; // "roundtask";
		JobDetail jobdetail = new JobDetail(jobName, groupName, RoundJob.class);
		jobdetail.getJobDataMap().put(ScheduleUtil.TaskInterfaceKey, task);
		if (param.getJobParam() != null) {
			jobdetail.getJobDataMap().put(ScheduleUtil.TaskCoreParamKey, param.getJobParam());
		}
		// 定期执行的trigger
		String triggerName = ScheduleUtil.makeTriggerName(task.getClass().getSimpleName(), now);
		SimpleTrigger trigger = new SimpleTrigger(triggerName, groupName, new Date(), null, param.getRepeatCount(), param
				.getInteval());
		try {
			sch.scheduleJob(jobdetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("addRoundTask scheduleJob failed," + e);
		}
		logger.debug("addRoundTask scheduleJob " + jobName + " successed.");
		return jobName;
	}

	/**
	 * 增加一个定期执行的任务
	 * 
	 * @param task
	 * @param param 定期轮询参数：间隔、参数 如果任务执行时间较长，则任务会排队的，直到上一个任务执行结束后才能进行到下一个任务
	 * @return jobName
	 */
	public String addRoundTask(SimpleTask task, Date startTime, Date endTime, RoundJobParam param) {
		if (task == null || param == null)
			return null;
		long now = System.currentTimeMillis();
		String jobName = ScheduleUtil.makeJobName(task.getClass().getSimpleName(), now);
		String groupName = ScheduleUtil.GroupName_RoundTask; // "roundtask";
		JobDetail jobdetail = new JobDetail(jobName, groupName, RoundJob.class);
		jobdetail.getJobDataMap().put(ScheduleUtil.TaskInterfaceKey, task);
		if (param.getJobParam() != null) {
			jobdetail.getJobDataMap().put(ScheduleUtil.TaskCoreParamKey, param.getJobParam());
		}
		// 定期执行的trigger
		String triggerName = ScheduleUtil.makeTriggerName(task.getClass().getSimpleName(), now);
		SimpleTrigger trigger = new SimpleTrigger(triggerName, groupName, new Date(), null, param.getRepeatCount(), param
				.getInteval());
		if (startTime != null) {
			trigger.setStartTime(startTime);
		}
		if (endTime != null) {
			trigger.setEndTime(endTime);
		}
		try {
			sch.scheduleJob(jobdetail, trigger);
		} catch (Exception e) {
			logger.error("addRoundTask scheduleJob failed," + e);
		}
		logger.debug("addRoundTask scheduleJob " + jobName + " successed.");
		return jobName;
	}

	/**
	 * 外部调用者取消一个定期轮询任务 <br>
	 * 此时如果job已经进入调度期间，则即使调用了该函数，那个进入队列的job也会继续执行,他不会被interrupt，除非 <br>
	 * 这个job是InterruptableJob
	 * 
	 * @param jobName 定期轮询的任务名
	 */
	public void cancelRoundTask(String jobName) {
		if (jobName == null)
			return;
		String groupName = ScheduleUtil.GroupName_RoundTask; // "roundtask";
		try {
			logger.warn("将删除job:" + jobName);
			sch.deleteJob(jobName, groupName);
		} catch (Exception e) {
			logger.error("cancelRoundTask jobName=" + jobName + "," + e);
		}
	}

	/**
	 * 取消定时执行的任务(cron)
	 * 
	 * @param jobName
	 */
	public void cancelCronTask(String jobName) {
		if (jobName == null)
			return;
		String groupName = ScheduleUtil.GroupName_CronTask; // "crontask";
		try {
			logger.warn("将删除job:" + jobName);
			sch.deleteJob(jobName, groupName);
		} catch (Exception e) {
			logger.error("cancelRoundTask jobName=" + jobName + "," + e);
		}
	}

	/** 调试输出 */
	public void debugOutAllInfo() {
		try {
			logger.debug(sch.getMetaData());
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 增加一个cron任务
	 * 
	 * @param task cron定期执行的任务
	 * @param cronExp cron表达式，请参考unix下的crontable
	 * @param param 任务的参数,最终会被SimpleTask.execute(param);
	 * @see http://airdream.javaeye.com/blog/53472 有cron表达式的说明
	 * @return jobName
	 */
	public String addCronTask(SimpleTask task, String cronExp, Object param) {
		long now = System.currentTimeMillis();
		String jobName = ScheduleUtil.makeJobName(task.getClass().getSimpleName(), now);
		String groupName = ScheduleUtil.GroupName_CronTask;
		JobDetail jobdetail = new JobDetail(jobName, groupName, CronJob.class);
		jobdetail.getJobDataMap().put(ScheduleUtil.TaskInterfaceKey, task);
		if (param != null) {
			jobdetail.getJobDataMap().put(ScheduleUtil.TaskCoreParamKey, param);
		}
		// 建立cron触发器
		CronExpression cexp = null;
		String triggerName = ScheduleUtil.makeTriggerName(task.getClass().getSimpleName(), now);
		CronTrigger cronTrigger = new CronTrigger(triggerName, groupName);
		try {
			cexp = new CronExpression(cronExp);// "0/5 * * * * ?"
		} catch (Exception e) {
			logger.error("new CronExpression failed," + e);
			return jobName;
		}
		cronTrigger.setCronExpression(cexp);
		try {
			System.out.println(jobdetail);
			System.out.println(cronTrigger);
			sch.scheduleJob(jobdetail, cronTrigger);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("CronTrigger scheduleJob failed," + e);
		}
		logger.debug("addCronTask successed," + jobName);
		return jobName;
	}

	/**
	 * 增加一个cron任务
	 * 
	 * @param task cron定期执行的任务
	 * @param cronExp cron表达式，请参考unix下的crontable
	 * @param startTime - 开始执行时间 可以为空
	 * @param endTime - 结束时间 可以为空
	 * @param param 任务的参数,最终会被SimpleTask.execute(param);
	 * @see http://airdream.javaeye.com/blog/53472 有cron表达式的说明
	 * @return jobName
	 */
	public String addCronTask(SimpleTask task, String cronExp, Date startTime, Date endTime, Object param) {
		long now = System.currentTimeMillis();
		String jobName = ScheduleUtil.makeJobName(task.getClass().getSimpleName(), now);
		String groupName = ScheduleUtil.GroupName_CronTask;
		JobDetail jobdetail = new JobDetail(jobName, groupName, CronJob.class);
		jobdetail.getJobDataMap().put(ScheduleUtil.TaskInterfaceKey, task);
		if (param != null) {
			jobdetail.getJobDataMap().put(ScheduleUtil.TaskCoreParamKey, param);
		}
		// 建立cron触发器
		CronExpression cexp = null;
		String triggerName = ScheduleUtil.makeTriggerName(task.getClass().getSimpleName(), now);
		CronTrigger cronTrigger = new CronTrigger(triggerName, groupName);
		try {
			cexp = new CronExpression(cronExp);// "0/5 * * * * ?"
		} catch (Exception e) {
			logger.error("new CronExpression failed," + e);
			return jobName;
		}
		cronTrigger.setCronExpression(cexp);
		if (startTime != null) {
			cronTrigger.setStartTime(startTime);
		}
		if (endTime != null) {
			cronTrigger.setEndTime(endTime);
		}
		try {
			sch.scheduleJob(jobdetail, cronTrigger);
		} catch (Exception e) {
			logger.error("CronTrigger scheduleJob failed," + e);
		}
		logger.debug("addCronTask successed," + jobName);
		return jobName;
	}
}
