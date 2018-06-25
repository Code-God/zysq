package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.Experts;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("expertsDao")
public class ExpertsDao extends EnhancedHibernateDaoSupport<Experts> {

	@Override
	protected String getEntityName() {
		return Experts.class.getName();
	}

	public Map<String, Object> queryRecord(int start, int limit) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Experts> list = new ArrayList<Experts>();
		String hql = "from Experts where 1=1 ";
		String countHql = "select count(id) from Experts where 1=1 ";
		String cond = "";
		hql += cond + " order by createDate desc";
		countHql += cond;
		List find = this.getHibernateTemplate().find(countHql);
		int totalCount = Integer.valueOf(find.get(0).toString());
		try {
			list = this.getPagingEntitiesByHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}
}
