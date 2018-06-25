package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.GiftLog;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("giftLogDao")
public class GiftLogDao extends EnhancedHibernateDaoSupport<GiftLog> {

	@Override
	protected String getEntityName() {
		return GiftLog.class.getName();
	}

	/**
	 * 查找数据
	 * 
	 * @param start
	 * @param limit
	 * @param type
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<GiftLog> list = new ArrayList<GiftLog>();
		String hql = "from GiftLog where 1=1 ";
		String countHql = "select count(id) from GiftLog where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			if (paramMap.get("startDate") != null && !StringUtils.isEmpty(paramMap.get("startDate"))) {
				cond += " and opdate >= '" + paramMap.get("startDate") + "'";
			}
			if (paramMap.get("endDate") != null && !StringUtils.isEmpty(paramMap.get("endDate"))) {
				cond += " and opdate <= '" + paramMap.get("endDate") + "'";
			}
			if (paramMap.get("stat") != null && !StringUtils.isEmpty(paramMap.get("stat"))) {
				cond += " and status = '" + paramMap.get("stat") + "'";
			}
		}
		hql += cond + " order by opdate desc";
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
