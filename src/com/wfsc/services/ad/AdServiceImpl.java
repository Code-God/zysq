package com.wfsc.services.ad;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.daos.ad.AdDao;

@Service("adService")
@SuppressWarnings("unchecked")
public class AdServiceImpl implements IAdService {

	Logger log = LogUtil.getLogger(LogUtil.SERVER);

	@Resource
	private AdDao adDao;

	@Override
	public List<AdvConfig> queryByType(int type) {
		List<AdvConfig> lists = adDao.getHibernateTemplate().find("from AdvConfig where adType = ? order by id", type);
		if (CollectionUtils.isEmpty(lists)) {
			return null;
		}else{
			//移除过期的广告
			Iterator<AdvConfig> it = lists.iterator();
			while(it.hasNext()){
				AdvConfig advConfig = it.next();
				if(advConfig.getDueDate()!= null && advConfig.isExpire()){
					it.remove();
				}
			}
		}
		return lists;
	}

	@Override
	public Page<AdvConfig> queryAllAds(Page<AdvConfig> page, int type, Long orgId) {
		return adDao.queryAdsForPage(page, type, orgId);
	}

	@Override
	public void deleteAdByIds(List<Long> ids) {
		String s = this.getClass().getResource("/").getPath();
		String context = s.substring(1, s.indexOf("WEB-INF"));
		File f = null;
		for (Long id : ids) {
			AdvConfig ad = adDao.getEntityById(id);
			f = new File(context + ad.getPicUrl());
			if(f.exists()){
				f.delete();
			}
		}
		adDao.deletAllEntities(ids);
	}

	@Override
	public AdvConfig getAdById(long id) {
		return adDao.getEntityById(id);
	}

	@Override
	public void saveOrUpdateAd(AdvConfig advConfig, Long orgId) {
		if(advConfig.getId() != null && advConfig.getId() != 0){
			AdvConfig ad = getAdById(advConfig.getId());
			if(ad == null)
				throw new CupidRuntimeException("广告不存在或者已被删除");
			else{
				//编辑不更新图片路径
				advConfig.setPicUrl(ad.getPicUrl());
			}
		}
		//普通广告最多允许存在三个
		int type = advConfig.getAdType();
		int count = adDao.countByHql("select count(*) from AdvConfig where adType = " + type + " and orgId=" + orgId);
		if(advConfig.getId() == null && type == 1 && count>= 4){
			throw new CupidRuntimeException("普通广告最多允许配置10个");
		}
		adDao.saveOrUpdateEntity(advConfig);
	}

	@Override
	public List<AdvConfig> getPptsByOrg(Long orgId) {
		return adDao.getEntitiesByOneProperty("orgId", orgId);
	}

}
