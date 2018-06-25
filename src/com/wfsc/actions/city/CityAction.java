package com.wfsc.actions.city;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.util.Page;
import com.wfsc.actions.common.CupidBaseAction;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.city.ICityService;
import com.wfsc.services.system.ISystemLogService;

@Controller("CityAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class CityAction extends CupidBaseAction{

	private static final long serialVersionUID = -8226696913379030518L;

	@Autowired
	private ICityService cityService;

	@Autowired
	private ISystemLogService systemLogService;

	public String index(){
		list();
		return "index";
	}

	public String list(){
		Page<City> page = super.getPage();
		page.setPaginationSize(7);

		String name = request.getParameter("name");
		String supportStr = request.getParameter("support");

		Boolean support = null;
		if(StringUtils.isNotEmpty(supportStr)){
			support = Boolean.valueOf(supportStr);
		}
		request.setAttribute("name", name);
		request.setAttribute("support", support);

		page = cityService.queryAllCityForPage(page, name, support);

		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/city_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}

	public void setSupport(){
		String idStr = request.getParameter("id");
		String supportStr = request.getParameter("support");
		String result = "success";
		try{
			cityService.changeCitySupportStatus(Long.parseLong(idStr), Boolean.parseBoolean(supportStr));

			Admin user = getCurrentAdminUser();
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_AREA, user.getUsername(), "设置支持配送的城市");
			systemLogService.saveSystemLog(systemLog);
		} catch (Exception ex){
			result = "failed";
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}

	public void setHot(){
		String idStr = request.getParameter("id");
		String hotStr = request.getParameter("hot");
		String result = "success";
		try{
			cityService.changeCityHotStatus(Long.parseLong(idStr), Boolean.parseBoolean(hotStr));

			Admin user = getCurrentAdminUser();
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_AREA, user.getUsername(), "设置热门城市");
			systemLogService.saveSystemLog(systemLog);
		} catch (Exception ex){
			result = "failed";
		}
		JSONObject json = new JSONObject();
		json.put("result", result);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}

	public void queryCitys(){
		String parentId = request.getParameter("parentId");
		Long pid = null;
		if (StringUtils.isNotBlank(parentId)) {
			pid = Long.valueOf(parentId);
		}
		List<City> citys = cityService.queryCitys(pid);
		JSONObject json = new JSONObject();
		json.put("result", citys);
		response.setCharacterEncoding("UTF-8");
		try {
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}


	public String selectCityTree(){
		return "select";
	}

	public String getCityTreeData(){
		response.setCharacterEncoding("UTF-8");
		List<City> citys = cityService.queryAllCitys();
		JSONArray jsons = new JSONArray();
		for(City city : citys){
			JSONObject json = new JSONObject();
			json.put("id", city.getId());
			json.put("name", city.getName());
			json.put("pId", city.getParentId());
			json.put("code", city.getCityCode());
			jsons.add(json);
		}
		try {
			response.getWriter().write(jsons.toString());
		} catch (IOException e) {
		}
		return null;
	}


}
