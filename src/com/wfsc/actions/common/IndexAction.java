package com.wfsc.actions.common;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.log.LogUtil;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.ProductRecommend;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.system.SeoConfig;
import com.wfsc.services.ad.IAdService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.services.system.ISeoConfigService;

/**
 * 广告
 * 
 * @author Xiaojiapeng
 * 
 */
@Controller("indexAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class IndexAction extends CupidBaseAction {

	private static final long serialVersionUID = -3567393270844062097L;

	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Resource(name = "adService")
	private IAdService adService;

	@Resource(name = "productCatService")
	private IProductCatService productCatService;

	@Resource(name = "productsService")
	private IProductsService productService;
	
	@Autowired
	private ISeoConfigService seoConfigService;

	public String index() {

		// 获取幻灯片广告
		List<AdvConfig> adList = adService.queryByType(2);
		setAttr("adList", adList);

		// 获取普通广告
		List<AdvConfig> adCommonList = adService.queryByType(1);

		// 获取所有商品分类
		List<ProductCat> pcList = productCatService.getAllProductCat();
		// 获取所有推荐商品
		List<Products> productList = productService.findByRecommend();

		try {
			// 构建分类树Map
			LinkedHashMap<Long, ProductCat> pcMap = ProductUtil.buildProductCatMap(pcList);
			
			// 将推荐分类挂在1级分类上
//			for (ProductCat pc : pcList) {
//				// 如果是推荐分类
//				if (pc.getRecommend() == ProductCat.RECOMMEND_YES) {
//					// 将推荐分类放入一级分类
//					getFirstParent(pcMap, pc.getParentId()).getRecommendList().add(pc);
//				}
//			}
			
			// 移除子分类
			ProductUtil.clearChildCat(pcMap);

			// 遍历一级分类
			int index = 1;// 遍历索引
			int mark = 0;// 取到第一个广告了
			Iterator iter = pcMap.entrySet().iterator();
			while (iter.hasNext()) {
				// 将推荐商品放入一级分类
				Map.Entry entry = (Map.Entry) iter.next();
				ProductCat pc = (ProductCat) entry.getValue();
				for (Products prd : productList) {
					// 如果推荐商品的一级分类属于当前分类,挂载
					if (getFirstCatCode(prd.getPrdCatCode()).equals(pc.getCode())) {
						// 大图推荐
						if (prd.getRecommend() == 2) {
							pc.setBigTop(prd);
						} else {
							// 小图推荐
							pc.getProList().add(prd);
						}
					}
				}
				// 将普通广告 挂在分类上(每显示两个分类，显示一个广告)
				if (CollectionUtils.isNotEmpty(adCommonList)) {
					if (mark < adCommonList.size() && index % 2 == 0) {
						pc.setAd(adCommonList.get(mark));
						mark++;
					}
				}
				index++;
			}
			setAttr("pcMap", pcMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		setAttr("pcList", pcList);
		setAttr("isIndex", "true");

		// 查询推荐商品和特惠商品
		List<ProductRecommend> recommendList = productService.queryAllProductRecommend();
		setAttr("recommendList", recommendList);
		
		// 获取SEO设置
		SeoConfig seoConfig = seoConfigService.getSeoConfig();
		request.setAttribute("seoConfig", seoConfig);
		
		return SUCCESS;
	}

	private static String getFirstCatCode(String childCode) {
		return childCode.substring(0, 3);
	}
}
