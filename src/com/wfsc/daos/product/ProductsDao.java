package com.wfsc.daos.product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.keyword.KeyWord;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;

/**
 * 商品
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Repository("productsDao")
public class ProductsDao extends EnhancedHibernateDaoSupport<Products> {
	
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Resource
	private ProductCatDao productCatDao;
	/**
	 * 获取推荐商品
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Products> findByRecommend() {
		return getHibernateTemplate().find("from Products where recommend > 0");
	}
	
	/**
	 * 获取分类下所属产品
	 * @param catCoe 分类编码
	 * @return
	 */
	public List<Products> findByCatCode(String catCoe) {
		List<Products> list = getHibernateTemplate().find("from Products WHERE prdCatCode LIKE '"+ catCoe +"%'");
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据产品编码获取产品
	 * @param code 产品编码
	 * @return
	 */
	public Products findByCode(String code) {
		return getEntitiesByOrCondition("prdCode", code).get(0);
	}

	@Override
	protected String getEntityName() {
		return Products.class.getName();
	}
	
	public Page<Products> findForPage(Page<Products> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("select distinct p from Products as p where 1=1 ");
		for(String key : paramap.keySet()){
			if("name".equals(key)){
				hql.append(" and p.name like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("recommend".equals(key)){
				hql.append(" and p.recommend ="+paramap.get(key));
				continue;
			}
			if("prdCatCode".equals(key)){
				hql.append(" and p.prdCatCode like '"+paramap.get(key)+"%'");
				continue;
			}
			if("prdCode".equals(key)){
				hql.append(" and p.prdCode = '"+paramap.get(key)+"'");
				continue;
			}
			//通过分销商过滤不同公司的产品
			if("fxCode".equals(key)){
				//属于该分销商的分类code
				List<ProductCat> cats = this.productCatDao.getEntitiesByOneProperty("fxCode", paramap.get(key));
				if(!cats.isEmpty()){
					StringBuffer sb = new StringBuffer("'");
					int i=0;
					for (ProductCat productCat : cats) {
						if(i == cats.size() - 1){//最后一个元素
							sb.append(productCat.getCode() +"'");
						}else{
							sb.append(productCat.getCode() + "','");
						}
						i++;
					}
					hql.append(" and p.prdCatCode in ("+ sb.toString() +")");
					continue;
				}else{
					//应该返回空记录
					hql.append(" and p.prdCatCode in ('')");
					continue;
				}
			}
		}
		
		hql.append(" order by recommend desc, createDate desc");
		
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<Products> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
	//	page.setTotalCount(list==null?0:list.size());
		page.setData(list);
		return page;
	}
	
	/**
	 * 分页查询(前台专用)
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<Products> findPage(Page<Products> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from Products as p where 1=1 ");
		// 条件查询
		for(String key : paramap.keySet()){
			if ("sort".equals(key)) {
				continue;
			}
			if("prdCatCode".equals(key)){
				hql.append(" and p.prdCatCode like '" + paramap.get(key) + "%'");
				continue;
			}
			if("key".equals(key)){
				hql.append(" and p.name like '%"+ paramap.get(key) +"%'");
				continue;
			}
		}
		// 排序
		if (paramap.get("sort") != null) {
			String sort = (String)paramap.get("sort");
			if (sort.equals("0")) {
				hql.append("order by p.id");
			} else if (sort.equals("1")) {
				hql.append("order by p.price");
			} else if(sort.equals("2")){
				System.out.println("按评论排序");
			}
			// 排序类型
			String isDesc = (String)paramap.get("isDesc");
			if (isDesc != null && Boolean.parseBoolean(isDesc)) {
				hql.append(" DESC");
			}
		}
		
		if(paramap.get("sorter") != null){
			String sorter = (String)paramap.get("sorter");
			String order = (String)paramap.get("order");
			hql.append("order by p." + sorter + " " + order);
		}else{
			hql.append("order by p.disPrice,p.price");
		}
		
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<Products> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}
	
	public List<Products> getProductByKeyword(final String keyword, final int start, final int limit){
		return getHibernateTemplate().execute(new HibernateCallback<List<Products>>(){

			@Override
			public List<Products> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Products where name like '%"+ keyword +"%'";
				Query query = session.createQuery(hql);
//				query.setString(0, keyword);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
			
		});
	}
	
	public synchronized String getMaxCodeByCatCode(String prdcatCode){
		List<Products> list = getHibernateTemplate().find("from Products WHERE prdCatCode = '"+ prdcatCode +"' order by id desc ");
		if (CollectionUtils.isEmpty(list)) {
			return prdcatCode+"000001";
		}else{
			String code = list.get(0).getPrdCode();
			String suff = StringUtils.substring(code, prdcatCode.length());
			String newSuff = Integer.valueOf(suff,10).intValue()+1+"";
			String finalSuff = newSuff;
			for(int i=0;i<6-newSuff.length();i++){
				finalSuff = "0"+finalSuff;
			}
			return prdcatCode+finalSuff;
		}
	}
	
	/**
	 * 获取搜索商品相关分类
	 * @param key 搜索关键字
	 * @return
	 */
	public List<String> findBySeachKey(String key){
		String hql = "select DISTINCT(prdCatCode) from Products as p where p.name like '%"+ key +"%' order by prdCatCode";
		return (List<String>)super.getHibernateTemplate().find(hql);
	}

	/**
	 * 查询最热门的10个关键字
	 * @param key
	 * @return
	 */
	public List<KeyWord> findAllKeyWord(String key){
		String hql = "from KeyWord where keyword like '%"+ key +"%'";
		return super.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 返回当前分类下的商品，支持分页、排序
	 * @param catCode
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Products> findByCatCode(final String catCode, final int start, final int limit, final String order, final String sorter){
		return getHibernateTemplate().executeFind(new HibernateCallback<List<Products>>(){

			@Override
			public List<Products> doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from Products where prdCatCode like '"+ catCode +"%' order by " + order + " " + sorter;
				Query query = session.createQuery(hql);
				query.setFirstResult(start);
				query.setMaxResults(limit);
				return query.list();
			}
			
		});
	}

	public Map<String, Object> queryRecord(int start, int limit, Map<String, String> paramMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Products> list = new ArrayList<Products>();
		String hql = "from Products where 1=1 ";
		String countHql = "select count(id) from Products where 1=1 ";
		String cond = "";
		if (paramMap != null) {// 构建查询参数
			if (paramMap.get("tstatus") != null && !StringUtils.isEmpty(paramMap.get("tstatus"))) {
				cond += " and tstatus = " + paramMap.get("tstatus") + "";
			}
//			if (paramMap.get("prdCatCode") != null && !StringUtils.isEmpty(paramMap.get("prdCatCode"))) {
//				cond += " and prdCatCode = '" + paramMap.get("prdCatCode") + "'";
//			}
			if (paramMap.get("keyword") != null && !StringUtils.isEmpty(paramMap.get("keyword"))) {
				cond += " and name like '%" + paramMap.get("keyword") + "%'";
			}
			if (paramMap.get("charge") != null && !StringUtils.isEmpty(paramMap.get("charge"))) {
				cond += " and chargeScore > 0 ";
			}
			
			//只查询属于自己分销商的产品,先查出该分销商的分类CODE，然后子查询; 注意，只有当prdCatCode没有值时才这么查。
			if (paramMap.get("orgCode") != null && !StringUtils.isEmpty(paramMap.get("orgCode"))) {
				ProductCatDao pcDao = (ProductCatDao) ServerBeanFactory.getBean("productCatDao");
				List<ProductCat> cats = new ArrayList<ProductCat>();
				//如果paramMap.get("prdCatCode")不为空， 则表示要查询该分类以及其子分类的产品
				if(paramMap.get("prdCatCode") != null){
					//找到该分类以及所有子分类ID
//					 cats = pcDao.getHibernateTemplate().find("from ProductCat where pid=" + paramMap.get("pid") );
					 cats = pcDao.getHibernateTemplate().find("from ProductCat where pid=" + paramMap.get("pid") + " or code='"+ paramMap.get("prdCatCode") +"'");
				}else{
					//属于该分销商的分类code
					 cats = pcDao.getEntitiesByOneProperty("fxCode", paramMap.get("orgCode"));
				}
				
				if(!cats.isEmpty()){
					StringBuffer sb = new StringBuffer("'");
					int i=0;
					for (ProductCat productCat : cats) {
						if(i == cats.size() - 1){//最后一个元素
							sb.append(productCat.getCode() +"'");
						}else{
							sb.append(productCat.getCode() + "','");
						}
						i++;
					}
					cond += " and prdCatCode in ("+ sb.toString() +")";
				}else{
					//应该返回空记录
					cond +=  " and prdCatCode in ('')";
				}
			}
			//库存大于0的
			cond += " and stock > 0 ";
			
			//关键字搜索和admin部分，不排除---需求反复，2.6 改回去
//			if (paramMap.get("keyword") == null && paramMap.get("fck") == null) {
//				//特殊需求，排除几个
//				cond += " and id > 4 ";
//			}
		}
		hql += cond + " order by  createDate desc limit " + start + ", " + limit;
		logger.info("##################hql===" + hql);
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

	
	public void setProductCatDao(ProductCatDao productCatDao) {
		this.productCatDao = productCatDao;
	}
}
