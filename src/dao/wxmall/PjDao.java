package dao.wxmall;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.wxmall.Pj;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.ServerBeanFactory;
import com.base.util.Page;
import com.wfsc.daos.product.ProductsDao;
@Repository("pjDao")
public class PjDao extends EnhancedHibernateDaoSupport<Pj> {

	@Override
	protected String getEntityName() {
		return Pj.class.getName();
	}

	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Pj> list = new ArrayList<Pj>();
		String hql = "from Pj where 1=1 ";
		String countHql = "select count(id) from Pj where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			if (paramMap.get("prdId") != null && !StringUtils.isEmpty(paramMap.get("prdId"))) {
				cond += " and prdid = " + paramMap.get("prdId") + "";
			}
			if (paramMap.get("keyword") != null && !StringUtils.isEmpty(paramMap.get("keyword"))) {
				cond += " and content like '%" + paramMap.get("keyword") + "%'";
			}
			
			if (paramMap.get("orgId") != null && !StringUtils.isEmpty(paramMap.get("orgId"))) {
				cond += " and orgId = " + paramMap.get("orgId") + "";
			}
		}
		hql += cond + " order by  pjDate desc";
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

	/**
	 * 按分页查找 
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<Pj> findForPage(Page<Pj> page, Map<String,Object> paramap){
		Map<String,Object> dataMap = new HashMap<String,Object>();
		StringBuffer hql = new StringBuffer("select distinct c from Pj as c where 1=1 ");
		try {
			Date sdate = null;
			Date edate = null;
			for (String key : paramap.keySet()) {
				if ("prdid".equals(key)) {
					hql.append(" and c.prdid = :prdid");
					dataMap.put("prdid", paramap.get("prdid"));
					continue;
				}
				if ("startTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					sdate = sf.parse(paramap.get(key).toString());
					hql.append(" and c.pjDate >= :pjDate " );
					dataMap.put("pjDate", sdate);
					continue;
				}
				if ("endTime".equals(key)) {
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
					edate = sf.parse(paramap.get(key).toString());
					hql.append(" and c.pjDate <= :edate ");
					dataMap.put("edate", edate);
				}
			}
			int totalCount = this.countByHqlWithParama(hql.toString(),dataMap);
			page.setTotalCount(totalCount);
			List<Pj> list = this.findList4PageWithParama(hql.toString(), page
					.getFirst() - 1, page.getPageSize(),dataMap);
			ProductsDao dao = (ProductsDao) ServerBeanFactory.getBean("productsDao");
			//填充商品名
			for (Pj pj : list) {
				if(dao.getEntityById(pj.getPrdid()) != null){
					pj.setPrdName(dao.getEntityById(pj.getPrdid()).getName());
				}
			}
			
			page.setData(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
