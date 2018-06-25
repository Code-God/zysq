package com.wfsc.daos.shopcart;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.shopcart.ShopCart;

/**
 * 购物车
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Repository("shopCartDao")
public class ShopCartDao extends EnhancedHibernateDaoSupport<ShopCart> {

	@Override
	protected String getEntityName() {
		return ShopCart.class.getName();
	}
	
	public List<ShopCart> queryAll() {
		return getAllEntities();
	}
	
	/**
	 * 查询用户的购物车
	 * @param userId
	 * @return
	 */
	public List<ShopCart> getShopCartByUserId(long userId){
		return getHibernateTemplate().find("from ShopCart where userId = ?", userId);
	}
	
	/**
	 * 根据用户id删除该用户的所有购物车数据
	 * @param userId
	 */
	public void deleteShopCartByUserId(final long userId){
		getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from ShopCart where userId = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				query.executeUpdate();
				return null;
			}
			
		});
	}

	/**
	 * 根据用户ID和产品编码删除一个购物记录
	 * @param userId 用户ID
	 * @param prdCode 产品编码
	 */
	public void deleteByUserIdAndPrdCode(final Long userId, final String prdCode){
		getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from ShopCart where userId = ? and prdCode = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				query.setString(1, prdCode);
				query.executeUpdate();
				return null;
			}
			
		});
	}
	
	public ShopCart getScByUserIdAndPrdCode(final long userId, final String prdCode){
		return getHibernateTemplate().execute(new HibernateCallback<ShopCart>(){

			@Override
			public ShopCart doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from ShopCart where userId = ? and prdCode = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, userId);
				query.setString(1, prdCode);
				List<ShopCart> scs = query.list();
				if(CollectionUtils.isNotEmpty(scs)){
					return scs.get(0);
				}
				return null;
			}
			
		});
	}
}