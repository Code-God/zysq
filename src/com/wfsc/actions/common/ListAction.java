package com.wfsc.actions.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.log.LogUtil;
import com.base.util.Page;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.keyword.KeyWord;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.Products;
import com.wfsc.services.keyword.IKeyWordService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.productcat.IProductCatService;

/**
 * 订单列表
 * 
 * @author Xiaojiapeng
 * 
 */
@SuppressWarnings("unchecked")
@Controller("listAction")
@Scope("prototype")
public class ListAction extends CupidBaseAction {

	private static final long serialVersionUID = -3567393270844062097L;
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	@Resource(name = "productCatService")
	private IProductCatService productCatService;

	@Resource(name = "productsService")
	private IProductsService productsService;
	
	@Autowired
	private IKeyWordService keywordService;
	
	public String list(){
		//分类编码
		String catCode = request.getParameter("code");
		String keyword = request.getParameter("keyword");
		String sorter = request.getParameter("sort");
		String order = request.getParameter("order");
		String pageStr = request.getParameter("page");

		if(StringUtils.isEmpty(sorter)){
			sorter = "disPrice";
		}
		if(StringUtils.isEmpty(order)){
			order = "asc";
		}
		
		request.setAttribute("code", catCode);
		request.setAttribute("keyword", keyword);
		request.setAttribute("sorter", sorter);
		request.setAttribute("order", order);
		//顶级分类
		String topCatCode = "";
		//二级分类
		String catCode2 = "";
		//三级分类
		String catCode3 = "";
		
		//顶级分类
		ProductCat topProductCat = null;
		//所有二级分类
		List<ProductCat> productCat2 = new ArrayList<ProductCat>();
		//所有三级分类
		List<ProductCat> productCat3 = new ArrayList<ProductCat>();
		
		//查询结果
		List<Products> products = new ArrayList<Products>();
		
		int limit = 12;
		int currPage = 1;
		if(StringUtils.isNotEmpty(pageStr)){
			currPage = Integer.parseInt(pageStr);
		}
		
		Page<Products> result = null;
		
		//点击分类进行查询时
		if(StringUtils.isBlank(keyword)){
			String prdCatCode = "";
			if(catCode.length() == 3){
				topCatCode = catCode;
				prdCatCode = topCatCode;
				
			}else if(catCode.length() == 6){
				topCatCode = catCode.substring(0, 3);
				catCode2 = catCode;
				prdCatCode = catCode2;
			}else{
				topCatCode = catCode.substring(0, 3);
				catCode2 = catCode.substring(0,6);
				catCode3 = catCode;
				prdCatCode = catCode3;
			}
			
			Page<Products> page = new Page<Products>(currPage, limit);
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("prdCatCode", prdCatCode);
			param.put("sorter", sorter);
			param.put("order", order);
			
			result = productsService.findPage(page, param);
			
			
			//根据顶级分类进行模糊查询，获取所有分类
			List<ProductCat> allCats = productCatService.findProductCatByCode(topCatCode);
			if(CollectionUtils.isNotEmpty(allCats)){
				for(ProductCat cat : allCats){
					//三级分类选中
					if(cat.getLevel() == 3 && StringUtils.equals(cat.getCode(), catCode)){
						request.setAttribute("currThreeCat", cat);
					}
					//二级分类展开
					if(catCode.length() >= 6 && cat.getLevel() == 2 && catCode.startsWith(cat.getCode())){
						request.setAttribute("currTwoCat", cat);
					}
					if(cat.getLevel() == 1){
						topProductCat = cat;
					}else if(cat.getLevel() == 2){
						productCat2.add(cat);
					}else if(cat.getLevel() == 3){
						productCat3.add(cat);
					}
				}
			}
		}else{
			//商品搜索进入时
			Page<Products> page = new Page<Products>(currPage, limit);
			Map<String, Object> param = new HashMap<String, Object>();
			if(StringUtils.isNotEmpty(catCode)){
				param.put("prdCatCode", catCode);
			}
			param.put("key", keyword);
			param.put("sorter", sorter);
			param.put("order", order);
			
			result = productsService.findPage(page, param);
		}
		if(result != null){
			products = result.getData();
			
			//搜索需要组装目录层次，显示出所有和商品相关的分类
			if(StringUtils.isNotBlank(keyword)){
				if(CollectionUtils.isNotEmpty(products)){
					// 首先获取所有分类
					List<ProductCat> categories = productCatService.getAllProductCat();
					// 获取模糊查询结果的产品分类
					List<String> relateCats = productsService.findBySeachKey(keyword);
					// 获取所有相关分类的
					for(String relateCat : relateCats){
						for(ProductCat category : categories){
							if(StringUtils.equals(category.getCode(), relateCat) || (category.getLevel() != 1 && relateCat.startsWith(category.getCode()))){
								if(category.getLevel()==2 && !productCat2.contains(category)){
									productCat2.add(category);
									
									if(StringUtils.isNotEmpty(catCode) && catCode.startsWith(category.getCode())){
										request.setAttribute("currTwoCat", category);
									}
								}
								if(category.getLevel() == 3 && !productCat3.contains(category)){
									productCat3.add(category);
									
									if(StringUtils.isNotEmpty(catCode) && StringUtils.equals(catCode, category.getCode())){
										request.setAttribute("currThreeCat", category);
									}
								}
							}
						}
					}
					
					//根据关键字查到了数据，更新关键字计数器
					keywordService.updateKeywordCounter(keyword);
				}else{
					request.setAttribute("keyword", keyword);
					return "noresult";
				}
			}
			
			request.setAttribute("products", products);
			request.setAttribute("currPage", result.getCurrPageNo());
			request.setAttribute("pages", result.getTotalPageCount());
			request.setAttribute("totalCount", result.getTotalCount());
			request.setAttribute("topProductCat", topProductCat);
			request.setAttribute("productCatTwo", productCat2);
			request.setAttribute("productCatThree", productCat3);
			
			// 获取本站用户最近浏览记录
			List<Products> justLook = (List<Products>) super.session.get(CupidStrutsConstants.SESSION_JUSTLOOK);
			request.setAttribute("justLook", justLook);
			
			if(StringUtils.isBlank(keyword) && CollectionUtils.isEmpty(products)){
				return "catnoresult";
			}
		}
		
		return "productList";
	}
	
	public void getKeyWord() {
		
		String key = getPara("key");
		if (key == null) {
			key = "";
		}

		List<KeyWord> list = keywordService.findAllKeyWord(key);

		renderJson(JSONArray.fromObject(list).toString());
	}
	
}
