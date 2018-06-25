package com.wfsc.daos.product;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.product.ProductSold;

@Repository("productSoldDao")
@SuppressWarnings("unchecked")
public class ProductSoldDao extends EnhancedHibernateDaoSupport<ProductSold> {

	@Override
	protected String getEntityName() {
		return ProductSold.class.getName();
	}
	
	public Map<String, Integer> getTopProductSale(final Date startTime, final Date endTime, final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<Map<String, Integer>>(){
			@Override
			public Map<String, Integer> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select prdCode, sum(sales) from ProductSold where saleDate > ? and saleDate <= ? group by prdCode order by sales desc";
				Query query = session.createQuery(hql);
				query.setDate(0, startTime);
				query.setDate(1, endTime);
				query.setFirstResult(0);
				query.setMaxResults(limit);
				List<Object[]> list = query.list();
				if(CollectionUtils.isNotEmpty(list)){
					Map<String, Integer> result = new LinkedHashMap<String, Integer>();
					for(Object[] obj : list){
						result.put(obj[0].toString(), Integer.parseInt(obj[1] + ""));
					}
					return result;
				}
				return null;
			}
			
		});
	}

}
