package dao.drug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugUDRelation;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;

/**
 * 医疗专业认证
 *
 */
@Repository("drugUDRelationDao")
public class DrugUDRelationDao extends EnhancedHibernateDaoSupport<DrugUDRelation>{

	@Override
	protected String getEntityName() {		
		return DrugUDRelation.class.getName();
	}
	/**
	 *  查询报名人员
	 */
	
	public Page<Object> findPageForRelation(final Page<Object> page,Map<String,Object> paramap){
		
		final StringBuffer sql = new StringBuffer("select users.regdate,users.realname,users.mobile_phone,users1.nickname,item.disease_name,users.audit_status,relation.signup_date,"
				+ " users.province,users.city,users.birthDate"
				+ " from drug_u_d_relation as relation"
				+ " left JOIN wf_user as users on relation.user_id=users.id"
				+ " left JOIN wf_user as users1 on relation.referrer_id=users1.id"
				+ " left JOIN drug_disease_item item on relation.disease_id=item.id"
				+ " where 1=1");
		
		
		for(String key : paramap.keySet()){
			if("realname".equals(key)){
				sql.append(" and users.realname like '%"+paramap.get(key)+"%'");
				continue;
			}
			
			if("diseaseName".equals(key)){
				sql.append(" and item.disease_name like '%"+paramap.get(key)+"%'");
				continue;
			}
			
			if("auditStatus".equals(key)){
				sql.append(" and users.audit_status like '%"+paramap.get(key)+"%'");
				continue;
			}
		}
		sql.append(" order by  relation.signup_date  desc");

		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				page.setTotalCount(q.list().size());
				q.setFirstResult(page.getFirst()-1);
				q.setMaxResults(page.getPageSize());
				
				return q.list();
			}
		});
		
		
		if(list!=null&&list.size()>0){
			
			//page.setTotalCount(list.size());
		}else{
			page.setTotalCount(0);
			list= new ArrayList();
		}
		page.setData(list);
		return page;
		
	}
	/**
	 *  通过用户id查询是否有推荐
	 * @param userid
	 * @return
	 */
	
	public DrugUDRelation findDrugUDRelationByUId(String userid){
		
		String hql=" from DrugUDRelation where userId='"+userid+"' ";
		List<DrugUDRelation> list=this.getHibernateTemplate().find(hql);
		if (list!=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	
	}
	
	/**
	 * 查询推荐人列表
	 */
	
	 public Page<Object> findPageForRecommendedPerson(final Page<Object> page,Map<String,Object> paramap){
		 
		 final StringBuffer sql = new StringBuffer("select relation.referrer_id,u.realname,u.nickname,u.mobile_phone,relation.signup_date"
					+ " from drug_u_d_relation relation"
					+ " left join wf_user u on relation.referrer_id=u.id"
					+ " where 1=1"
					+ " and relation.referrer_id <> '' " );
			
			for(String key : paramap.keySet()){
				if("realname".equals(key)){
					sql.append(" and u.realname like '%"+paramap.get(key)+"%'");
					continue;
				}
			}
			sql.append("group by relation.referrer_id");
			List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws SQLException, HibernateException {
					Query q = session.createSQLQuery(sql.toString());
					page.setTotalCount(q.list().size());
					q.setFirstResult(page.getFirst()-1);
					q.setMaxResults(page.getPageSize());
					return q.list();
				}
			});
			if(list!=null&&list.size()>0){
				//page.setTotalCount(list.size());
			}else{
				page.setTotalCount(0);
				list= new ArrayList();
			}
			page.setData(list);
			return page;
		
	}
	/**
	 *  根据推荐人id获取用户列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	 
	 public Page<Object> findPageForUsers(final Page<Object> page, Map<String,Object> paramap){
		 final StringBuffer sql = new StringBuffer("select  relation.user_id,u.realname,u.nickname,u.mobile_phone,u.regdate"
					+ " from drug_u_d_relation relation"
					+ " left join wf_user u on relation.user_id=u.id"
					+ " where 1=1");
		 		for(String key : paramap.keySet()){
				   if("referrerId".equals(key)){
					sql.append(" and relation.referrer_id ="+paramap.get(key)+" ");
					continue;
				}
			}
			
			for(String key : paramap.keySet()){
				if("realname".equals(key)){
					sql.append(" and u.realname like '%"+paramap.get(key)+"%'");
					continue;
				}
			}
			sql.append(" order by u.regdate desc");
			List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session) throws SQLException, HibernateException {
					Query q = session.createSQLQuery(sql.toString());
					page.setTotalCount(q.list().size());
					q.setFirstResult(page.getFirst()-1);
					q.setMaxResults(page.getPageSize());
					return q.list();
				}
			});
			if(list!=null&&list.size()>0){
				//page.setTotalCount(list.size());
			}else{
				page.setTotalCount(0);
				list= new ArrayList();
			}
			page.setData(list);
			return page;
		 
	 }
	 
	 /**
	  * 根据用户id和项目id查找用户是否报名过改项目
	  * @param userId
	  * @param diseaseId
	  * @return
	  */
	 public boolean isExist(String userId,String diseaseId){
		 
		 String hql=" from DrugUDRelation where userId='"+userId+"' and diseaseId="+diseaseId;
		 List list=this.getHibernateTemplate().find(hql);
		 if(list!=null && list.size()>0){
			 return true;
		 }
		 return false;
	 }

	
}
