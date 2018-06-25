/*
 *Copyright (C) 2008 Tekview Co.,Ltd. All rights reserved.
 *
 *Project  : Apex Ocean
 *FileName : DaoUtil.java
 *
 *Created  : version(5.0), jimsu@tekview.com, Jan 31, 2008/1:36:55 PM
 */
package com.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.base.exception.CupidRuntimeException;

/**
 * @author jimsu
 * 
 */
public class DaoUtil {

	private static final int MYSQL = 0;

	private static final int ORACLE = 1;

	private static final int MSSQL = 2;

	private static final int IBM_DB2 = 3;

	private static final char SPLITER = ';';

	private static int getCurrentUsedDatabaseType() {
		LocalSessionFactoryBean sessionFactoryBean = (LocalSessionFactoryBean) ServerBeanFactory.getBean("&sessionFactory");
		String databaseDialect = sessionFactoryBean.getConfiguration().getProperty("hibernate.dialect");
		if ("org.hibernate.dialect.SQLServerDialect".equals(databaseDialect))
			return DaoUtil.MSSQL;
		else if ("org.hibernate.dialect.MySQLInnoDBDialect".equals(databaseDialect))
			return DaoUtil.MYSQL;
		else if ("org.hibernate.dialect.DB2Dialect".equals(databaseDialect))
			return DaoUtil.IBM_DB2;
		else if ("org.hibernate.dialect.OracleDialect".equals(databaseDialect))
			return DaoUtil.ORACLE;
		else
			throw new CupidRuntimeException("The database: " + databaseDialect + " used is not currently supported.");
	}

	public static boolean isUseMysql() {
		return getCurrentUsedDatabaseType() == DaoUtil.MYSQL;
	}

	public static boolean isUseOracle() {
		return getCurrentUsedDatabaseType() == DaoUtil.ORACLE;
	}

	public static boolean isUseDB2() {
		return getCurrentUsedDatabaseType() == DaoUtil.IBM_DB2;
	}

	public static boolean isUseSqlServer() {
		return getCurrentUsedDatabaseType() == DaoUtil.MSSQL;
	}

	/**
	 * 组装多字段过滤的查询条件,多条件是个and关系
	 * <p>
	 * 将我们定义的条件转换为hibernate规定的条件列表
	 * 
	 * @param criteria hibernate的条件
	 * @param daocriteria DaoCriteria条件列表
	 * 
	 * @return hibernate条件列表
	 */
	public static Criteria getQueryOperatorEnum(Criteria criteria, List daocriteria) {
		for (Iterator it = daocriteria.iterator(); it.hasNext();) {
			DaoCriteria daoCriteria = (DaoCriteria) it.next();
			String operator = daoCriteria.getOperator();
			if (QueryOperatorEnum.LESS.getName().equals(operator)) {
				criteria.add(Restrictions.lt(daoCriteria.getPropertyName(), daoCriteria.getValue()));
			} else if (QueryOperatorEnum.BIGGER.getName().equals(operator)) {
				criteria.add(Restrictions.gt(daoCriteria.getPropertyName(), daoCriteria.getValue()));
			} else if (QueryOperatorEnum.EQUAL.getName().equals(operator)) {
				criteria.add(Restrictions.eq(daoCriteria.getPropertyName(), daoCriteria.getValue()));
			} else if (QueryOperatorEnum.LESS_EQUAL.getName().equals(operator)) {
				criteria.add(Restrictions.le(daoCriteria.getPropertyName(), daoCriteria.getValue()));
			} else if (QueryOperatorEnum.BIGGER_EQUAL.getName().equals(operator)) {
				criteria.add(Restrictions.ge(daoCriteria.getPropertyName(), daoCriteria.getValue()));
			} else if (QueryOperatorEnum.NOT_EQUAL.getName().equals(operator)) {
				criteria.add(Restrictions.ne(daoCriteria.getPropertyName(), daoCriteria.getValue()));
			} else if (QueryOperatorEnum.ISNULL.getName().equals(operator)) {
				criteria.add(Restrictions.isNull(daoCriteria.getPropertyName()));
			} else if (QueryOperatorEnum.ISNOTNULL.getName().equals(operator)) {
				criteria.add(Restrictions.isNotNull(daoCriteria.getPropertyName()));
			} else if (QueryOperatorEnum.IN.getName().equals(operator)) {
				criteria.add(Restrictions.in(daoCriteria.getPropertyName(), (Collection) daoCriteria.getValue()));
			} else if (QueryOperatorEnum.LIKE.getName().equals(operator)) {
				Object value = daoCriteria.getValue();
				if (value instanceof String) {
					String oldValue = (String) value;
					value = oldValue.replaceAll("%", "\\\\%");// 将查询中的%转义为\\%
				}
				criteria.add(Restrictions.like(daoCriteria.getPropertyName(), "%" + value + "%"));
			}
		}
		return criteria;
	}

	/**
	 * 将查询条件<code>DaoCriteria</code>转换成<code>Criteria</code>
	 * 
	 * @param c
	 * @param daoCriteria
	 * @author aaron lee
	 */
	@SuppressWarnings("unchecked")
	public static void convertCriteria(Criteria c, DaoCriteria daoCriteria) {
		String operator = daoCriteria.getOperator();
		Object value = daoCriteria.getValue();
		String propertyName = daoCriteria.getPropertyName();
		if (QueryOperatorEnum.EQUAL.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.eq(propertyName.substring(propertyName.indexOf(".") + 1), value));
			}else{
				c.add(Restrictions.eq(propertyName, value));
			}
		} else if (QueryOperatorEnum.BIGGER.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.gt(propertyName.substring(propertyName.indexOf(".") + 1), value));
			}else{
				c.add(Restrictions.gt(propertyName, value));
			}
		} else if (QueryOperatorEnum.BIGGER_EQUAL.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.ge(propertyName.substring(propertyName.indexOf(".") + 1), value));
			}else{
				c.add(Restrictions.ge(propertyName, value));
			}
		} else if (QueryOperatorEnum.IN.getName().equals(operator)) {
			if (!CollectionUtils.isEmpty((Collection<?>) value)) {
				if(propertyName.indexOf(".") != -1){
					Criteria subC = null;
					String[] props = StringUtils.split(propertyName, ".");
					for (int j = 0; j < props.length; j++) {
						if (j != props.length - 1) {
							subC = c.createCriteria(props[j]);
						}
					}
					subC.add(Restrictions.in(propertyName.substring(propertyName.indexOf(".") + 1), (Collection<?>) value));
				}else{
					c.add(Restrictions.in(propertyName, (Collection<?>) value));
				}
			}
		} else if (QueryOperatorEnum.LESS.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.lt(propertyName.substring(propertyName.indexOf(".") + 1), value));
			}else{
				c.add(Restrictions.lt(propertyName, value));
			}
		} else if (QueryOperatorEnum.LESS_EQUAL.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.le(propertyName.substring(propertyName.indexOf(".") + 1), value));
			}else{
				c.add(Restrictions.le(propertyName, value));
			}
		} else if (QueryOperatorEnum.NOT_EQUAL.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.ne(propertyName.substring(propertyName.indexOf(".") + 1), value));
			}else{
				c.add(Restrictions.ne(propertyName, value));
			}
		} else if (QueryOperatorEnum.LIKE.getName().equals(operator)) {
			// 将查询中的%转义为\\%
			if (value instanceof String) {
				String oldValue = (String) value;
				value = oldValue.replaceAll("%", "\\\\%");
			}
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.like(propertyName.substring(propertyName.indexOf(".") + 1), "%" + value + "%"));
			}else{
				c.add(Restrictions.like(propertyName, "%" + value + "%"));
			}
		} else if (QueryOperatorEnum.ORDER.getName().equals(operator)) {
			if (value instanceof Boolean) {
				Boolean asc = Boolean.valueOf(value.toString());
				if(propertyName.indexOf(".") != -1){
					Criteria subC = null;
					String[] props = StringUtils.split(propertyName, ".");
					for (int j = 0; j < props.length; j++) {
						if (j != props.length - 1) {
							subC = c.createCriteria(props[j]);
						}
					}
					if (asc)
						subC.addOrder(Order.asc(propertyName.substring(propertyName.indexOf(".") + 1)));
					else
						subC.addOrder(Order.desc(propertyName.substring(propertyName.indexOf(".") + 1)));
				}else{
					if (asc)
						c.addOrder(Order.asc(propertyName));
					else
						c.addOrder(Order.desc(propertyName));
				}
			}
		} else if (QueryOperatorEnum.COUNT.getName().equals(operator)) {
			c.setMaxResults(Integer.parseInt(String.valueOf(value)));
		} else if (QueryOperatorEnum.PAGE.getName().equals(operator)) {
			c.setFirstResult(daoCriteria.getStartIndex());
			c.setMaxResults(daoCriteria.getMaxCount());
		} else if (QueryOperatorEnum.ISNULL.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.isNull(propertyName.substring(propertyName.indexOf(".") + 1)));
			}else{
				c.add(Restrictions.isNull(propertyName));
			}	
		} else if (QueryOperatorEnum.ISNOTNULL.getName().equals(operator)) {
			if(propertyName.indexOf(".") != -1){
				Criteria subC = null;
				String[] props = StringUtils.split(propertyName, ".");
				for (int j = 0; j < props.length; j++) {
					if (j != props.length - 1) {
						subC = c.createCriteria(props[j]);
					}
				}
				subC.add(Restrictions.isNotNull(propertyName.substring(propertyName.indexOf(".") + 1)));
			}else{
				c.add(Restrictions.isNotNull(propertyName));
			}	
		} else if (QueryOperatorEnum.OR.getName().equals(operator)) {
			/*
			 * wf
			 */
			List<String> list = DaoUtil.string2List(propertyName);
			if (list.size() < 2) {
				return;
			}
			LogicalExpression expression = Restrictions.or(Restrictions.eq(list.get(0), value), Restrictions.eq(list.get(1),
					value));
			for (int i = 2; i < list.size(); i++) {
				expression = Restrictions.or(expression, Restrictions.eq(list.get(i), value));
			}
			c.add(expression);
		}
	}

	/**
	 * 将DwrDaoCriteria对象转化为DaoCriteria对象
	 */
	public static List<DaoCriteria> convertDwrDaoCriteria(List<DwrDaoCriteria> criterias) {
		List<DaoCriteria> daoCriterias = new ArrayList<DaoCriteria>();
		if (null != criterias && !criterias.isEmpty()) {
			for (DwrDaoCriteria criteria : criterias) {
				DaoCriteria tempDaoCriteria = new DaoCriteria();
				tempDaoCriteria.setPropertyName(criteria.getPropertyName());
				tempDaoCriteria.setOperator(criteria.getOperator());
				tempDaoCriteria.setStartIndex(criteria.getStartIndex());
				tempDaoCriteria.setMaxCount(criteria.getMaxCount());
				if (criteria.getValueType().isStringOnly()) {// 单个的字符串
					tempDaoCriteria.setValue(criteria.getValue());
					daoCriterias.add(tempDaoCriteria);
				} else if (criteria.getValueType().isLongOnly()) {// 单个的长整型
					Long tempValue = Long.parseLong(criteria.getValue());
					tempDaoCriteria.setValue(tempValue);
					daoCriterias.add(tempDaoCriteria);
				} else if (criteria.getValueType().isBooleanOnly()) {// 单个的布尔型
					Boolean tempValue = Boolean.parseBoolean(criteria.getValue());
					tempDaoCriteria.setValue(tempValue);
					daoCriterias.add(tempDaoCriteria);
				} else if (criteria.getValueType().isStringCollection()) {// 字符串List
					String separator = criteria.getSeparator();
					List<String> tempStringList = new ArrayList<String>();
					String[] tempStringArray = criteria.getValue().split(separator);
					for (int i = 0; i < tempStringArray.length; i++)
						tempStringList.add(tempStringArray[i]);
					tempDaoCriteria.setValue(tempStringList);
					daoCriterias.add(tempDaoCriteria);
				} else if (criteria.getValueType().isIntegerCollection()) {// 整型List
					String separator = criteria.getSeparator();
					List<Integer> tempIntegerList = new ArrayList<Integer>();
					String[] tempStringArray = criteria.getValue().split(separator);
					for (int i = 0; i < tempStringArray.length; i++) {
						Integer tempValue = Integer.parseInt(tempStringArray[i]);
						tempIntegerList.add(tempValue);
					}
					tempDaoCriteria.setValue(tempIntegerList);
					daoCriterias.add(tempDaoCriteria);
				} else if (criteria.getValueType().isLongCollection()) {// 长整型List
					String separator = criteria.getSeparator();
					List<Long> tempLongList = new ArrayList<Long>();
					String[] tempStringArray = criteria.getValue().split(separator);
					for (int i = 0; i < tempStringArray.length; i++) {
						Long tempValue = Long.parseLong(tempStringArray[i]);
						tempLongList.add(tempValue);
					}
					tempDaoCriteria.setValue(tempLongList);
					daoCriterias.add(tempDaoCriteria);
				}
			}
		}
		return daoCriterias;
	}

	/**
	 * 将数据库获取的字段转换为业务数据需要的类型
	 * <p>
	 * 即:将String转换为List<String>
	 * 
	 * @param notifyList
	 */
	public static List<String> string2List(String notifyString) {
		List<String> usersList = new ArrayList<String>();
		if (notifyString == null || notifyString.trim().length() == 0)
			return usersList;
		String[] strs = StringUtils.split(notifyString, SPLITER);
		for (int i = 0; i < strs.length; i++) {
			usersList.add(strs[i]);
		}
		return usersList;
	}

	/**
	 * 将业务数据类型转换为数据库要求的字段
	 * <p>
	 * 即:将List转换为String<String>
	 * 
	 * @param notifyList
	 */
	public static String list2String(List<String> notifyList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < notifyList.size() - 1; i++) {
			if (notifyList.get(i) != null)
				sb.append(notifyList.get(i)).append(SPLITER);
		}
		if (CollectionUtils.isNotEmpty(notifyList)) {
			if (notifyList.get(notifyList.size() - 1) != null)
				sb.append(notifyList.get(notifyList.size() - 1));
		}
		return sb.toString();
	}
}
