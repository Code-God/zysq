package com.wfsc.daos.system;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.system.SeoConfig;

@Repository("seoConfigDao")
public class SeoConfigDao extends EnhancedHibernateDaoSupport<SeoConfig> {

	@Override
	protected String getEntityName() {
		return SeoConfig.class.getName();
	}

}
