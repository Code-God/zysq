package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.ShowCase;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("showCaseDao")
public class ShowCaseDao extends EnhancedHibernateDaoSupport<ShowCase> {

	@Override
	protected String getEntityName() {
		return ShowCase.class.getName();
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
		List<ShowCase> list = new ArrayList<ShowCase>();
		String hql = "from ShowCase where 1=1 ";
		String countHql = "select count(id) from ShowCase where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			if (paramMap.get("t") != null && !StringUtils.isEmpty(paramMap.get("t"))) {
				//子栏目：：：：：  例如：0-邮政资讯 1-业务学习 2-生活常识
				cond += " and docType = " + paramMap.get("t");
			}
			//只有邮政咨询的文章才有这个字段
			if (paramMap.get("cid") != null && !StringUtils.isEmpty(paramMap.get("cid"))) {
				cond += " and cid = " + paramMap.get("cid");
			}
			//大模块
			if (paramMap.get("model") != null && !StringUtils.isEmpty(paramMap.get("model"))) {
				cond += " and columns.module = " + paramMap.get("model");
			}
			
			if (paramMap.get("keyword") != null && !StringUtils.isEmpty(paramMap.get("keyword"))) {
				cond += " and title like '%" + paramMap.get("keyword") + "%'";
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
