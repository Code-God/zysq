package com.wfsc.services.stock;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Page;
import com.wfsc.common.bo.product.ProductStock;
import com.wfsc.common.bo.system.City;
import com.wfsc.daos.city.CityDao;
import com.wfsc.daos.stock.ProductStockDao;

@Service("stockService")
public class StockServiceImpl implements IStockService {
	
	@Autowired
	private ProductStockDao stockDao;
	
	@Autowired
	private CityDao cityDao;

	@Override
	public int deletePrdStockByCityCode(int cityCode) {
		return stockDao.deletePrdStockByCityCode(cityCode);
	}

	@Override
	public void generatePrdStock(String prdCode) {
		//查询出所有支持的城市
		List<City> cities = cityDao.queryCityBySupport(true);
		List<ProductStock> stocks = new ArrayList<ProductStock>();
		
		if(CollectionUtils.isNotEmpty(cities)){
			//初始化库存数据库，每个支持的城市下面都生成一条库存为0的数据
			for(City city : cities){
				ProductStock stock = new ProductStock();
				stock.setCityCode(city.getCode());
				stock.setPrdCode(prdCode);
				stock.setStock(0);
				
				stocks.add(stock);
			}
			stockDao.saveOrUpdateAllEntities(stocks);
		}
		
	}

	@Override
	public int getStockByPCodeAndCCode(String prdCode, int cityCode) {
		ProductStock stock = stockDao.getUniqueEntityByPropNames(new String[]{"prdCode","cityCode"}, new Object[]{prdCode, cityCode});
		if(stock == null)
			return 0;
		return stock.getStock();
	}

	@Override
	public void saveOrUpdateProductStock(String prdCode, int cityCode, int stock) {
		ProductStock productStock = stockDao.getUniqueEntityByPropNames(new String[]{"prdCode","cityCode"}, new Object[]{prdCode, cityCode});
		if(productStock == null){
			//不存在，则保存一份新的
			productStock = new ProductStock();
			productStock.setCityCode(cityCode);
			productStock.setPrdCode(prdCode);
			productStock.setStock(stock);
			stockDao.saveEntity(productStock);
		}else{
			productStock.setStock(stock);
			stockDao.updateEntity(productStock);
		}
		
	}

	@Override
	public Page<ProductStock> getProductStockByPrdCode(Page<ProductStock> page, String prdCode) {
		return stockDao.getProductStockByPrdCode(page, prdCode);
	}

}
