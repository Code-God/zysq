/*
 * Copyright 2008 Tekview Technology Co.,Ltd. All rights reserved.
 * 
 * Project  : Apex Ocean
 * Filename : UserDao.java
 * Create   : wenjun.cui@tekview.com, Jan 30, 2008/10:24:57 AM
 */
package com.wfsc.daos.pay;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.order.Consume;

/**
 * 消费记录DAO
 * @since cupid
 * 
 * @author jacky.wang
 */
@Repository("consumeDao")
public class ConsumeDao extends EnhancedHibernateDaoSupport<Consume> {


	@Override
	protected String getEntityName() {
		return Consume.class.getName();
	}

	
}