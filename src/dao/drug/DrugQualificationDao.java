package dao.drug;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugQualification;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 医疗专业认证
 *
 */
@Repository("drugQualificationDao")
public class DrugQualificationDao extends EnhancedHibernateDaoSupport<DrugQualification>{

	@Override
	protected String getEntityName() {		
		return DrugQualification.class.getName();
	}

	
	/**
	 * 通过用户id查看医疗认证信息
	 */
	public DrugQualification getQualificationByUserId(String userid){
		String hql="from DrugQualification where userId="+userid;
		List<DrugQualification> qulist=this.getHibernateTemplate().find(hql);
		if(qulist!=null&&qulist.size()>0){
			return qulist.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 查看医师认证列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<DrugQualification> findPageForQualification(final Page<DrugQualification> page, Map<String,Object> paramap){
		final StringBuffer sql = new StringBuffer("select q.id, u.realname,u.sex,q.hospital,q.office,q.professional_title,q.review_state "
				+ " from drug_qualification as q"
				+ " inner join wf_user u on q.user_id=u.id"
				+ " where 1=1");
		
		
		
		for(String key : paramap.keySet()){
			if("hospital".equals(key)){
				sql.append(" and q.hospital like '%"+paramap.get(key)+"%'");
				continue;
			}
			
			if("office".equals(key)){
				sql.append(" and q.office like '%"+paramap.get(key)+"%'");
				continue;
			}
			
			if("professionalTitle".equals(key)){
				sql.append(" and q.professional_title like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("reviewState".equals(key)){
				sql.append(" and q.review_state like '%"+paramap.get(key)+"%'");
				continue;
			}
		}
		sql.append(" order by  q.id  desc");
		
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				q.setFirstResult(page.getFirst()-1);
				q.setMaxResults(page.getPageSize());
				return q.list();
			}
		});
		if(list!=null&&list.size()>0){
			page.setTotalCount(list.size());
		}else{
			page.setTotalCount(0);
			list= new ArrayList();
		}
		page.setData(list);
		return page;
	}
	
	/**
	 * 通过认证id查询认证信息
	 * @param id
	 * @return
	 */
	public Object findQualificationById(String id){
		final StringBuffer sql = new StringBuffer("select q.id, u.realname,u.sex,q.hospital,q.office,q.professional_title,q.review_state,q.qualification_pic "
				+ " from drug_qualification as q"
				+ " inner join wf_user u on q.user_id=u.id"
				+ " where q.id="+id);
		List list= this.getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException, HibernateException {
				Query q = session.createSQLQuery(sql.toString());
				return q.list();
			}
		});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
		
	}
	
}
