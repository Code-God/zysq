/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : UserDao.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/10:24:57 AM
 */
package com.wfsc.daos.system;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.system.BackUpPlan;

/**
 * @since cupid
 * 
 */
@Repository("backUpPlanDao")
public class BackUpPlanDao extends EnhancedHibernateDaoSupport<BackUpPlan> {

	@Override
	protected String getEntityName() {
		return BackUpPlan.class.getName();
	}

}