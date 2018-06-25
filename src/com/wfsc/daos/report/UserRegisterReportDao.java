package com.wfsc.daos.report;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import model.bo.auth.Org;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.report.UserRegisterReport;

@Repository("userRegisterReportDao")
public class UserRegisterReportDao extends EnhancedHibernateDaoSupport<UserRegisterReport> {

	@Override
	protected String getEntityName() {
		return UserRegisterReport.class.getName();
	}
	
	/**
	 * 根据年份 月份 第几周来查询报表
	 * @param year
	 * @param month
	 * @param week
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserRegisterReport getReportByYMW(int year, int month, int week){
		List<UserRegisterReport> reports = getHibernateTemplate().find("from UserRegisterReport where year = ? and month = ? and week = ?", year, month, week);
		if(CollectionUtils.isNotEmpty(reports)){
			return reports.get(0);
		}
		return null;
	}
	
	/**
	 * 获取年报表数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getYearData(final Org org){
		return getHibernateTemplate().execute(new HibernateCallback<Map<Integer, Integer>>(){
			@Override
			public Map<Integer, Integer> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select year, sum(regCount) from UserRegisterReport where orgId="+ org.getId() +" group by year order by year asc";
				Query query = session.createQuery(hql);
				List<Object[]> list = query.list();
				if(CollectionUtils.isNotEmpty(list)){
					Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
					for(Object[] obj : list){
						result.put(Integer.valueOf(obj[0] + ""), Integer.valueOf(obj[1] + ""));
					}
					return result;
				}
				return null;
			}
			
		});
	}
	
	/**
	 * 获取指定年份的月报表数据
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getMonthData(final int year, final Org org){
		return getHibernateTemplate().execute(new HibernateCallback<Map<Integer, Integer>>(){
			@Override
			public Map<Integer, Integer> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select month, sum(regCount) from UserRegisterReport where year = ? and orgId=? group by month order by month asc";
				Query query = session.createQuery(hql);
				query.setInteger(0, year);
				query.setLong(1, org.getId());
				List<Object[]> list = query.list();
				if(CollectionUtils.isNotEmpty(list)){
					Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
					for(Object[] obj : list){
						result.put(Integer.valueOf(obj[0] + ""), Integer.valueOf(obj[1] + ""));
					}
					return result;
				}
				return null;
			}
			
		});
	}
	
	/**
	 * 获取指定年份的周报表数据
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getWeekData(final int year, final Org org){
		return getHibernateTemplate().execute(new HibernateCallback<Map<Integer, Integer>>(){

			@Override
			public Map<Integer, Integer> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select week, regCount from UserRegisterReport where year = ? and org=? order by week asc";
				Query query = session.createQuery(hql);
				query.setInteger(0, year);
				query.setLong(0, org.getId());
				List<Object[]> list = query.list();
				if(CollectionUtils.isNotEmpty(list)){
					Map<Integer, Integer> result = new TreeMap<Integer, Integer>();
					for(Object[] obj : list){
						result.put(Integer.valueOf(obj[0] + ""), Integer.valueOf(obj[1] + ""));
					}
					return result;
				}
				return null;
			}
			
		});
	}

}
