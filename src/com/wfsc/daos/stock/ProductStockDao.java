package com.wfsc.daos.stock;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.product.ProductStock;

@Repository("stockDao")
public class ProductStockDao extends EnhancedHibernateDaoSupport<ProductStock> {

	@Override
	protected String getEntityName() {
		return ProductStock.class.getName();
	}
	
	/**
	 * 根据城市编码删除该城市下面的所有商品的库存数据
	 * @param cityCode
	 * @return 删除的数据量
	 */
	public int deletePrdStockByCityCode(final int cityCode){
		return getHibernateTemplate().execute(new HibernateCallback<Integer>(){

			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from ProductStock where cityCode = ?";
				Query query = session.createQuery(hql);
				query.setInteger(0, cityCode);
				return query.executeUpdate();
			}
			
		});
	}
	
	public Page<ProductStock> getProductStockByPrdCode(Page<ProductStock> page, String prdCode){
		StringBuffer hql = new StringBuffer("from ProductStock where prdCode='" + prdCode + "'");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<ProductStock> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	
	/**
	 * 更新库存数
	 * @param cityCode 城市编码
	 * @param prdCode 产品编码
	 * @param stockNum 减少库存数
	 */
	public void reduceStock(Integer cityCode, String prdCode, int count){
		String hql = "update ProductStock set stock = stock - "+ count +" where prdCode = '" + prdCode +"' and cityCode = " + cityCode;
		super.executeUpdateHql(hql);
	}
}
