/******************************************************************************** 
 * Create Author   : Jonim
 * Create Date     : 2009-10-15
 * File Name       : JdbcObjectOperatorDao.java
 *
 * Apex OssWorks是上海泰信科技有限公司自主研发的一款IT运维产品，公司拥有完全自主知识产权及专利，
 * 本系统的源代码归公司所有，任何团体或个人不得以任何形式拷贝、反编译、传播，更不得作为商业用途，对
 * 侵犯产品知识产权的任何行为，上海泰信科技有限公司将依法对其追究法律责任。
 *
 * Copyright 1999 - 2009 Tekview Technology Co.,Ltd. All right reserved.
 ********************************************************************************/
package dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import util.CommonUtil;
import util.OceanRuntimeException;

/**
 * 对自定义表单公共数据库操作，保存保存，查找等功能
 * 
 * @author Jonim
 * 
 */
public class JdbcObjectOperatorDao extends HibernateDaoSupport {

	private static Logger log = Logger.getLogger(JdbcObjectOperatorDao.class);
	
	/**
	 * save the table
	 * 
	 * @param sqlStr
	 */
	public void saveTableData(String sqlStr) {
		Statement stmt = null;
		Connection con = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			stmt = con.createStatement();
			stmt.executeUpdate(sqlStr);
			log.info("-------------------save object success!---------------------");
		} catch (SQLException e) {
			 e.printStackTrace();
			log.error(e.getMessage() + "\n-------------------save object failed!---------------------:" + sqlStr);
			throw new OceanRuntimeException("save.customFeild.sql.erre");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null && !con.isClosed())
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}




	/**
	 * get the total row counts
	 * 
	 * @param sqlStr
	 * @return
	 */
	public Long getTotalCount(String sqlStr) {
		Long i = Long.valueOf(0);
		Statement stmt = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStr);
			if (rs.next()) {
				i = ((Number) rs.getObject(1)).longValue();
			}
			log.info("-------------------get object success!---------------------");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("-------------------get counts object failed!---------------------");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null && !con.isClosed())
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}

	/**
	 * get the total row counts
	 * 
	 * @param sqlStr
	 * @return
	 */
	public long getTotalCounts(String sqlStr) {
		long i = 0;
		Statement stmt = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStr);
			rs.last();
			i = Long.parseLong(String.valueOf(rs.getRow()));
			log.info("-------------------get object success!---------------------");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("-------------------get counts object failed!---------------------");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null && !con.isClosed())
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}

	// 查询group by之后的分组记录总数
	public Long getGroupTotalCount(String sqlStr) {
		Long i = Long.valueOf(0);
		Statement stmt = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sqlStr);
			// if (rs.next()) {
			// i = (Long) rs.getObject(1);
			rs.last();
			i = 1L * rs.getRow();
			// }
			log.info("-------------------get object success!---------------------");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("-------------------get counts object failed!---------------------");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (con != null && !con.isClosed())
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}


	/**
	 * 
	 * 生成的sql 查询具体的CI列表
	 * 
	 * @param sql
	 * @return
	 * @throws OceanRuntimeException
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryObjectBysql(final String sql) throws OceanRuntimeException {
		Session session = getSession();
		if (null == StringUtils.trimToNull(sql))
			return new ArrayList<Object[]>();
		try {
			org.hibernate.SQLQuery query = session.createSQLQuery(sql);
			List<Object[]> list = query.list();
			return list;
		} catch (Exception e) {
			throw new OceanRuntimeException("");
		} finally {
			if (null != session && session.isOpen()) {
				// session.close();
			}
		}
	}

	/**
	 * 根据sql语句返回Map形式结果集
	 * 
	 * @param sql
	 * @param start
	 * @param limit
	 * @return
	 * @throws OceanRuntimeException
	 * @author JoveDeng
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> queryMapBysql(final String sql, final Integer start, final Integer limit)
			throws OceanRuntimeException {
		final List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		if (StringUtils.trimToEmpty(sql).equals(""))
			return rlist;
		this.querySql(start, limit, sql, new SQLResultOperation() {

			@Override
			public Object opration(ResultSet set, ResultSetMetaData meta) throws OceanRuntimeException {
				try {
					while (set.next()) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (int i = 1; i <= meta.getColumnCount(); i++) {
							String col = meta.getColumnLabel(i);
							if (null == col || "".equals(col))
								col = meta.getColumnName(i);
							// 由于使用Oracle存入的键值都变成了大小，故在存入时全部改为小写了
							Object value  = set.getObject(i);
							if (null !=value && value.getClass().getSimpleName().toUpperCase().equals("CLOB")) {
								java.sql.Clob clob =(Clob) value;
								value =  StringUtils.trimToEmpty(clob.getSubString(1l,Long.valueOf(clob.length()).intValue()));
							}
							map.put(StringUtils.trim(col).toLowerCase(), value);
						}
						rlist.add(map);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					throw new OceanRuntimeException("sql语句查询结果数据处理错误!");
				}
				return null;
			}
		});
		return rlist;
	}

	public List<Map<String, Object>> queryMapBysql(final String sql) throws OceanRuntimeException {
		final List<Map<String, Object>> rlist = new ArrayList<Map<String, Object>>();
		if (StringUtils.trimToEmpty(sql).equals(""))
			return rlist;
		return this.queryMapBysql(sql, null, null);
	}

	/**
	 * 
	 * sql模板操作接口
	 * 
	 * @author JoveDeng
	 * @version 1.0
	 * @since Apex OssWorks 5.5
	 */
	public interface SQLResultOperation {

		public Object opration(ResultSet set, ResultSetMetaData meta) throws OceanRuntimeException;
	}

	/**
	 * 
	 * sql查询模板方法
	 * 
	 * @param sql
	 * @param resultOperation
	 * @author JoveDeng
	 * @return
	 */
	public Object querySql(String sql, SQLResultOperation resultOperation) {
		return querySql(null, null, sql, resultOperation);
	}

	/**
	 * 
	 * sql查询模板方法
	 * 
	 * @param sql
	 * @param resultOperation
	 * @author JoveDeng
	 * @return
	 */
	public Object querySql(Integer start, Integer limit, String sql, SQLResultOperation resultOperation) {
		Connection con = null;
		Statement stmt = null;
		ResultSet set = null;
		ResultSetMetaData meta = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (start != null || limit != null) {
				stmt.setMaxRows(start + limit);
			}
			set = stmt.executeQuery(sql);
			if (null != start && start != 0) {
				// set.relative(start);
				set.absolute(start);
			}
			meta = set.getMetaData();
			return resultOperation.opration(set, meta);
		} catch (Exception e) {
			e.printStackTrace();
			throw new OceanRuntimeException("sql语句查询数据错误!" + sql);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (set != null) {
					set.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
			}
		}
	}

	/**
	 * 
	 * 批量执行sql
	 * 
	 * @param sql
	 */
	public void execSqlBatch(List<String> sqls) throws OceanRuntimeException {
		this.execSqlBatch(sqls, null);
	}

	/**
	 * 
	 * 批量执行sql
	 * 
	 * @param sql
	 */
	public Object execSqlBatch(List<String> sqls, JdbcDaoOperatorINTransaction operrator) throws OceanRuntimeException {
		Connection con = null;
		Statement stmt = null;
		ResultSet set = null;
		Object obj = null;
		DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
		try {
			con = ds.getConnection();
//			con.setAutoCommit(false);
			stmt = con.createStatement();
			for (String sql : sqls) {
				if (null != StringUtils.trimToNull(sql)) {
					stmt.addBatch(sql);
					System.out.println(sql);
				}
			}
			stmt.executeBatch();
			if (null != operrator)
				obj = operrator.operator(stmt);
//			con.commit();
			return obj;
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new OceanRuntimeException("sql.batch.error");
		} catch (OceanRuntimeException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					log.error(e1);
				}
			}
			throw new OceanRuntimeException(" sql batch has error!");
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				log.error(e);
			}
			try {
				if (set != null) {
					set.close();
				}
			} catch (SQLException e) {
				log.error(e);
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				log.error(e);
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 
	 * 生成的sql 查询具体的CI列表
	 * 
	 * @param sql
	 * @return
	 * @throws OceanRuntimeException
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> queryObject4pageBysql(int start, int limit, final String sql) throws OceanRuntimeException {
		Session session = getSession();
		if (null == StringUtils.trimToNull(sql))
			return new ArrayList<Object[]>();
		try {
			org.hibernate.SQLQuery query = session.createSQLQuery(sql);
			query.setFirstResult(start);
			query.setMaxResults(limit);
			List<Object[]> list = query.list();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new OceanRuntimeException("");
		} finally {
			if (null != session && session.isOpen()) {
				// session.close();
			}
		}
	}

	/**
	 * 创建资产视图
	 * 
	 * @param tableNames
	 */
	public void createAssetView(List<String> tableNames) {
		StringBuffer strSql = new StringBuffer("drop view if exists asset_view;");
		String columns = "id,asset_name,asset_code,asset_type,asset_price,asset_purchase,asset_warranty,asset_supplier,asset_contract,asset_status,asset_principal";
		if (tableNames != null && tableNames.size() > 0) {
			strSql.append("create view asset_view as select ").append(columns).append(" from ").append(tableNames.get(0));
		}
		for (int i = 1; i < tableNames.size(); i++) {
			strSql.append(" union select ").append(columns).append(" from ").append(tableNames.get(i));
		}
		Statement statement = null;
		Connection conn = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			conn = ds.getConnection();
			statement = conn.createStatement();
			String[] sqls = strSql.toString().split(";");
			for (String sql : sqls) {
				statement.addBatch(sql);
			}
			statement.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (statement != null)
				statement.close();
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	/**
	 * query data by sql
	 * 
	 * @param sqlStr
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object executeQuery (String sql) {
		PreparedStatement pstat = null;
		Connection con = null;
		ResultSet rs = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			pstat = con.prepareStatement(sql);
			rs = pstat.executeQuery();
			if (rs.next()) {
				return rs.getObject(1);
			}
			log.info("-------------------get object success!---------------------");
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("-------------------get object datas failed!---------------------");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstat != null)
					pstat.close();
				if (con != null && !con.isClosed())
					con.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * save the table
	 * 
	 * @param sqlStr
	 */
	public boolean validateSqlExpression(String sql) {
		boolean isValidate = false;
		Statement stmt = null;
		Connection con = null;
		try {
			DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
			con = ds.getConnection();
			stmt = con.createStatement();
			stmt.executeQuery(sql);
			isValidate = true;
		} catch (SQLException e) {
			// e.printStackTrace();
			log.error(e.getMessage() + "\n-------------------validate dispatch rule failed!---------------------:" + sql);
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (con != null && !con.isClosed()) con.close();
			} catch (Exception ex) {
				
			}
		}
		return isValidate;
	}
	
	
	/**
	 * 批处理执行sql语句
	 * 
	 * @param dbTableName
	 * @return
	 */
	public boolean executeBatch(List<String> sqls){
		Connection conn = null;
		Statement stmt = null;
		try {
			if (CommonUtil.isNotEmpty(sqls)) {
				DataSource ds = SessionFactoryUtils.getDataSource(getSessionFactory());
				conn = ds.getConnection();
				stmt = conn.createStatement();
				for (String sql : sqls) {
					stmt.addBatch(sql);
				}
				stmt.executeBatch();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try { if (stmt != null) { stmt.close(); } } catch (Exception e) { e.printStackTrace(); }
			try { if (conn != null) { conn.close(); } } catch (Exception e) { e.printStackTrace(); }
		}
		return true;
	}
}
