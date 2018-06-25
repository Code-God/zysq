package com.wfsc.daos.ad;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.ad.AdvConfig;

/**
 * 广告
 * 
 * @author Xiaojiapeng
 * 
 */
@Repository("adDao")
@SuppressWarnings("unchecked")
public class AdDao extends EnhancedHibernateDaoSupport<AdvConfig> {

	/**
	 * 根据广告类型获取广告
	 * 
	 * @param adType
	 * @return
	 */
	public List<AdvConfig> queryByType(int type) {
		List<AdvConfig> lists = getHibernateTemplate().find("from AdvConfig where adType = ? order by id", type);
		if (CollectionUtils.isEmpty(lists)) {
			return null;
		}
		return lists;
	}

	public long addUser(AdvConfig o) {
		getHibernateTemplate().saveOrUpdate(o);
		return o.getId();
	}

	@Override
	protected String getEntityName() {
		return AdvConfig.class.getName();
	}
	
	/**
	 * 分页查询广告
	 * @param page
	 * @return
	 */
	public Page<AdvConfig> queryAdsForPage(Page<AdvConfig> page, int type, Long orgId){
		StringBuffer hql = new StringBuffer("from AdvConfig p where 1=1 ");
		if(type != 0)
			hql.append(" and adType=" + type);
		if(orgId != null){
			hql.append(" and orgId=" + orgId);
		}
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<AdvConfig> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	
}