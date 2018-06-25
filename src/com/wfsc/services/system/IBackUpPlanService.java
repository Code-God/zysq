package com.wfsc.services.system;

import com.wfsc.common.bo.system.BackUpPlan;

/**
 * 系统权限相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IBackUpPlanService {
	public BackUpPlan getBackUpPlan();
	public void saveOrUpdateBackUpPlan(BackUpPlan backUpPlan);
	
}
