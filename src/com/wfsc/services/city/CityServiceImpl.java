package com.wfsc.services.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.util.Page;
import com.wfsc.common.bo.system.City;
import com.wfsc.daos.city.CityDao;
import com.wfsc.daos.stock.ProductStockDao;

@Service("cityService")
public class CityServiceImpl implements ICityService {
	
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private ProductStockDao stockDao;

	@Override
	public void changeCitySupportStatus(long id, boolean support) {
		City city = getCityById(id);
		if(city == null)
			return;
		if(city.isSupport() == support)
			return;
		city.setSupport(support);
		cityDao.updateEntity(city);
		//如果原来是支持的，改变为不支持后，将会删除该城市区域下的所有商品库存数据
		//TODO : 是否需要删除原有库存数据
		if(support == false){
//			stockDao.deletePrdStockByCityCode(city.getCode());
		}
		
	}

	@Override
	public City getCityByCode(int cityCode) {
		 City city = cityDao.getUniqueEntityByOneProperty("code", cityCode);
		 return city;
	}

	@Override
	public City getCityById(long id) {
		return cityDao.getEntityById(id);
	}

	@Override
	public Page<City> queryAllCityForPage(Page<City> page, String name, Boolean support) {
		return cityDao.queryAllCityForPage(page, name, support);
	}

	@Override
	public List<City> queryCitiesForSupport() {
		return cityDao.queryCityBySupport(true);
	}

	@Override
	public void changeCityHotStatus(long id, boolean hot) {
		City city = getCityById(id);
		if(city == null)
			return;
		if(city.isHot() == hot)
			return;
		city.setHot(hot);
		cityDao.updateEntity(city);
	}
	
	@Override
	public List<City> queryCitys(Long parentId) {
		return this.cityDao.queryCitys(parentId);
	}
	
	@Override
	public List<City> queryAllCitys() {
		return this.cityDao.queryAllCitys();
	}

}
