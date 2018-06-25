package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.WxRules;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

@Repository("wxRuleDao")
public class WxRuleDao extends EnhancedHibernateDaoSupport<WxRules> {

	@Override
	protected String getEntityName() {
		return WxRules.class.getName();
	}
	
	
	/**
	 * 查找数据 
	 * @param start
	 * @param limit
	 * @param type
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		int totalCount = this.count();
		Map<String, Object> result = new HashMap<String, Object>();
		List<WxRules> list = new ArrayList<WxRules>();
		
		String hql = "from WxRules where 1=1 ";
//		if(paramMap != null){//构建查询参数
//			if(!StringUtils.isEmpty(paramMap.get("t"))){
//				hql += " and isget = '" + paramMap.get("t") + "'";
//			}
//			if(!StringUtils.isEmpty(paramMap.get("wincode"))){
//				hql += " and wincode = '" + paramMap.get("wincode") + "'";
//			}
//		}
		
		hql += " order by id desc";
		
		try{
			list = this.getPagingEntitiesByHql(hql, start, limit);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		result.put("total", totalCount);
		result.put("list", list);
		return result;
	}
}
