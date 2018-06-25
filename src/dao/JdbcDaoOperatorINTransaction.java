/******************************************************************************** 
 * Create Author   : JoveDeng
 * Create Date     : May 24, 2010
 * File Name       : JdbcDaoOperatorINTransaction.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package dao;

import java.sql.Statement;

import util.OceanRuntimeException;

/**
 * 
 * jdbc对执行sql同一事务内业务逻辑的处理
 *
 * @version 1.0
 */
public interface JdbcDaoOperatorINTransaction {

	public Object operator(Statement stmt)throws OceanRuntimeException;
	
}

