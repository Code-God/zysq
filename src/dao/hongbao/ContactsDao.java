package dao.hongbao;

import java.util.HashMap;
import java.util.Map;

import model.bo.Contacts;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("contactsDao")
public class ContactsDao extends EnhancedHibernateDaoSupport<Contacts> {

	@Override
	protected String getEntityName() {
		return Contacts.class.getName();
	}

	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
//		List<UserHongBao> list = new ArrayList<UserHongBao>();
//		String hql = "from UserHongBao where status = 0 and 1=1 ";
//		String countHql = "select count(id) from UserHongBao where status = 0 and 1=1 ";
//		String cond = "";
//		if (paramMap != null) {// 构建查询参数
//			if (paramMap.get("userId") != null && !StringUtils.isEmpty(paramMap.get("userId"))) {
//				cond += " and userId = " + paramMap.get("userId") + "";
//			}
//		}
//		hql += cond + " order by  getDate desc";
//		countHql += cond;
//		List find = this.getHibernateTemplate().find(countHql);
//		int totalCount = Integer.valueOf(find.get(0).toString());
//		try {
//			list = this.getPagingEntitiesByHql(hql, start, limit);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		result.put("total", totalCount);
//		result.put("list", list);
		return result;
	}
}
