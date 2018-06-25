package dao.drug;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugScoreLog;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
/**
 * 积分
 * @author Administrator
 *
 */
@Repository("drugScoreLogDao")
public class DrugScoreLogDao extends EnhancedHibernateDaoSupport<DrugScoreLog> {

	@Override
	protected String getEntityName() {
		return DrugScoreLog.class.getName();
	}

	/**
	 * 查看该积分类型的积分是否已经存在
	 * @return
	 */
	public boolean isExsit(String openid,String source){
		String hql="from DrugScoreLog where openId='"+openid+"' and source="+source;
		List<DrugScoreLog> list=this.getHibernateTemplate().find(hql);
		if(list!=null&&list.size()>0){
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 通过openid查看用户积分总数
	 * @param openid
	 * @return
	 */
	public Integer getTotalScoreByOpenid(String openid){
		try{
			final String sql="select sum(score) from drug_score_log where action=1 and  openid='"+openid+"'";
			List list= getHibernateTemplate().execute(new HibernateCallback(){
	
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query= session.createSQLQuery(sql);
					return query.list();
				}
				
			} );	
			if(list!=null&&list.size()>0){
				return Integer.valueOf(list.get(0).toString());
			}else{
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 *  根据openid查看用户获取积分列表分页
	 */
	
	public Page<DrugScoreLog> findPageObtainForDrugScoreLog(Page<DrugScoreLog> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer(" from DrugScoreLog where 1=1 and action=1 ");
		for(String key : paramap.keySet()){
			if("openId".equals(key)){
				hql.append(" and openId='"+paramap.get(key)+"' ");
				continue;
			}			
		}
		hql.append(" order by opdate  desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugScoreLog> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}
	
	
	/**
	 *  根据openid查看用户兑换积分列表分页
	 */
	
	public Page<DrugScoreLog> findPageExchangeForDrugScoreLog(Page<DrugScoreLog> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer(" from DrugScoreLog where 1=1 and action=0 ");
		for(String key : paramap.keySet()){
			if("openId".equals(key)){
				hql.append(" and openId='"+paramap.get(key)+"' ");
				continue;
			}			
		}
		hql.append(" order by opdate  desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugScoreLog> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}
	
	

	
}
