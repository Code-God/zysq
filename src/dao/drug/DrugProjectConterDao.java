package dao.drug;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import model.bo.drug.DrugProjectConter;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 项目中心
 * @author Administrator
 *
 */

@Repository("drugProjectConterDao")
public class DrugProjectConterDao extends EnhancedHibernateDaoSupport<DrugProjectConter> {

	@Override
	protected String getEntityName() {
		return DrugProjectConter.class.getName();
	}
	/**
	 * 分页查询项目中心列表
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<DrugProjectConter> findPageForProjectConter(Page<DrugProjectConter> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from DrugProjectConter where 1=1");
		for(String key : paramap.keySet()){
			if("organizationName".equals(key)){
				hql.append(" and organizationName like '%"+paramap.get(key)+"%'");
				continue;
			}			
		}
		hql.append(" order by  id desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugProjectConter> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}
	
	/**
	 * 通过疾病id查看项目中心
	 */
	
	public List<DrugProjectConter> getProjectConterByDiseaseid(String diseaseId){
		String hql=" from DrugProjectConter where diseaseId='"+diseaseId+"' order by id desc";
		return this.getHibernateTemplate().find(hql);
		
	}

}
