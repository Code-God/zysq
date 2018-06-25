/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Ocean
 * Filename : RoundJobParam.java
 * Create   : jimsu@tekview.com, Jan 25, 2008/9:58:35 PM
 */
package com.base.job;

import org.quartz.SimpleTrigger;

/**
 * 定期轮询任务参数
 * @author tekview
 *
 */
public class RoundJobParam {
	private Object jobParam;// SimepleTask的参数
	private long inteval; // 毫秒,任务执行的间隔时间
	private int repeatCount = SimpleTrigger.REPEAT_INDEFINITELY; // 重复执行次数

	public RoundJobParam() {
	}

	public RoundJobParam(long inteval) {
		this.inteval = inteval;
	}

	public long getInteval() {
		return inteval;
	}

	public void setInteval(long inteval) {
		this.inteval = inteval;
	}

	public Object getJobParam() {
		return jobParam;
	}

	public void setJobParam(Object jobParam) {
		this.jobParam = jobParam;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

}
