package com.wfsc.services.system;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfsc.common.bo.system.SeoConfig;
import com.wfsc.daos.system.SeoConfigDao;

@Service("seoConfigService")
public class SeoConfigServiceImpl implements ISeoConfigService {
	
	@Autowired
	private SeoConfigDao seoConfigDao;
	
	private SeoConfig seoConf;

	@Override
	public void saveSeoConfig(SeoConfig seoConfig) {
		List<SeoConfig> seoConfigs = seoConfigDao.getAllEntities();
		SeoConfig seoConf = null;
		if(CollectionUtils.isNotEmpty(seoConfigs)){
			seoConf = seoConfigs.get(0);
		}
		if(seoConf != null)
			seoConfig.setId(seoConf.getId());
		
		seoConfigDao.saveOrUpdateEntity(seoConfig);
		
		seoConf = seoConfig;
	}

	@Override
	public SeoConfig getSeoConfig() {
		//如果有保存的，直接返回，不从数据库获取
		if(seoConf != null){
			return seoConf;
		}
		List<SeoConfig> seoConfigs = seoConfigDao.getAllEntities();
		SeoConfig seoConfig = null;
		if(CollectionUtils.isNotEmpty(seoConfigs)){
			seoConfig = seoConfigs.get(0);
			seoConf = seoConfig;
		}
		return seoConfig;
	}

}
