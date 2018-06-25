/******************************************************************************** 
 * Create Author   : JoveDeng
 * Create Date     : Jan 22, 2010
 * File Name       : JobRunnarFactory.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
/******************************************************************************** 
 * Create Author   : JoveDeng
 * Create Date     : Jan 22, 2010
 * File Name       : JobRunnarFactory.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.base.log.LogUtil;
import com.wfsc.util.DateUtil;


/**
 * 在不重新启动服务的情况下更新定时服务的时间配置
 * (主要是方便测试用,在频繁手动更改服务的启动时间规则时系统自动加载新的时间规则)
 * 服务配置文件: /itsm/src/ossjob.properties
 * @author JoveDeng
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class JobRunner {
	
	private static Logger logg = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	 
	private  final  static String ClassKeyName = "class";
	
	private  final  static String CronExpressionKeyName = "CronExpression";
	
	private  final  static String JobNameKeyName = "JobName";
	
	private  final  static String JOBSKeyName = "JOBS";
	
	private final static String JobAutoRefresh="JobAutoRefresh";
	
	private static List<String> joblist = new ArrayList<String>();
	
	private static ScheduleCenter schedule = null;
	
	private JobRunner(){
		if(schedule==null){
			schedule = ScheduleCenter.getInstance();
			schedule.init();
			runAutoRefreshJob();
		}
	}
	
	/**
	 * 
	 * 启动服务
	 */
	public static void run(){
		JobRunner runner = new JobRunner();
		Properties conf = runner.getConfig();
		if(null == conf)return;
		runner.resumeAll();
		String[] job = runner.getJobNameList(conf);
		for(String jobName :job){
			runner.startJob(conf, jobName);
		}
		runner.startScheduler();
	}
	
	private void startScheduler(){
		
	}
	
	@SuppressWarnings("unchecked")
	private void resumeAll(){
		for(String jobname : joblist){
			schedule.cancelCronTask(jobname);
		}
		joblist.removeAll(joblist);
		String time = DateUtil.convertLong2String(System.currentTimeMillis(), "yyyy-mm-dd HH:mm:ss");
		logg.info("------------------------"+time+"   刷新轮询任务 ");
	}
	
	/**
	 * 
	 * 启动轮询任务
	 * 
	 * @param conf
	 * @param jobName
	 */
	private void startJob(Properties conf,String jobName){
		String cexpstr = StringUtils.trimToNull(conf.getProperty(jobName+"."+CronExpressionKeyName));
		String className = StringUtils.trimToNull(conf.getProperty(jobName+"."+ClassKeyName));
		String jobNameZH = conf.getProperty(jobName+"."+JobNameKeyName);
		if(jobNameZH!=null){
			try {
				jobNameZH =	new String(jobNameZH.getBytes("iso8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}else{
			jobNameZH="";
		}
		if(null == cexpstr || null ==className)return ;
		logg.info("启动:"+jobNameZH+"任务..."+jobName);
		joblist.add(runJob(jobName, className, cexpstr));
	}
	
	/**
	 * 启动自动更新
	 * 用于检查轮询任务的配置是否需要更新(如果更新了则重新加载所有轮询任务)
	 */
	private void runAutoRefreshJob(){
		this.runJob("autoRefreshJob", AutoRefreshJob.class.getName(), "0 0/1 * * * ?");//1分钟查看一次配置文件
	}
	
	public static void main(String[] args){
		System.out.println(AutoRefreshJob.class.getName());
	}
	
	/**
	 * 
	 * 启动一项轮询任务
	 * @param jobName
	 * @param className
	 * @param cexpstr
	 */
	private String runJob(String jobName , String className ,String cexpstr){
		Object obj = null;
		String jobname = null;
		try {
			try {
				logg.info("任务===="+className);
				obj = Class.forName(className).newInstance();
				System.out.println(className);
			} catch (InstantiationException e) {
				logg.error(e.getMessage());
			} catch (IllegalAccessException e) {
				logg.error(e.getMessage());
			}
			SimpleTask stask = (SimpleTask)obj;
			jobname = schedule.addCronTask(stask, cexpstr, null);
			//joblist.add(jobname);
			/*if(null ==obj){
				String exc = jobName+"任务:启动发生错误, "+obj.getClass().getName()+"没有实现Job接口!";
				logg.error(exc);
				throw new OceanRuntimeException(exc);
			}*/
			/*Job newJob = (Job) obj;
			JobDetail jobDetail =scheduler.getJobDetail(jobName, jobName);
			if(jobDetail ==null)jobDetail = new JobDetail(jobName,jobName,newJob.getClass()); 
			JobDataMap jobMap = jobDetail.getJobDataMap();
			jobMap.clear();
			CronTrigger cronTrigger = new CronTrigger(jobName,jobName); 
			CronExpression cexp = new CronExpression(cexpstr);
			cronTrigger.setCronExpression(cexp);//设置Cron表达式 
			scheduler.deleteJob(jobName, jobName);
			scheduler.scheduleJob(jobDetail, cronTrigger); */
		} catch (ClassNotFoundException e) {
			logg.error(e.getMessage());
		} catch (Exception e){
			logg.error(e.getMessage());
		}
		return jobname;
	}
	
	/**
	 * 
	 * 任务列表
	 * 
	 * @param conf
	 * @return
	 */
	private String[] getJobNameList(Properties conf){
		String jobs  = StringUtils.trimToEmpty(conf.getProperty(JOBSKeyName));
		return StringUtils.split(jobs, ",");
	}
	
	/**
	 * 
	 * 获取配置文件
	 * @return
	 */
	private  Properties getConfig(){
		Properties proper = new Properties();
		InputStream  input = null;
		try {
			String filestr =JobRunner.class.getClassLoader().getResource("ossjob.properties").getPath();
			File file = new File(filestr);
			input = new FileInputStream(file);
			input.read();
			proper.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != input){
				try {
					input.close();
				} catch (IOException e){
					logg.error(e.getMessage());
				}
			}
		}
		return proper;
	}
	
	/**
	 * 
	 * 查看配置文件是否设置了动态加载
	 * 
	 * @return
	 */
	public static boolean needRefresh(){
		JobRunner runner = new JobRunner();
		Properties conf = runner.getConfig();
		String flag = conf.getProperty(JobRunner.JobAutoRefresh);
		return StringUtils.trimToEmpty(flag).toUpperCase().equals("Y");
	}
}

