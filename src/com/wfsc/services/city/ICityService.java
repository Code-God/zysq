package com.wfsc.services.city;

import java.util.List;

import com.base.util.Page;
import com.wfsc.common.bo.system.City;

/**
 * 城市管理
 * 设置城市是否支持，当前城市等信息
 * @author Xiaojiapeng
 *
 */
public interface ICityService {
	
	/**
	 * 查询所有支持的城市区域
	 * @return
	 */
	public List<City> queryCitiesForSupport();
	
	/**
	 * 分页查询城市信息
	 * @param page 分页参数
	 * @param support 是否是支持的城市，如果该参数为空，则查询所有的城市信息
	 * @return
	 */
	public Page<City> queryAllCityForPage(Page<City> page, String name, Boolean support);
	
	/**
	 * 改变城市支持状态
	 * 注意，城市如果开始是支持的，改变状态后，该城市所拥有的所有商品库存信息将全部清空
	 * @param support
	 */
	public void changeCitySupportStatus(long id, boolean support);
	
	public void changeCityHotStatus(long id, boolean hot);
	
	/**
	 * 根据城市编码获取城市的信息
	 * @param cityCode
	 * @return
	 */
	public City getCityByCode(int cityCode);
	
	/**
	 * 查询城市信息
	 * @param id
	 * @return
	 */
	public City getCityById(long id);
	
	/**
	 * 获取城市
	 * @param support
	 * @return
	 */
	public List<City> queryCitys(Long parentId);
	
	/**
	 * 获取城市
	 * @param support
	 * @return
	 */
	public List<City> queryAllCitys();
	
}
