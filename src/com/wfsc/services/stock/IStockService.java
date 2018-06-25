package com.wfsc.services.stock;

import com.base.util.Page;
import com.wfsc.common.bo.product.ProductStock;

/**
 * 商品库存管理
 * @author Xiaojiapeng
 *
 */
public interface IStockService {
	
	/**
	 * 根据商品编码和城市编码获取商品库存
	 * @param prdCode
	 * @param cityCode
	 * @return
	 */
	public int getStockByPCodeAndCCode(String prdCode, int cityCode);
	
	/**
	 * 根据城市编码删除商品库存
	 * 警告：该方法会删除一个城市区域的所有商品库存，谨慎调用
	 * @param cityCode
	 * @return 删除商品的库存数据量
	 */
	public int deletePrdStockByCityCode(int cityCode);
	
	/**
	 * 生成商品库存数据
	 * 当添加一件新的商品时，调用该方法，可生成所有支持城市的库存数据，默认库存为0
	 * @param prdCode
	 */
	public void generatePrdStock(String prdCode);
	
	/**
	 * 修改商品库存
	 * 修改指定城市的指定商品的库存
	 * @param prdCode
	 * @param cityCode
	 * @param stock
	 */
	public void saveOrUpdateProductStock(String prdCode, int cityCode, int stock);
	
	public Page<ProductStock> getProductStockByPrdCode(Page<ProductStock> page, String prdCode);

}
