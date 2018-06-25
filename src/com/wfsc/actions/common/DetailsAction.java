package com.wfsc.actions.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.account.Favourite;
import com.wfsc.common.bo.comment.Comments;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.comment.ICommentsService;
import com.wfsc.services.favourite.IFavouriteService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.services.stock.IStockService;

/**
 * 商品详情
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Controller("detailsAction")
@Scope("prototype")
public class DetailsAction extends CupidBaseAction {

	private static final long serialVersionUID = 1046934988472814177L;

	Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Resource(name = "productsService")
	private IProductsService productService;
	
	@Resource(name = "productCatService")
	private IProductCatService productCatService;
	
	@Resource(name = "commentsService")
	private ICommentsService commentsService;
		
	@Resource(name = "stockService")
	private IStockService stockService;
	
	@Autowired
	private IFavouriteService favouriteService;

	public String index() {

		// 获取商品ID
		Long id = getParaToLong("id");
		String code = getPara("code");
		
		Products prd = null;
		if (!StringUtils.isEmpty(code)) {
			prd = productService.getByPrdCode(code);
		} else {
			prd = productService.getById(id);
			code = prd.getPrdCode();
		}
		
		setAttr("prd", prd);
		
		// 产品图片
		List<String> picList = new ArrayList<String>();
		picList.add(prd.getPicUrl1());
		picList.add(prd.getPicUrl2());
		picList.add(prd.getPicUrl3());
		picList.add(prd.getPicUrl4());
		picList.add(prd.getPicUrl5());
		setAttr("picList", picList);
		
		// 好中差分别多少条
		int all = commentsService.countByStarsAndPrdCode(0, prd.getPrdCode());
		int one = commentsService.countByStarsAndPrdCode(1, prd.getPrdCode());
		int two = commentsService.countByStarsAndPrdCode(2, prd.getPrdCode());
		int three = commentsService.countByStarsAndPrdCode(3, prd.getPrdCode());
		setAttr("all", all);
		setAttr("one", one);
		setAttr("two", two);
		setAttr("three", three);
		
		// 记录访问产品
		List<Products> justLook = justLook(prd);
		setAttr("justLook", justLook);

		// 获取所有商品分类
		List<ProductCat> pcList = productCatService.getAllProductCat();
		// 构建分类树Map
		LinkedHashMap<Long, ProductCat> pcMap = ProductUtil.buildProductCatMap(pcList);
		
		// 查询当前商品的父分类
		ProductCat parentPc = productCatService.findByCode(prd.getPrdCatCode());
		ProductCat lastPc = pcMap.get(parentPc.getParentId());
		setAttr("lastPc", lastPc);
		
		String html = "商品详情";
		try {
			// 当前顶级分类信息
			ProductCat pc1 = null;
			ProductCat pc2 = null;
			ProductCat pc3 = null;
			
			// 类别预处理(为了显获取顶级类别)
			String code1 = code.substring(0, 3);
			String code2 = code.substring(0, 6);
			String code3 = code.substring(0, 9);
			
			// 获取当前顶级类别
			Iterator iter = pcMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				ProductCat pc = (ProductCat) entry.getValue();
				if (pc.getCode().equals(code1)) {
					pc1 = pc;
				}
				if (pc.getCode().equals(code2)) {
					pc2 = pc;
				}
				if (pc.getCode().equals(code3)) {
					pc3 = pc;
				}
			}
			
			html = "<strong><a href=\"{oneUrl}\">{one}</a></strong>";
			html = html.replace("{oneUrl}", "public/list.Q?code=" + pc1.getCode());
			html = html.replace("{one}", pc1.getName());
			
			html += "<span>&gt;&nbsp;<a href=\"{twoUrl}\" id='two'>{two}</a></span>";
			html = html.replace("{twoUrl}", "public/list.Q?code=" + pc2.getCode());
			html = html.replace("{two}", pc2.getName());
			
			html += "<span>&nbsp;&gt;&nbsp;<a href=\"{threeUrl}\">{three}</a></span>";
			html = html.replace("{threeUrl}", "public/list.Q?code=" + pc3.getCode());
			html = html.replace("{three}", pc3.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("link", html);
		
		// 移除子分类
		ProductUtil.clearChildCat(pcMap);
		setAttr("pcMap", pcMap);
		
		//商品是否收藏
		if(session.get(CupidStrutsConstants.SESSION_USER) != null){
			User user = (User)session.get(CupidStrutsConstants.SESSION_USER);
			Favourite favourite = favouriteService.getFavouriteByUserIdAndPrdId(user.getId(), prd.getId());
			if(favourite != null){
				prd.setFavourite(true);
			}
		}
		
		return SUCCESS;
	}
	
	public void getPager() {

		String prdCode = getPara("prdCode");
		Integer stars = getParaToInt("stars");
		if (stars ==  null) {
			stars = 0;
		}

		// 获取分页对象
		Page<Comments> page = super.getPage();
		// 分页条件
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("prdCode", prdCode);
		param.put("stars", stars);
		// 分页查询
		page = commentsService.findPage(page, param);
		for(Comments cm : page.getData()){
//			User user = userService.getUserById(cm.getCreatorId());
//			cm.setUser(user);
			cm.setShowDate(DateUtils.formatDate(cm.getPdate(), "yyyy-MM-dd HH:mm:ss"));
		}

		renderJson(JSONObject.fromObject(page).toString());
	}

	// 获取产品库存
	public void getStock(){
		
		String prdCode = getPara("prdCode");
		
		// 查询库存
		City city = (City)super.session.get(CupidStrutsConstants.CURR_CITY);
		int stock = stockService.getStockByPCodeAndCCode(prdCode, city.getCode());
		
		JSONObject json = new JSONObject();
		json.put("stock", stock);
		
		renderJson(json.toString());
	}
	
	private static final int JUSTLOOK_MAX = 10;
	
	// 刚刚看过的商品
	private List<Products> justLook(Products pro){
		LinkedList<Products> list = (LinkedList<Products>) super.session.get(CupidStrutsConstants.SESSION_JUSTLOOK);
		if (list == null) {
			list = new LinkedList<Products>();
		}
		// 如果已经存在，就不记录
		boolean flag = false;
		for (Products x : list) {
			if (x.getPrdCode().equals(pro.getPrdCode())) {
				flag = true;
			}
		}
		if (!flag) {
			list.addFirst(pro);
		}
		
		if (list.size() > JUSTLOOK_MAX) {
			// 删除最后一个
			list.removeLast();
		}

		super.session.put(CupidStrutsConstants.SESSION_JUSTLOOK, list);
		
		return list;
	}
}
