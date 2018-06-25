package com.wfsc.services.product;

import java.util.List;
import java.util.Map;

import model.bo.fenxiao.OneProduct;

import com.base.util.Page;
import com.wfsc.common.bo.product.ProductRecommend;
import com.wfsc.common.bo.product.Products;

/**
 * 产品service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IProductsService {

	/** 获取所有商品 */
	public List<Products> getAll();

	/** 获取某个商品的信息 */
	public Products getById(Long id);

	/** 添加修改商品 */
	public void saveOrUpdateEntity(Products products);

	/** 删除商品信息 */
	public void deleteById(Long id);

	public void deleteByIds(List<Long> ids);

	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 * @param paramap 查询参数.
	 * 
	 * @return 分页查询结果
	 */
	public Page<Products> findForPage(final Page<Products> page, Map<String, Object> paramap);
	
	/**
	 * 分页查询(前台专用)
	 * @param page
	 * @param paramap
	 * @return
	 */
	public Page<Products> findPage(final Page<Products> page, Map<String, Object> paramap);
	
	/**
	 * 根据关键字查询商品，支持分页
	 * @param keyword
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Products> getProductByKeyword(String keyword, int start, int limit);

	/**
	 * 获取推荐商品
	 * 
	 * @return
	 */
	public List<Products> findByRecommend();

	/**
	 * 获取分类下所属产品
	 * 
	 * @param catCode 分类编码
	 * @return
	 */
	public List<Products> findByCatCode(String catCode);
	
	/**
	 *  获取分类下所属产品，支持分页，排序
	 * @param catCode
	 * @param start
	 * @param limit
	 * @param sort 排序规则，现仅支持两种：0-按发布时间（倒序），1-按价格（从小到大）， 默认按时间排序
	 * @return
	 */
	public List<Products> findByCatCode(String catCode, int start, int limit, int sort);
	
	/**
	 * 获取分类下所属产品，支持分页，排序
	 * @param catCode
	 * @param start
	 * @param limit
	 * @param sorter 排序的属性
	 * @param order 排序类型 asc desc
	 * @return
	 */
	public List<Products> findByCatCode(String catCode, int start, int limit, String sorter, String order);
	
	/**
	 * 根据商品编码获取商品信息
	 * 
	 * @param code 产品编码
	 * @return
	 */
	public Products findByCode(String code);

	/**
	 * 获取所有的新品特惠信息
	 * @return
	 */
	public List<ProductRecommend> queryAllProductRecommend();
	
	/**
	 * 根据类型获取新品特惠信息
	 * @param type
	 * @return
	 */
	public List<ProductRecommend> queryRecommendByType(int type);
	
	/**
	 * 根据推荐类型和商品编码返回推荐信息
	 * @param type
	 * @param prdCode
	 * @return
	 */
	public ProductRecommend getRecommendByTypeAndPrdCode(int type, String prdCode);
	
	/**
	 * 删除特惠新品
	 * @param id
	 */
	public void deleteRecommendById(long id);
	
	/**
	 * 添加特惠新品
	 * @param type
	 * @param prdCode
	 */
	public void addProductRecommend(int type, String prdCode);
	
	public String getMaxCodeByCatCode(String prdcatCode);
	public Products getByPrdCode(String prdCode);
	
	/**
	 * 获取搜索商品相关分类
	 * @param key 搜索关键字
	 * @return
	 */
	public List<String> findBySeachKey(String key);
	
	/**
	 * 根据商品ID查找 
	 * @param prdId
	 * @return
	 */
	public Products getPrductById(Long prdId);
	
	public int getRecommendCount(Integer recommend,String prdCatCode);

	public OneProduct getOnePrdById(Long id);

	public void saveOrUpdateOnePrd(OneProduct one);

	public Products getPrductByCode(String prdCode);

	public void modSaleCount(String prdId, String saleCount);
}
