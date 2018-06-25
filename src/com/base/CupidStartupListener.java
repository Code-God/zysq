/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : NmServerStartupListener.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/12:07:39 PM
 */
package com.base;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.base.job.JobRunner;
import com.base.log.LogUtil;
import com.base.tools.Version;

/**
 * 启动监听类，用来获取Spring容器的{@link WebApplicationContext}工厂对象，注入到{@link ServerBeanFactory}中， 供服务器端各个模块调用，并调用{@link ServerBeanFactory#initModules()}方法初始化网管系统。
 * 
 * @author Andy Cui
 * @version 1.0
 * 
 * @since Apex Ocean 5.0
 */
public class CupidStartupListener implements ServletContextListener {

	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 1. Spring容器初始化，根据配置初始化各个bean
//		APEXServletContext.getInstance(sce.getServletContext());
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		ServerBeanFactory.setAppContext(ctx);
		ServerBeanFactory.setServletContext(sce.getServletContext());
		ServerBeanFactory.initModules();
		logger.info("启动定时任务");
		JobRunner.run();
//		AutoBackUp autoBackUp=new AutoBackUp();
//		ScheduleCenter schedule = ScheduleCenter.getInstance();
//		schedule.init();
//		String jobName = schedule.addCronTask(autoBackUp, "0 0/1 9,12 * * ?",null);
//		logger.info("启动数据库备份计划，jobName= "+jobName);
		//服务器重新启动时，重新加载已经配置好的作业计划
//		ScheduleAllOperater scheduleAllOperater = (ScheduleAllOperater) ServerBeanFactory.getBean("scheduleAllOperater");
//		scheduleAllOperater.startUpAllSchedule();
//		SLAService slaService = (SLAService)ServerBeanFactory.getBean("slaService");
//		slaService.reStartAllJobs();
//		CreatTestDataUtil.creatData();//创建测试数据
		logger.info("Cupid online Server [" + Version.getInstance().getSvnTag() + ".GA (build: SVNTag="
				+ Version.getInstance().getSvnTag() + " date=" + Version.getInstance().getBuildDate() + ")] has started...");
		//创建或更新资产视图
//		CreateFormService createFormService=(CreateFormService)ServerBeanFactory.getBean("createFormService");
//		createFormService.createAssetView();
		}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Cupid online Server [" + Version.getInstance().getSvnTag() + ".GA (build: SVNTag="
				+ Version.getInstance().getSvnTag() + " date=" + Version.getInstance().getBuildDate() + ")] has stopped...");
	}

	
}