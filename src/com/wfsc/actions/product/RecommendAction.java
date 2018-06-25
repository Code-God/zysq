package com.wfsc.actions.product;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.wfsc.common.bo.product.ProductRecommend;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.system.ISystemLogService;

@Controller("RecommendAction")
@Scope("prototype")
public class RecommendAction extends DispatchPagerAction {

	private static final long serialVersionUID = 2469851225709557083L;
	
	@Autowired
	private IProductsService productService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	/**
	 * 展示特惠新品
	 * @return
	 */
	public String list(){
		String recommend = request.getParameter("recommend");
		int type = 1;
		List<ProductRecommend> recommends = null;
		if(StringUtils.isNotEmpty(recommend)){
			type = Integer.parseInt(recommend);
			recommends = productService.queryRecommendByType(type);
		}else{
			recommends = productService.queryAllProductRecommend();
		}
		
		request.setAttribute("recommend", recommend);
		request.setAttribute("recommends", recommends);
		return "list";
	}
	
	/**
	 * 取消特惠新品
	 * @throws IOException 
	 */
	public void cancel() throws IOException{
		String idStr = request.getParameter("id");
		long id = 0;
		if(StringUtils.isNotEmpty(idStr)){
			id = Long.parseLong(idStr);
		}
		productService.deleteRecommendById(id);
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_RECOMMEND, user.getUsername(), "取消特惠新品");
		systemLogService.saveSystemLog(systemLog);
		
		JSONObject json = new JSONObject();
		json.put("result", "success");
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}
	
	public String preAdd(){
		return "add";
	}
	
	public void check() throws IOException{
		String prdCode = request.getParameter("prdCode");
		
		JSONObject json = new JSONObject();
		if(StringUtils.isEmpty(prdCode)){
			json.put("result", "failed");
			json.put("msg", "请输入商品编码");
		}else{
			Products product = productService.findByCode(prdCode);
			if(product == null){
				json.put("result", "failed");
				json.put("msg", "商品不存在");
			}else{
				if(product.getDisPrice() == null){
					json.put("result", "failed");
					json.put("msg", "商品未设置折扣价格，不能设置推荐");
				}else{
					String typeStr = request.getParameter("type");
					int type = Integer.parseInt(typeStr);
					ProductRecommend recommend = productService.getRecommendByTypeAndPrdCode(type, prdCode);
					if(recommend != null){
						json.put("result", "failed");
						json.put("msg", "该商品已设置推荐");
					}else{
						json.put("result", "success");
					}
				}
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
	}
	
	public String add(){
		String recommend = request.getParameter("recommend");
		String prdCode = request.getParameter("prdCode");
		if(StringUtils.isEmpty(recommend) || StringUtils.isBlank(prdCode)){
			throw new CupidRuntimeException("参数错误");
		}
		int type = Integer.parseInt(recommend);
		productService.addProductRecommend(type, prdCode);
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_RECOMMEND, user.getUsername(), "新增特惠新品");
		systemLogService.saveSystemLog(systemLog);
		return SUCCESS;
	}

}
