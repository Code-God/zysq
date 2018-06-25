package com.wfsc.daos.keyword;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.wfsc.common.bo.keyword.KeyWord;

@SuppressWarnings("unchecked")
@Repository("keywordDao")
public class KeyWordDao extends EnhancedHibernateDaoSupport<KeyWord> {

	@Override
	protected String getEntityName() {
		return KeyWord.class.getName();
	}
	
	/**
	 * 查找关键字记录
	 * @param keyword
	 * @return
	 */
	public KeyWord getKeyWord(String keyword){
		String hql = "from KeyWord where keyword = ?";
		List<KeyWord> keywords = getHibernateTemplate().find(hql, keyword);
		if(CollectionUtils.isNotEmpty(keywords)){
			return keywords.get(0);
		}
		return null;
	}
	
	public KeyWord getOldestKeyWord(){
		return getHibernateTemplate().execute(new HibernateCallback<KeyWord>(){
			@Override
			public KeyWord doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from KeyWord order by activeDate asc";
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List<KeyWord> keywords = query.list();
				if(CollectionUtils.isNotEmpty(keywords)){
					return keywords.get(0);
				}
				return null;
			}
			
		});
	}
	
	public void deleteOldestKeyWord(){
		KeyWord keyword = getOldestKeyWord();
		if(keyword == null)
			return;
		final long id = keyword.getId();
		getHibernateTemplate().execute(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				String hql = "delete from KeyWord where id = ?";
				Query query = session.createQuery(hql);
				query.setLong(0, id);
				query.executeUpdate();
				return null;
			}
			
		});
	}
	
	/**
	 * 查询最热门的10个关键字
	 * @param key
	 * @return
	 */
	public List<KeyWord> findAllKeyWord(final String key){
		return getHibernateTemplate().execute(new HibernateCallback<List<KeyWord>>(){
			@Override
			public List<KeyWord> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from KeyWord where keyword like '%"+ key +"%' order by searchCount desc,activeDate desc";
				Query query = session.createQuery(hql);
				query.setFirstResult(0);
				query.setMaxResults(10);
				return query.list();
			}
			
		});
	}

}
