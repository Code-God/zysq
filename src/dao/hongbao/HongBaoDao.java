package dao.hongbao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.act.HongBao;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("hongBaoDao")
public class HongBaoDao extends EnhancedHibernateDaoSupport<HongBao> {

	@Override
	protected String getEntityName() {
		return HongBao.class.getName();
	}
	
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<HongBao> list = new ArrayList<HongBao>();
		String hql = "from HongBao where 1=1 ";
		String countHql = "select count(id) from HongBao where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			if (paramMap.get("orgId") != null && !StringUtils.isEmpty(paramMap.get("orgId"))) {
				cond += " and orgId = " + paramMap.get("orgId") + "";
			}
		}
		hql += cond + " order by  createDate desc";
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
