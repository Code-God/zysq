/******************************************************************************** 
 * Create Author   : ClarenceWu
 * Create Date     : Apr 27, 2011
 * File Name       : CustomDialect.java
 *
 * APEX OSSWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package dao;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * 增加一些hibernate不支持的类型
 *
 * @author ClarenceWu
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class CustomDialect extends MySQL5InnoDBDialect {
	public CustomDialect(){
        super();
        /** TEXT类型 **/
        registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
    } 
}
