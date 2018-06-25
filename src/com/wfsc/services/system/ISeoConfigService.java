package com.wfsc.services.system;

import com.wfsc.common.bo.system.SeoConfig;

public interface ISeoConfigService {
	
	/**
	 * 保存SEO设置
	 * @param seoConfig
	 */
	public void saveSeoConfig(SeoConfig seoConfig);
	
	/**
	 * 获取SEO设置
	 * @return
	 */
	public SeoConfig getSeoConfig();

}
