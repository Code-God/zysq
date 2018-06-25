package com.wfsc.daos.email;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.system.EmailServerConfig;

@Repository("emailServerDao")
public class EmailServerDao extends EnhancedHibernateDaoSupport<EmailServerConfig> {

	@Override
	protected String getEntityName() {
		return EmailServerConfig.class.getName();
	}

}
