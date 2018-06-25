package com.base.job;

import java.util.Date;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;

/**
 * 用来重新启动任务的
 *
 * @author JoveDeng
 * @version 1.0
 * @since Apex OssWorks 5.5
 */
public class AutoRefreshJob extends SimpleTask {
	private static Logger logg = LogUtil.getLogger(AutoRefreshJob.class.getName());
	@Override
	public Object execute(Object param, Date nextFireTime) {
		this.execute();
		return null;
	}
	
	public void execute() {
		if(JobRunner.needRefresh()){
			logg.info("重新启动所有轮询任务...");
			JobRunner.run();
		}
	}

}

