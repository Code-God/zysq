package com.wfsc.services.ad;

import java.util.List;

import com.base.util.Page;
import com.wfsc.common.bo.ad.AdvConfig;

/**
 * 广告
 * 
 * @author Xiaojiapeng
 * 
 */
public interface IAdService {

	/**
	 * 根据广告类型获取广告
	 * 
	 * @param adType
	 * @return
	 */
	public List<AdvConfig> queryByType(int type);
	
	/**
	 * 分页查询广告
	 * @param orgId - 分销商ID
	 * @return
	 */
	public Page<AdvConfig> queryAllAds(Page<AdvConfig> page, int type, Long orgId);
	
	/**
	 * 根据ID获取广告
	 * @param id
	 * @return
	 */
	public AdvConfig getAdById(long id);
	
	/**
	 * 保存或者更新广告
	 * @param advConfig
	 * @param orgId 
	 */
	public void saveOrUpdateAd(AdvConfig advConfig, Long orgId);
	
	/**
	 * 删除广告
	 * @param ids
	 */
	public void deleteAdByIds(List<Long> ids);

	
	public List<AdvConfig> getPptsByOrg(Long orgId);
}
