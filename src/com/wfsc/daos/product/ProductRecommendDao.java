package com.wfsc.daos.product;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.product.ProductRecommend;

/**
 * 新品特惠推荐数据访问
 * 
 * @author Xiaojiapeng
 * 
 */
@Repository("productRecommendDao")
public class ProductRecommendDao extends EnhancedHibernateDaoSupport<ProductRecommend> {

	public List<ProductRecommend> queryAll() {
		return getAllEntities();
	}

	@Override
	protected String getEntityName() {
		return ProductRecommend.class.getName();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductRecommend> queryRecommendByType(int type){
		String hql = "from ProductRecommend where type = ?";
		return getHibernateTemplate().find(hql, type);
	}
	
	public int countRecommendByType(int type){
		return countByHql("select count(*) from ProductRecommend where type = " + type);
	}
	
	public ProductRecommend getRecommendByTypeAndPrdCode(int type, String prdCode){
		String hql = "from ProductRecommend where type = ? and product.prdCode = ?";
		List<ProductRecommend> result = getHibernateTemplate().find(hql, type, prdCode);
		if(CollectionUtils.isNotEmpty(result)){
			return result.get(0);
		}
		return null;
	}
}