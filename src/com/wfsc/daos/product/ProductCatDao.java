package com.wfsc.daos.product;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
import com.base.util.Page;
import com.wfsc.common.bo.product.ProductCat;

/**
 * 商品分类
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Repository("productCatDao")
public class ProductCatDao extends EnhancedHibernateDaoSupport<ProductCat> {

	public List<ProductCat> queryAll() {
		return getAllEntities();
	}

	/**
	 * 获取子分类
	 * @param code 分类编码
	 * @return
	 */
	public List<ProductCat> findByCode(String code) {
		List<ProductCat> list = getHibernateTemplate().find("from ProductCat as pc where pc.code like '"+ code +"%'");
		return list;
	}

	@Override
	protected String getEntityName() {
		return ProductCat.class.getName();
	}
	
	public Page<ProductCat> findForPage(Page<ProductCat> page, Map<String,Object> paramap){
		StringBuffer hql = new StringBuffer("from ProductCat p where 1=1 ");
		for(Map.Entry<String, Object> entry : paramap.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			if(value == null)
				continue;
			if("name".equals(key) && StringUtils.isNotBlank(value.toString())){
				hql.append(" and p.name like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("code".equals(key) && StringUtils.isNotBlank(value.toString())){
				hql.append(" and p.code like '%"+paramap.get(key)+"%'");
				continue;
			}
			if("fxcode".equals(key) && StringUtils.isNotBlank(value.toString())){
				hql.append(" and p.fxCode = '"+paramap.get(key)+"'");
				continue;
			}
			if("recommend".equals(key)){
				int recommend = (Integer)value;
				hql.append(" and p.recommend = " + recommend);
			}
		}
		int totalCount = this.countByHql(hql.toString());
		page.setTotalCount(totalCount);
		List<ProductCat> list = this.findList4Page(hql.toString(), page.getFirst() - 1, page.getPageSize());
		page.setData(list);
		return page;
	}

	/**
	 * 获取搜索商品相关分类
	 * @param key
	 * @return
	 */
	public List<ProductCat> findBySeachKey(String key){
		String hql = "from ProductCat where code in (select prdCatCode from Products as p where p.name like '%"+ key +"%')";
		return (List<ProductCat>)super.getHibernateTemplate().find(hql);
	}
	
	/**
	 * 统计一级分类数量
	 * @return
	 */
	public int countTopPrdCat(){
		String hql = "select count(*) from ProductCat where parentId is null or parentId = 0";
		return this.countByHql(hql);
	}
	
	public Map<String,String> getTopPrdCatMap(){
		String hql = " from ProductCat where parentId is null or parentId = 0 order by id";
		Map<String,String> map = new LinkedHashMap<String,String>();
		List<ProductCat> list= this.getHibernateTemplate().find(hql);
		if(list!=null){
			for(ProductCat cat : list){
				map.put(cat.getCode(), cat.getName());
			}
		}
		return map;
	}
	
	/**
	 * 统计分类的子分类数目
	 * @param parentId
	 * @return
	 */
	public int countChildCat(long parentId){
		String hql = "select count(*) from ProductCat where parentId = " + parentId;
		return this.countByHql(hql);
	}
}