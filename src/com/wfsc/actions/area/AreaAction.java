package com.wfsc.actions.area;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.constants.CupidStrutsConstants;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.city.ICityService;

@Controller("AreaAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class AreaAction extends CupidBaseAction{

	private static final long serialVersionUID = 5116848577283541054L;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 根据IP地址定位用户所在城市
	 * @throws IOException 
	 */
	public void current() throws IOException{
		String cityCodeStr = request.getParameter("cityCode");
		//131为北京市的城市编码，默认为北京市
		int cityCode = 131;
		if(StringUtils.isNotEmpty(cityCodeStr)){
			try{
				cityCode = Integer.parseInt(cityCodeStr);
			}catch (Exception ex){
			}
		}
		City city = cityService.getCityByCode(cityCode);
		//根据城市编码未找到相关城市信息，则设置默认城市为北京市
		if(city == null){
			cityCode = 131;
			city = cityService.getCityByCode(cityCode);
		}
		User user = (User)session.get(CupidStrutsConstants.SESSION_USER);
		//如果用户登录了，切换地址后，将当前用户切换的城市编码存入用户表中，以便下次用户登录，直接加载到该区域
		if(user != null){
			User userBo = userService.getUserById(user.getId());
			userBo.setCityCode(city.getCode());
			user.setCityCode(city.getCode());
			userService.updateUser(userBo);
		}
		
		session.put(CupidStrutsConstants.CURR_CITY, city);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(JSONObject.fromObject(city).toString());
		response.getWriter().flush();
	}
	
	/**
	 * 获取所有城市
	 */
	public void all(){
		List<City> cities = cityService.queryCitiesForSupport();
		JSONArray array = new JSONArray();
		String json = "";
		if(CollectionUtils.isNotEmpty(cities)){
			array.addAll(cities);
			json = array.toString();
		}
		
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}

}
