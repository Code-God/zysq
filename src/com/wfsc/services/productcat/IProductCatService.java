package com.wfsc.services.productcat;

import java.util.List;
import java.util.Map;

import com.base.util.Page;
import com.wfsc.common.bo.product.ProductCat;

/**
 * 商品分类
 * 
 * @author Xiaojiapeng
 * 
 */
public interface IProductCatService {
	
	/**
	 * 获取所有分类信息
	 * @param orgCode 
	 * @return
	 */
	public List<ProductCat> getAllProductCat();
	/**
	 * 获取所有分类信息
	 * @param orgCode 
	 * @return
	 */
	public List<ProductCat> getAllProductCat(String orgCode);
	
	/**
	 * 获取子分类
	 * 
	 * @param code 分类编码
	 * @return
	 */
	public List<ProductCat> findProductCatByCode(String code);
	
	/**
	 * 按HQL分页查询.
	 * 
	 * @param page
	 * @param paramap 查询参数.
	 * 
	 * @return 分页查询结果
	 */
	public Page<ProductCat> queryCatForPage(final Page<ProductCat> page, Map<String, Object> paramap);
	
	/***
	 * 根据ID获取对象
	 * @param id 分类ID
	 * @return
	 */
	public ProductCat getProductCatById(Long id);
	
	/**
	 * 添加或修改分类对象
	 * @param productCat
	 */
	public void saveOrUpdateProductCat(ProductCat productCat);
	
	/**
	 * 根据Id批量删除商品分类，忽略该id的商品是否存在
	 * @param ids
	 */
	public void deleteProductCatByIds(List<Long> ids);
	
	/**
	 * 获取搜索商品相关分类
	 * @param key 搜索关键字
	 * @return
	 */
	public List<ProductCat> findBySeachKey(String key);
	
	/**
	 * 生成分类编码
	 * @param level 将要生成的分类编码等级
	 * @param levelOneId 如果生成二级分类编码和三级分类编码，需要该参数
	 * @param levelTwoId 如果生成三级分类编码，需要该参数
	 * @return 生成的编码
	 */
	public String generatorPrdCatCode(int level, long levelOneId, long levelTwoId);
	
	/**
	 * 根据父类id获取子分类，如果Id为0，则返回一级分类
	 * @param parentId
	 * @return
	 */
	public List<ProductCat> getPrdCatsByParentId(long parentId);

	/**
	 * 根据编码获取分类
	 * @param code 分类编码
	 * @return
	 */
	public ProductCat findByCode(String code);

	public List<ProductCat> getAllProductCatByOrg(String code, String pid);
}
