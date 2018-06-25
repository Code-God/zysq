package dao.drug;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;

/**
 * 捷信项目管理
 * @author Administrator
 *
 */
@Repository("drugDiseaseDictDao")
public class DrugDiseaseDictDao extends EnhancedHibernateDaoSupport<DrugDiseaseDict> {

	@Override
	protected String getEntityName() {
		return DrugDiseaseDict.class.getName();
	}
	/**
	 * 查询捷信项目列表分页
	 * @param page
	 * @param paramap
	 * @return
	 */
	
	public Page<DrugDiseaseDict> findPageForDiseaseDict(Page<DrugDiseaseDict> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from DrugDiseaseDict where 1=1");
		for(String key : paramap.keySet()){
			if("dicDiseaseName".equals(key)){
				hql.append(" and dicDiseaseName like '%"+paramap.get(key)+"%'");
				continue;
			}			
		}
		hql.append(" order by  id  desc");
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<DrugDiseaseDict> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());	
		page.setData(list);
		return page;
	}

}
