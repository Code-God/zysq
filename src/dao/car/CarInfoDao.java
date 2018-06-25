package dao.car;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.car.CarInfo;
import model.bo.car.CleaningLogs;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("carInfoDao")
public class CarInfoDao extends EnhancedHibernateDaoSupport<CarInfo> {

	@Override
	protected String getEntityName() {
		return CarInfo.class.getName();
	}

	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CarInfo> list = new ArrayList<CarInfo>();
		String hql = "from CarInfo where 1=1 ";
		String countHql = "select count(id) from CarInfo where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			//根据时间查询
			if (paramMap.get("flag") != null && !StringUtils.isEmpty(paramMap.get("flag"))) {
				cond += " and flag = '" + Long.valueOf(paramMap.get("flag")) + "'";
			}
			if (paramMap.get("openId") != null && !StringUtils.isEmpty(paramMap.get("openId"))) {
				cond += " and openId = '" + paramMap.get("openId") + "'";
			}
		}
		hql += cond + " order by  submitDate desc";
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
