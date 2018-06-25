/******************************************************************************** 
 * Create Author   :timy
 * Create Date     : Jan 13, 2010
 * File Name       : AutoBackUp.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.wfsc.tasks;

import java.lang.management.ManagementFactory;
import java.util.Date;

import org.apache.log4j.Logger;

import com.base.ServerBeanFactory;
import com.base.job.SimpleTask;
import com.base.log.LogUtil;
import com.sun.management.OperatingSystemMXBean;
import com.wfsc.common.bo.system.BackUpPlan;
import com.wfsc.services.system.IBackUpPlanService;

/**
 * 
 * @author timy
 * @version 1.0
 * @since Apex OssWorks 5.5 备份数据库 
 * 1.是否启用备份，启用直接进入第二步，否则直接结束 <br>
 * 2.前提条件启用数据库备份：从数据库获得备份类型和备份时间，判断是否应该备份（是否到备份时间），不备份直接结束，否则进入第三步 <br>
 * 3.前提条件备份时间到：把备份命令写入dat文件，运行bat备份数据库。最后进入第四步  <br>
 * 4.是否备份附件，备份则备份附件。最后进入第五步 5.判断是否把备份的文件上传到FTP。最后进入第六步  <br>
 * 6.是否发送邮件通知备份结果。最后结束 <br>
 */
public class AutoBackUp extends SimpleTask {
	Logger log = LogUtil.getLogger(LogUtil.SERVER);
	BackUpPlan data = null;
	// 保存数据库备份的分数
	int storeFileNum;
	//@Resource(name = "backUpPlanService")
	private IBackUpPlanService backUpPlanService = (IBackUpPlanService)ServerBeanFactory.getBean("backUpPlanService");

	@Override
	public Object execute(Object param, Date nextFireTime) {
		log.info("进入数据库备份定时任务");
		data = ((IBackUpPlanService)ServerBeanFactory.getBean("backUpPlanService")).getBackUpPlan();
		if (null != data) {
			// 启用备份
			if (data.getStartBackUp() == 1) {
				log.info("启用备份");
				storeFileNum = data.getStoreFileNum();
					Date sysDate = new Date();
					int houre = sysDate.getHours();
					int houreInBd = data.getBackUpTime();
					if (houreInBd == houre) {
						startBackUpDB();
						return null;
					} else {
						return null;
					}
				}
		} else {
			return null;
		}
		return null;
	}

	/**
	 * 开始执行备份工作和余下的任务
	 */
	public void startBackUpDB() {
		// 判断操作系统和数据库类型
		String operateSystemType = "";
		String DBType = "mysqlDB";
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		if (operatingSystemMXBean.getName().indexOf("Windows") != -1) {
			operateSystemType = "windows";
		} else {
			operateSystemType = "linux";
		}
		// 把用户自定义路径作为绝对路径用来存储备份之后打包的文件夹
		String definitionUrlByUser = "";
		definitionUrlByUser = data.getStoreURL();
		String StoreAttachmentURL = definitionUrlByUser;
		new BackUpDB().backUpDbByOperatorSystemAndDbType(operateSystemType, DBType, 
				StoreAttachmentURL, storeFileNum);
	}
}
