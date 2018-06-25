package com.wfsc.actions.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;

import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.base.util.Page;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.services.system.ISystemLogService;

/**
 * 产品分类管理
 * @author Xiaojiapeng
 *
 */
@Controller("ProductCatAction")
@Scope("prototype")
@SuppressWarnings("unchecked")
public class ProductCatAction extends DispatchPagerAction  {

	private static final long serialVersionUID = -6840812222299260353L;

	@Resource(name = "productCatService")
	private IProductCatService productCatService;
	
	@Resource(name = "adminService")
	private AdminService adminService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	private ProductCat productCat;
	
	private String picName;
	
	private File picFile;

	public ProductCat getProductCat() {
		return productCat;
	}

	public void setProductCat(ProductCat productCat) {
		this.productCat = productCat;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public File getPicFile() {
		return picFile;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public String index(){
//		list();
		return "index";
	}
	
	public String list(){
		Page<ProductCat> page = super.getPage();
		Map<String,Object> param = new HashMap<String,Object>();
		page.setPaginationSize(7);
		
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String recommendStr = request.getParameter("recommend");
		Integer recommend = null; 
		
		param.put("name", name);
		param.put("code", code);
		//需要通过分销商编码进行过滤
		param.put("fxcode", this.getCurrentOrgCode());
		request.setAttribute("name", name);
		request.setAttribute("code", code);
		if(StringUtils.isNotEmpty(recommendStr)){
			recommend = Integer.valueOf(recommendStr);
			param.put("recommend", recommend);
			request.setAttribute("recommend", recommendStr);
		}
		
		page = productCatService.queryCatForPage(page, param);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/productcat_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		return "list";
	}
	
	public String input() {
		ProductCat productCat=null;
		String id =(String) request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			productCat= new ProductCat();
			request.setAttribute("productCat", productCat);
			return "preadd";
		}else{
			productCat = productCatService.getProductCatById(Long.valueOf(id));
			String p1 = productCat.getPicUrl();
			if(StringUtils.isNotEmpty(p1)){
				productCat.setPicUrl(p1.substring(p1.lastIndexOf("/")+1));
			}
		}
		request.setAttribute("productCat", productCat);
		return "edit";
	}
	
	public String edit(){
		if(productCat == null)
			throw new CupidRuntimeException("参数错误");
		long id = productCat.getId();
		ProductCat prdCat = productCatService.getProductCatById(id);
		if(prdCat == null)
			throw new CupidRuntimeException("分类不存在或者已被删除");
		prdCat.setName(productCat.getName());
		if(prdCat.getLevel() == 1){
			prdCat.setBgcolor(productCat.getBgcolor());
			prdCat.setRecommend(productCat.getRecommend());
		}
		productCatService.saveOrUpdateProductCat(prdCat);
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT_CAT, user.getUsername(), "编辑商品分类【" + prdCat.getCode() + "】");
		systemLogService.saveSystemLog(systemLog);
		return "ok";
	}
	
	public String save(){
		if(productCat == null)
			throw new CupidRuntimeException("参数错误");
		
		String fileName = "";
		//编辑不更新图片
//		if(productCat.getLevel() == 1){
//			if(picFile == null || StringUtils.isBlank(picName))
//				throw new CupidRuntimeException("请选择分类图标后保存");
//			fileName = UUID.randomUUID().toString().replaceAll("-", "") + picName.substring(picName.lastIndexOf("."));
//			productCat.setPicUrl(fileName);
//		}
		int level = productCat.getLevel();
		long prdCatLevel1 = 0;
		long prdCatLevel2 = 0;
		String level1 = request.getParameter("oneLevel");
		String level2 = request.getParameter("twoLevel");
		
		//生成分类编码
		if(level == 1){
			productCat.setParentId(0L);
		}else if(level == 2){
			prdCatLevel1 = Long.parseLong(level1);
			productCat.setParentId(prdCatLevel1);
			productCat.setBgcolor("");
			productCat.setPicUrl("");
			productCat.setRecommend(0);
		}else if(level == 3){
			prdCatLevel1 = Long.parseLong(level1);
			prdCatLevel2 = Long.parseLong(level2);
			productCat.setParentId(prdCatLevel2);
			productCat.setBgcolor("");
			productCat.setPicUrl("");
			productCat.setRecommend(0);
		}
		//此分类属于哪个总分销商
		productCat.setCode(this.getCurrentOrgCode());
		//
		productCat.setFxCode(this.adminService.getOrgByUserId(this.getCurrentAdminUser().getId()).getCode());
		productCatService.saveOrUpdateProductCat(productCat);
		
		//保存成功后，再将图片上传到服务器上面
//		if(productCat.getLevel() == 1){
//			FileInputStream fis = null;
//			try {
//				fis = new FileInputStream(picFile);
//				boolean result = FTPUtil.uploadFile(fileName, fis);
//				if(!result){
//					throw new CupidRuntimeException("上传图片到图片服务器失败");
//				}
//			} catch (Exception e) {
//				throw new CupidRuntimeException("上传图片到图片服务器失败");
//			}finally{
//				if(fis != null)
//					try {
//						fis.close();
//					} catch (IOException e) {
//					}
//			}
//		}
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT_CAT, user.getUsername(), "新增商品分类【" + productCat.getCode() + "】");
		systemLogService.saveSystemLog(systemLog);
		return "ok";
	}
	
	public void deleteByIds() throws IOException{
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		JSONObject result = new JSONObject();
		try {
			productCatService.deleteProductCatByIds(idList);
			
			Admin user = getCurrentAdminUser();
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT_CAT, user.getUsername(), "删除商品分类");
			systemLogService.saveSystemLog(systemLog);
			result.put("result", "success");
		} catch (CupidRuntimeException ex){
			result.put("result", "failed");
			result.put("msg",ex.getMessage());
		} catch (Exception e) {
			result.put("result", "failed");
			result.put("msg","系统操作，删除失败！");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result.toString());
		response.getWriter().flush();
	}
	
	public void allPrdCat() throws IOException{
		List<ProductCat> prdCats = productCatService.getAllProductCat();
		String json = "";
		JSONArray array = new JSONArray();
		if(CollectionUtils.isNotEmpty(prdCats)){
			array.addAll(prdCats);
			json = array.toString();
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		response.getWriter().flush();
	}
	
	public void category() throws IOException{
		List<ProductCat> cats = productCatService.getAllProductCat();
		
		String json = "";
		if(CollectionUtils.isNotEmpty(cats)){
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<Long, ProductCat> catMap = new LinkedHashMap<Long, ProductCat>();
			//组织数据
			for(ProductCat cat : cats){
				catMap.put(cat.getId(), cat);
			}
			for (Iterator<Long> it =  catMap.keySet().iterator();it.hasNext();){
				Long key = it.next();
				ProductCat value = catMap.get(key);
				if(catMap.get(value.getParentId()) != null){
					catMap.get(value.getParentId()).getChildList().add(value);
				}
			}
			for(Map.Entry<Long, ProductCat> entry : catMap.entrySet()){
				ProductCat cat = entry.getValue();
				if(cat.getLevel() == 1){
					Map<String, Object> temp = convert(cat);
					if(CollectionUtils.isNotEmpty(cat.getChildList())){
						for(ProductCat cat2 : cat.getChildList()){
							Map<String, Object> temp1 = convert(cat2);
							((List<Map<String, Object>>)temp.get("children")).add(temp1);
							if(CollectionUtils.isNotEmpty(cat2.getChildList())){
								for(ProductCat cat3 : cat2.getChildList()){
									Map<String, Object> temp2 = convert(cat3);
									((List<Map<String, Object>>)temp1.get("children")).add(temp2);
								}
							}
						}
					}
					list.add(temp);
				}
			}
			json = JSONArray.fromObject(list).toString();
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
		response.getWriter().flush();
	}
	
	private Map<String, Object> convert(ProductCat cat){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", cat.getName());
		map.put("code", cat.getCode());
		map.put("id", cat.getId());
		map.put("level", cat.getLevel());
		map.put("parentId", cat.getParentId());
		map.put("children", new ArrayList<Map<Long, Object>>());
		return map;
	}

	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
}
