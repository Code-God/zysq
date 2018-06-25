package dao.drug;

import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDrugs;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 药物
 * @author Administrator
 *
 */
@Repository("drugDrugsDao")
public class DrugDrugsDao extends EnhancedHibernateDaoSupport<DrugDrugs>{

	@Override
	protected String getEntityName() {
		return DrugDrugs.class.getName();
	}
	
	public Page<DrugDrugs> findPageForDrugs(Page<DrugDrugs> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from DrugDrugs where 1=1");
		for(String key : paramap.keySet()){
			if("medicineName".equals(key)){
				hql.append(" and medicineName like '%"+paramap.get(key)+"%'");
				continue;
			}			
		}
		hql.append(" order by  id  desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugDrugs> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}

	
	/**
	 * 根据项目编号获取对应的药物信息
	 * @param diseaseId
	 */
	public List<DrugDrugs> getDrugMedicineById(Long diseaseId) {
		String hql = "from DrugDrugs where diseaseId = ?";
		return this.getHibernateTemplate().find(hql, diseaseId);
	}

}
