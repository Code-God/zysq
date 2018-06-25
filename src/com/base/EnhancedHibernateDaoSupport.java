/******************************************************************************** 
 * Create Author   : Andy Cui
 * Create Date     : Oct 13, 2009
 * File Name       : EnhancedHibernateDaoSupport.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package com.base;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.base.exception.CupidRuntimeException;


/**
 * 增强HibernateDaoSupport，提供一些通用的操作数据的方法，如果不能满足要求，请在子类中编写自己的方法。
 * 
 * @param <T> 要具体持久化的类
 * 
 * @author Apex Team
 * @version 1.0
 */
public abstract class EnhancedHibernateDaoSupport<T> extends HibernateDaoSupport {

	/**
	 * 操作实体的全名称，如：MapNode.class.getName()、MapLink.class.getName()等
	 * 
	 * @return
	 */
	protected abstract String getEntityName();

	@Resource(name="sessionFactory")    //为父类HibernateDaoSupport注入sessionFactory的值
    public void setSuperSessionFactory(SessionFactory sessionFactory){
        super.setSessionFactory(sessionFactory);
    }


	/**
	 * 条件查询实体对象的某几个属性而不返回整个实体对象，在仅仅需要实体对象的属性而不是整个实体对象时，可以提高效率。
	 * 
	 * @param selectedProps 要查询的属性名称
	 * @param conditionKeys where子句中的查询条件
	 * @param values where子句中的查询条件对应的值
	 * @return 符合条件的实体对象某几个属性的集合
	 */
	@SuppressWarnings("unchecked")
	public List<?> getEntityProperties(final String[] selectedProps, final String[] conditionKeys, final Object[] values) {
		if (ArrayUtils.isEmpty(selectedProps)
				|| (!ArrayUtils.isEmpty(conditionKeys) && !ArrayUtils.isEmpty(values) && conditionKeys.length != values.length)) {
			throw new IllegalArgumentException("Invalid arguments to execute sql query.");
		}
		StringBuilder sql = new StringBuilder("select ");
		for (int i = 0, n = selectedProps.length; i < n; i++) {
			if (i != n - 1)
				sql.append(selectedProps[i] + ",");
			else
				sql.append(selectedProps[i] + " from ");
		}
		if (conditionKeys == null) {
			sql.append(getEntityName());
		} else {
			sql.append(getEntityName() + " where ");
			for (int i = 0, n = conditionKeys.length; i < n; i++) {
				if (i != n - 1) {
					sql.append(conditionKeys[i]);
					sql.append(" = ? and ");
				} else {
					sql.append(conditionKeys[i]);
					sql.append(" = ?");
				}
			}
		}
		return getHibernateTemplate().find(sql.toString(), values);
	}

	/**
	 * 判断指定条件的实体对象是否存在
	 * 
	 * @param propertyNames
	 * @param values
	 * @return 返回true如果指定条件的对象存在，否则返回false
	 */
	@SuppressWarnings("unchecked")
	public boolean isEntityExisted(final String[] propertyNames, final Object[] values) {
		if (ArrayUtils.isEmpty(propertyNames) || ArrayUtils.isEmpty(values) || propertyNames.length != values.length)
			throw new IllegalArgumentException("Invalid arguments to execute sql query.");
		StringBuilder queryString = new StringBuilder("select id from " + getEntityName() + " where ");
		for (int i = 0, n = propertyNames.length; i < n; i++) {
			if (i != n - 1)
				queryString.append(propertyNames[i] + " = ? and ");
			else
				queryString.append(propertyNames[i] + " = ?");
		}
		List result = getHibernateTemplate().find(queryString.toString(), values);
		return CollectionUtils.isNotEmpty(result);
	}

	/**
	 * 查询所有实体
	 * 
	 * @param entityName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAllEntities() {
		return getHibernateTemplate().find("from " + getEntityName());
	}

	/**
	 * 将查询条件封装为hql查找
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntityByHql(String hql) {
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 分页查询实体对象
	 * 
	 * @param start 开始位置 - 下标从0开始
	 * @param pageSize 每页大小
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getPagingEntities(final int start, final int pageSize) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getEntityName());
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
				return criteria.list();
			}
		});
	}
	
	/**
	 * 分页根据hql查询实体对象
	 * 
	 * @param start 开始位置 - 下标从0开始
	 * @param pageSize 每页大小
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getPagingEntitiesByHql(final String hql, final int start, final int pageSize) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(pageSize);
				return query.list();
			}
		});
	}

	/**
	 * 根据实体对象的多个属性分页查询实体对象
	 * 
	 * @param propertyNames 参数名的数组
	 * @param values 与参数名对应的值
	 * @param start 分页开始位置 - 下标从0开始
	 * @param pageSize 分页尺寸
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getPagingEntitiesByPropName(final String[] propertyNames, final Object[] values, final int start,
			final int pageSize) {
		if (ArrayUtils.isEmpty(propertyNames) || ArrayUtils.isEmpty(values) || propertyNames.length != values.length) {
			throw new IllegalArgumentException("Invalid arguments to execute sql query.");
		}
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getEntityName());
				for (int i = 0; i < propertyNames.length; i++) {
					if (values[i] == null)
						criteria.add(Restrictions.isNull(propertyNames[i]));
					else if (values[i].toString().indexOf("%") != -1) {// 模糊查询,即value里直接包含%的，作为模糊查询
						criteria.add(Restrictions.like(propertyNames[i], values[i]));
					} else {
						criteria.add(Restrictions.eq(propertyNames[i], values[i]));
					}
				}
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
				return criteria.list();
			}
		});
	}

	public List<T> getPagingEntitiesByPropName(final String propertyName, final Object value, final int start,
			final int pageSize) {
		return getPagingEntitiesByPropName(new String[] { propertyName }, new Object[] { value }, start, pageSize);
	}

	/**
	 * 根据SQL的or条件查询，举个例子如下： from DeviceResource where (type = 'ROUTER' or type = 'L3_SWITCH');
	 * 
	 * <p>
	 * Notes: 请在进行or查询时调用这个方法<br>
	 * <br>
	 * Create Author : allen.wang <br>
	 * Create Date : 2009-3-18
	 * 
	 * @param start
	 * @param pageSize
	 * @param propertyName
	 * @param values
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> getPagingEntitiesByOrCondition(final int start, final int pageSize, final String propertyName,
			final Object... values) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getEntityName());
				Disjunction d = Restrictions.disjunction();
				criteria = criteria.add(d);
				for (int i = 0; i < values.length; i++)
					d.add(Restrictions.eq(propertyName, values[i]));
				criteria.setFirstResult(start);
				criteria.setMaxResults(pageSize);
				return criteria.list();
			}
		});
	}

	/**
	 * 根据主键查询实体，如果没有查到返回null
	 * 
	 * @param entityName 实体名
	 * @param id 实体ID
	 * @return 实体对象
	 */
	@SuppressWarnings("unchecked")
	public T getEntityById(Long id) {
		if (id == null)
			return null;
		return (T) (getHibernateTemplate().get(getEntityName(), id));
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return hibernate id
	 */
	public Long saveEntity(T entity) {
		return (Long) getHibernateTemplate().save(entity);
	}

	/**
	 * 批量更新实体
	 * 
	 * @param entities
	 */
	@SuppressWarnings("unchecked")
	public void saveOrUpdateAllEntities(Collection<T> entities) {
		if (entities.isEmpty())
			return;
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * 更新单个实体
	 * 
	 * @param entity
	 * 
	 */
	public void updateEntity(T entity) {
		if (entity == null)
			return;
		getHibernateTemplate().update(entity);
	}

	/**
	 * 根据id删除实体
	 * 
	 * @param id
	 */
	public void deleteEntity(long id) {
		T entity = getEntityById(id);
		if (entity == null)
			return;
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 删除单个实体
	 * 
	 * @param entity
	 * 
	 */
	public void deleteEntity(T entity) {
		if (entity == null)
			return;
		getHibernateTemplate().delete(entity);
	}

	/**
	 * 根据指定的属性名-值，删除实体
	 * 
	 * @param propertyName
	 * @param value
	 */
	public void deleteEntityByProperty(String propertyName, final Object value) {
		getHibernateTemplate().bulkUpdate("delete from " + getEntityName() + " where " + propertyName + " = ?", value);
	}

	/**
	 * 删除所有实体
	 * 
	 * @param entities
	 */
	@SuppressWarnings("unchecked")
	public void deleteAllEntities(Collection<T> entities) {
		if (entities.isEmpty())
			return;
		getHibernateTemplate().deleteAll(entities);
	}

	/**
	 * 根据实体对象的多个属性查询实体对象，举个例子如下： from DeviceResource where type = 'ROUTER' and temp = false and mgtIp = '192.168.0.8';
	 * 在这个例子中，我知道查询只会返回唯一值
	 * 
	 * <p>
	 * 在你确定查询返回唯一一个值的情况下调用这个方法，否则请使用{{@link #getEntitiesByPropNames(String[], Object[])}
	 * 
	 * @param propertyNames 匹配的属性名
	 * @param values 属性对应的值
	 * 
	 * @return 符合该特定查询条件的唯一实体
	 */
	@SuppressWarnings("unchecked")
	public T getUniqueEntityByPropNames(final String[] propertyNames, final Object[] values) {
		List<T> result = getEntitiesByPropNames(propertyNames, values);
		if (!CollectionUtils.isEmpty(result))
			return result.get(0);
		return null;
	}

	/**
	 * 根据实体对象的多个属性查询实体对象，举个例子如下： from DeviceResource where type = 'ROUTER' and temp = false;
	 * 
	 * @param propertyNames 匹配的属性名
	 * @param values 属性对应的值
	 * 
	 * @return 符合该特定查询条件的多个实体
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntitiesByPropNames(final String[] propertyNames, final Object[] values) {
		if (ArrayUtils.isEmpty(propertyNames) || ArrayUtils.isEmpty(values) || propertyNames.length != values.length) {
			throw new IllegalArgumentException("arguments is invalid.");
		}
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				for (int i = 0; i < propertyNames.length; i++) {
					String propertyName = propertyNames[i];
					if (propertyName.indexOf(".") != -1) {
						Criteria subC = null;
						String[] props = StringUtils.split(propertyName, ".");
						for (int j = 0; j < props.length; j++) {
							if (j != props.length - 1) {
								subC = c.createCriteria(props[j]);
							}
						}
						if (values[i] == null)
							subC.add(Restrictions.isNull(propertyName.substring(propertyName.indexOf(".") + 1)));
						else
							subC.add(Restrictions.eq(propertyName.substring(propertyName.indexOf(".") + 1), values[i]));
					} else {
						if (values[i] == null)
							c.add(Restrictions.isNull(propertyNames[i]));
						else if (values[i].toString().indexOf("%") != -1) {// 模糊查询,即value里直接包含%的，作为模糊查询
							c.add(Restrictions.like(propertyNames[i], values[i]));
						} else {
							c.add(Restrictions.eq(propertyNames[i], values[i]));
						}
					}
				}
				return c.list();
			}
		});
	}

	/**
	 * 根据实体对象的多个属性查询实体对象数量，举个例子如下： from DeviceResource where (type = 'ROUTER' or type = 'L3_SWITCH');<br>
	 * <br>
	 * Create Author : allen.wang <br>
	 * Create Date : 2009-3-18
	 * 
	 * @param propertyName
	 * @param values
	 * @return
	 */
	public int countEntitiesByOrCondition(final String propertyName, final Object... values) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(getEntityName());
				Disjunction d = Restrictions.disjunction();
				criteria = criteria.add(d);
				for (int i = 0; i < values.length; i++)
					d.add(Restrictions.eq(propertyName, values[i]));
				int num = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();// 取得符合条件的总数
				return new Integer(num);
			}
		});
	}

	/**
	 * 根据实体对象的多个属性查询实体对象数量，举个例子如下： from DeviceResource where type = 'ROUTER' and temp = false;
	 * 
	 * @param start 开始位置
	 * @param pageSize 每页大小
	 * @param propertyNames 匹配的属性名
	 * @param values 属性对应的值
	 * 
	 * @return 符合该特定查询条件的多个实体
	 */
	@SuppressWarnings("unchecked")
	public int countEntitiesByPropNames(final String[] propertyNames, final Object[] values) {
		if (ArrayUtils.isEmpty(propertyNames) || ArrayUtils.isEmpty(values) || propertyNames.length != values.length) {
			throw new IllegalArgumentException("arguments is invalid.");
		}
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				for (int i = 0; i < propertyNames.length; i++) {
					String propertyName = propertyNames[i];
					if (propertyName.indexOf(".") != -1) {
						Criteria subC = null;
						String[] props = StringUtils.split(propertyName, ".");
						for (int j = 0; j < props.length; j++) {
							if (j != props.length - 1) {
								subC = c.createCriteria(props[j]);
							}
						}
						if (values[i] == null)
							subC.add(Restrictions.isNull(propertyName.substring(propertyName.indexOf(".") + 1)));
						else
							subC.add(Restrictions.eq(propertyName.substring(propertyName.indexOf(".") + 1), values[i]));
					} else {
						if (values[i] == null)
							c.add(Restrictions.isNull(propertyNames[i]));
						else
							c.add(Restrictions.eq(propertyNames[i], values[i]));
					}
				}
				int num = ((Integer) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();// 取得符合条件的总数
				return new Integer(num);
			}
		});
	}

	/**
	 * 根据实体对象的属性查询实体对象总数<br>
	 * 
	 * <pre>
	 * 方法 : {{@link #countEntitiesByPropNames(String[], Object[]) 的重载&lt;/pre&gt;
	 * <br>
	 * <br>
	 * Create Author : allen.wang
	 * <br>
	 * Create Date   : 2009-3-13
	 * 
	 * &#064;param propertyNames
	 * 					参数名
	 * &#064;param values
	 * 					与参数名对应的值
	 * &#064;return
	 * 					总数
	 * 
	 */
	public int countEntitiesByPropNames(final String propertyName, final Object value) {
		return countEntitiesByPropNames(new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * 根据实体对象的多个属性分页查询实体对象，举个例子如下： from DeviceResource where type = 'ROUTER' and temp = false;
	 * 
	 * @param start 开始位置
	 * @param pageSize 每页大小
	 * @param propertyNames 匹配的属性名
	 * @param values 属性对应的值
	 * 
	 * @return 符合该特定查询条件的多个实体
	 */
	@SuppressWarnings("unchecked")
	public List<T> getPagingEntitiesByPropNames(final int start, final int pageSize, final String[] propertyNames,
			final Object[] values) {
		if (ArrayUtils.isEmpty(propertyNames) || ArrayUtils.isEmpty(values) || propertyNames.length != values.length) {
			throw new IllegalArgumentException("arguments is invalid.");
		}
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				c.setFirstResult(start);
				c.setMaxResults(pageSize);
				for (int i = 0; i < propertyNames.length; i++) {
					String propertyName = propertyNames[i];
					if (propertyName.indexOf(".") != -1) {
						Criteria subC = null;
						String[] props = StringUtils.split(propertyName, ".");
						for (int j = 0; j < props.length; j++) {
							if (j != props.length - 1) {
								subC = c.createCriteria(props[j]);
							}
						}
						if (values[i] == null)
							subC.add(Restrictions.isNull(propertyName.substring(propertyName.indexOf(".") + 1)));
						else
							subC.add(Restrictions.eq(propertyName.substring(propertyName.indexOf(".") + 1), values[i]));
					} else {
						if (values[i] == null)
							c.add(Restrictions.isNull(propertyNames[i]));
						else if (values[i].toString().indexOf("%") != -1) {// 模糊查询,即value里直接包含%的，作为模糊查询
							c.add(Restrictions.like(propertyNames[i], values[i]));
						} else {
							c.add(Restrictions.eq(propertyNames[i], values[i]));
						}
					}
				}
				return c.list();
			}
		});
	}

	/**
	 * 根据实体对象的某个属性查询实体对象，举个例子如下： from DeviceResource where type = 'ROUTER';
	 * 
	 * @param propertyName 实体对象的属性名
	 * @param value 属性对应的值
	 * 
	 * @return 符合该特定查询条件的多个实体
	 */
	public List<T> getEntitiesByOneProperty(final String propertyName, final Object value) {
		return getEntitiesByPropNames(new String[] { propertyName }, new Object[] { value });
	}

	/**
	 * 根据实体对象的某个属性查询唯一的实体对象
	 * 
	 * @param propertyName 实体对象的属性名
	 * @param value 属性对应的值
	 * 
	 * @return 符合该特定查询条件的唯一实体，如果没有找到，则返回null
	 */
	public T getUniqueEntityByOneProperty(final String propertyName, final Object value) {
		List<T> result = getEntitiesByOneProperty(propertyName, value);
		if (CollectionUtils.isNotEmpty(result))
			return result.get(0);
		return null;
	}

	/**
	 * 根据SQL的or条件查询，举个例子如下： from DeviceResource where (type = 'ROUTER' or type = 'L3_SWITCH');
	 * 
	 * <p>
	 * Notes: 请在进行or查询时调用这个方法
	 * 
	 * @param propertyName 属性名
	 * @param values 属性的可能值
	 * 
	 * @return 符合条件的记录
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntitiesByOrCondition(final String propertyName, final Object... values) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				Disjunction d = Restrictions.disjunction();
				c = c.add(d);
				for (int i = 0; i < values.length; i++)
					d.add(Restrictions.eq(propertyName, values[i]));
				return c.list();
			}
		});
	}

	/**
	 * 删除所有指定ID的对象
	 * 
	 * @param ids
	 */
	public void deletAllEntities(List<Long> ids) {
		StringBuffer sql = new StringBuffer("delete from " + getEntityName() + " where ");
		for (int i = 0; i < ids.size(); i++) {
			sql.append("id = ");
			sql.append(ids.get(i));
			if (i != (ids.size() - 1))
				sql.append(" or ");
		}
		getHibernateTemplate().bulkUpdate(sql.toString());
	}

	/**
	 * 删除所有的对象
	 */
	public void deleteAllEntities() {
		getHibernateTemplate().bulkUpdate("delete from " + getEntityName());
	}

	/**
	 * 根据实体对象的属性组成的查询条件查询的通用方法
	 * 
	 * @param rules 实体对象属性组成的rule
	 * @return 符合该特定查询条件的多个实体
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntitiesByDaoCriterias(final List<DaoCriteria> rules) {
		if (CollectionUtils.isEmpty(rules)) {
			throw new IllegalArgumentException("arguments is invalid.");
		}
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				for (DaoCriteria daoCriteria : rules) {
					DaoUtil.convertCriteria(c, daoCriteria);
				}
				return c.list();
			}
		});
	}

	public int getEntitiesNumber(final List<DaoCriteria> rules) {
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				for (DaoCriteria daoCriteria : rules) {
					DaoUtil.convertCriteria(c, daoCriteria);
				}
				int num = ((Integer) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();// 取得符合条件的总数
				return new Integer(num);
			}
		});
	}

	// public Object delete
	@SuppressWarnings("unchecked")
	public List<T> getEntitiesByCriterion(final Criterion criterion) {
		return (List<T>) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria c = session.createCriteria(getEntityName());
				c.add(criterion);
				return c.list();
			}
		});
	}

	@SuppressWarnings("unchecked")
	public int count(List<DaoCriteria> rules) {
		String condition = convertCriteriaToSql(rules);
		String sql = "select count(*) from " + getEntityName();
		if (!StringUtils.isEmpty(condition)) {
			sql = sql + " where " + condition;
		}
		List<Number> list = getHibernateTemplate().find(sql);
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	public int count() {
		List<Number> list = getHibernateTemplate().find("select count(*) from " + getEntityName());
		return Integer.parseInt(list.get(0).toString());
	}

	public void deleteQueryObjects(List<DaoCriteria> rules) {
		String condition = convertCriteriaToSql(rules);
		String sql = "delete from " + getEntityName();
		if (!StringUtils.isEmpty(condition)) {
			sql = sql + " where " + condition;
		}
		getHibernateTemplate().bulkUpdate(sql);
	}

	/*
	 * 将Criteria对象转化为响应的sql语句
	 */
	@SuppressWarnings("unchecked")
	public String convertCriteriaToSql(List<DaoCriteria> rules) {
		String condition = "";
		for (int i = 0; i < rules.size(); i++) {
			DaoCriteria daoCriteria = rules.get(i);
			String propertyName = daoCriteria.getPropertyName();
			Object value = daoCriteria.getValue();
			if (null == propertyName || propertyName.equals("id"))
				continue;
			if (value instanceof List) {
				List list = (List) value;
				String newCondition = "";
				for (int j = 0; j < list.size(); j++) {
					Object object = list.get(j);
					if (j == 0) {
						newCondition = propertyName + " = '" + object + "'";
					} else {
						newCondition = newCondition + " or " + propertyName + " = '" + object + "'";
					}
				}
				if (StringUtils.isEmpty(condition))
					condition = newCondition;
				else
					condition = condition + " and " + newCondition;
			} else {
				if (i == 0)
					condition = propertyName + " = '" + value + "'";
				else
					condition = condition + " and " + propertyName + " = '" + value + "'";
			}
		}
		return condition;
	}

	/**
	 * 根据某个属性判断新增记录是否与以前的冲突了，不区分大小写 propertyName:bo对象中的属性名 propertyValue:属性名对应的属性值
	 * 
	 * @return 如果冲突了，返回true,否则返回false
	 */
	@SuppressWarnings("unchecked")
	public boolean isConflictOneProperty(final String propertyName, final String propertyValue) {
		List<T> objects = getHibernateTemplate().find("from " + getEntityName() + " where upper(" + propertyName + ") = ?",
				propertyValue.toUpperCase());
		if (CollectionUtils.isEmpty(objects))
			return false;
		else
			return true;
	}

	/**
	 * 在更新对象时，先判断该对象还存不存在，如果不存在则抛出异常图示用户
	 * 
	 * @param id ：对象Id
	 * @param obj ：整个对象
	 */
	public void updateOceanObject(long id, Object obj) {
		T entity = getEntityById(id);
		if (entity == null)
			throw new CupidRuntimeException("该记录已被其他用户删除");
		else
			getHibernateTemplate().merge(obj);
	}

	/**
	 * 根据Id判断对象是否存在,如果存在返回true，不存在返回false
	 * 
	 * @param id ：对象Id
	 */
	public boolean isObjExist(long id) {
		T entity = getEntityById(id);
		if (entity == null)
			return false;
		else
			return true;
	}

	/**
	 * add by jonim 根据HQL进行分页查询
	 * 
	 * @param hql
	 * @param firstRow
	 * @param maxRow
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List findList4Page(final String hql, final int firstRow, final int maxRow) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createQuery(hql);
				q.setFirstResult(firstRow);
				q.setMaxResults(maxRow);
				return q.list();
			}
		});
	}
	
	public Integer executeUpdateHql(final String hql)throws CupidRuntimeException{
		try {
			return (Integer) this.getHibernateTemplate().execute(new HibernateCallback(){
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery(hql);
					return Integer.valueOf(query.executeUpdate());
				}});
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CupidRuntimeException("executeUpdate is fail! hql:"+hql);
		}
	}
	
	@SuppressWarnings("unchecked")
	public int countByHql(String hql) {
		String countHql = prepareCountHql(hql);
		List<Number> list = getHibernateTemplate().find(countHql);
		return Integer.parseInt(list.get(0).toString());
	}
	
	
	private String prepareCountHql(String hql) {
		// select子句与order by子句会影响count查询,进行简单的排除.
		String fromHql = "from " + StringUtils.substringAfter(hql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");
		String countHql = "select count(*) " + fromHql;
		return countHql;
	}
	
	public void saveOrUpdateEntity(T entity){
		this.getHibernateTemplate().saveOrUpdate(entity);
	}
	@SuppressWarnings("unchecked")
	public int countByHqlWithParama(String hql,final Map<String,Object> dataMap){
		List list = prepareCountByHqlWithParama(hql,dataMap);
		return Integer.valueOf(list.get(0).toString());
	}
	@SuppressWarnings("unchecked")
	private List prepareCountByHqlWithParama(String hql,final Map<String,Object> dataMap) {
		final String countHql = prepareCountHql(hql);
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createQuery(countHql);
				for(String key : dataMap.keySet()){
					Object obj = dataMap.get(key);
					if(obj instanceof java.util.Date) {
						q.setDate(key, (java.util.Date)obj);
					}else if(obj instanceof java.lang.String){
						q.setString(key, (java.lang.String)obj);
					}else if(obj instanceof java.lang.Integer){
						q.setInteger(key, (java.lang.Integer)obj);
					}else if(obj instanceof java.lang.Long){
						q.setLong(key, (java.lang.Long)obj);
					}else if(obj instanceof java.lang.Float){
						q.setFloat(key, (java.lang.Float)obj);
					}else if(obj instanceof java.lang.Double){
						q.setDouble(key, (java.lang.Double)obj);
					}else if(obj instanceof java.lang.Boolean){
						q.setBoolean(key, (java.lang.Boolean)obj);
					}else{
						q.setEntity(key, obj);
					}
				}
				return q.list();
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	public List findList4PageWithParama(final String hql, final int firstRow,final int maxRow,final Map<String,Object> dataMap) {
		return this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createQuery(hql);
				for(String key : dataMap.keySet()){
					Object obj = dataMap.get(key);
					if(obj instanceof java.util.Date) {
						q.setDate(key, (java.util.Date)obj);
					}else if(obj instanceof java.lang.String){
						q.setString(key, (java.lang.String)obj);
					}else if(obj instanceof java.lang.Integer){
						q.setInteger(key, (java.lang.Integer)obj);
					}else if(obj instanceof java.lang.Long){
						q.setLong(key, (java.lang.Long)obj);
					}else if(obj instanceof java.lang.Float){
						q.setFloat(key, (java.lang.Float)obj);
					}else if(obj instanceof java.lang.Double){
						q.setDouble(key, (java.lang.Double)obj);
					}else if(obj instanceof java.lang.Boolean){
						q.setBoolean(key, (java.lang.Boolean)obj);
					}else{
						q.setEntity(key, obj);
					}
				}
				q.setFirstResult(firstRow);
				q.setMaxResults(maxRow);
				return q.list();
			}
		});
	}
}
