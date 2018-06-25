package com.wfsc.actions.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.Paging;
import model.bo.auth.Org;
import model.bo.hotel.BookRecord;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.product.IHotelService;

/**
 * 酒店行业相关ACTION
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("HotelAction")
@Scope("prototype")
public class HotelAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6196102306994217616L;


	private Logger logger = Logger.getLogger(AdminAction.class);
	
	private Org org;
	
	@Resource(name = "userService")
	private IUserService userService;

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	/**
	 * 房间预定列表
	 * @return
	 * @throws IOException
	 */
	public String bookList() {
		return "ok";
	}
	
	public String cancelBook() throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try{
			IHotelService service = (IHotelService) ServerBeanFactory.getBean("hotelService");
			String id = request.getParameter("id");
			service.updateBookRecord(Long.valueOf(id), BookRecord.STATUS_IDLE);
			out.write("{\"msg\":\"ok\"}");
		}catch(Exception e){
			out.write("{\"msg\":\"fail\"}");
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getBookList() throws IOException{
		response.setCharacterEncoding("UTF-8");
		IHotelService service = (IHotelService) ServerBeanFactory.getBean("hotelService");
		PrintWriter out = response.getWriter();
		String page = request.getParameter("page") == null ? "0" : request.getParameter("page").toString();
		String limit = request.getParameter("limit") == null ? "20" : request.getParameter("limit").toString();
		
		// 查询参数
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orgId", this.getCurrentOrgAdmin().getId().toString());
		
		try {
			Paging pp = service.getMyBookedRecords(Integer.valueOf(page), Integer.valueOf(limit).intValue(), paramMap);
			if (!pp.getDatas().isEmpty()) {
				out.print(JSONObject.fromObject(pp).toString());
			} else {
				out.print("{\"result\":\"null\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
}
