package dao.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.car.CleaningInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("cleaningDao")
public class CleaningInfoDao extends EnhancedHibernateDaoSupport<CleaningInfo> {

	@Override
	protected String getEntityName() {
		return CleaningInfo.class.getName();
	}

	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CleaningInfo> list = new ArrayList<CleaningInfo>();
		String hql = "from CleaningInfo where 1=1 ";
		String countHql = "select count(id) from CleaningInfo where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			//根据区域查询
			if (paramMap.get("area") != null && !StringUtils.isEmpty(paramMap.get("area"))) {
				cond += " and area = '" + paramMap.get("area") + "'";
			}
			
		}
		hql += cond + " order by  facName desc";
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
