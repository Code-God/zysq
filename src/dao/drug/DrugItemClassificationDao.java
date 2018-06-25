package dao.drug;

import java.util.List;
import java.util.Map;

import model.bo.drug.DrugItemClassification;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 疾病项目类型
 * @author Administrator
 *
 */
@Repository("drugItemClassificationDao")
public class DrugItemClassificationDao extends EnhancedHibernateDaoSupport<DrugItemClassification>{

	@Override
	protected String getEntityName() {
		return DrugItemClassification.class.getName();
	}
	
	public Page<DrugItemClassification> findPageForDisease(Page<DrugItemClassification> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from DrugItemClassification where 1=1");
		for(String key : paramap.keySet()){
			if("classificationName".equals(key)){
				hql.append(" and classificationName like '%"+paramap.get(key)+"%'");
				continue;
			}			
		}
		hql.append(" order by  id  desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugItemClassification> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}
	
	/**
	 * 查看所有疾病信息
	 * @return
	 */
	public List<DrugItemClassification> getAllDrugItemClassification(){
		String hql = "from DrugItemClassification order by id desc";
		return (List<DrugItemClassification>)this.getHibernateTemplate().find(hql);
	}
}
