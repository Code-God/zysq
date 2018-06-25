package com.wfsc.actions.product;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.bo.fenxiao.OneProduct;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.ICarInfoService;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.base.tools.Version;
import com.base.util.Page;
import com.exttool.MarkConfig;
import com.exttool.MarkTool;
import com.wfsc.common.bo.product.ProductCat;
import com.wfsc.common.bo.product.ProductStock;
import com.wfsc.common.bo.product.Products;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.city.ICityService;
import com.wfsc.services.product.IProductsService;
import com.wfsc.services.productcat.IProductCatService;
import com.wfsc.services.stock.IStockService;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.DateUtil;
import com.wfsc.util.SysUtil;

import dao.fenxiao.OneProductDao;

/**
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
@Controller("ProductsAction")
@Scope("prototype")
public class ProductsAction extends DispatchPagerAction {

	private static final long serialVersionUID = -6840812222299260353L;

	@Resource(name = "productsService")
	private IProductsService productsService;
	
	@Resource(name = "productCatService")
	private IProductCatService productCatService;
	
	@Autowired
	private IStockService stockService;
	
	@Autowired
	private ICityService cityService;
	
	@Autowired
	private ISystemLogService systemLogService;

	private Products products;
	
	private OneProduct one;
	
	private File[] myFile;

	private String[] myFileContentType;

	private String[] myFileFileName;

	private String imageFileName;

	public String manager(){
		list();
		return "manager";
	}
	/**
	 * 系统管理员管理商品， 可以查看并管理所有商家的商品 
	 * @return
	 */
	public String managerall(){
		return "managerall";
	}
	
	@SuppressWarnings("unchecked")
	public String list(){
		Page<Products> page = new Page<Products>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String name = request.getParameter("name");
		String recommend = request.getParameter("recommend");
		String prdCatCode = request.getParameter("prdCatCode");
		String prdCode = request.getParameter("prdCode");
		
		//分销商编号
		String fxCode = request.getParameter("fxCode");
		String fxOrgName = request.getParameter("fxOrgName");
		
		if(fxCode == null && this.getCurrentOrgAdmin() != null){
			fxCode = this.getCurrentOrgCode();
			fxOrgName = this.getCurrentOrgAdmin().getOrgname();
		}else{
			fxOrgName = "admin";
		}
		
		if(StringUtils.isNotEmpty(fxCode)){
			paramap.put("fxCode", fxCode);
			request.setAttribute("fxCode", fxCode);
			request.setAttribute("fxOrgName", fxOrgName);
		}
		if(StringUtils.isNotEmpty(name)){
			paramap.put("name", name);
			request.setAttribute("name", name);
		}
		if(StringUtils.isNotEmpty(recommend)){
			paramap.put("recommend", Integer.valueOf(recommend));
			request.setAttribute("recommend", recommend);
		}
		if(StringUtils.isNotEmpty(prdCatCode)){
			paramap.put("prdCatCode", prdCatCode);
			request.setAttribute("prdCatCode", prdCatCode);
			request.setAttribute("prdCatName", request.getParameter("prdCatName"));
		}
		if(StringUtils.isNotEmpty(prdCode)){
			paramap.put("prdCode", prdCode);
			request.setAttribute("prdCode", prdCode);
		}
		
		page = productsService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/products_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("productslist", page.getData());
		
		if(this.getCurrentAdminUser().isSuperAdmin()){
			request.setAttribute("superAdmin", true);
		}
		
		
		return "list";
	}
	
	public String openPrdCatTree(){
		return "prdCatTree";
	}
	/**
	 * 选择分销商树 
	 * @return
	 */
	public String openOrgTree(){
		return "orgTree";
	}
	public String getPrdCatTreeData(){
		response.setCharacterEncoding("UTF-8");
		List<ProductCat> cats= productCatService.getAllProductCat(this.getCurrentOrgCode());
		 JSONArray jsons = new JSONArray();
		 for(ProductCat type : cats){
			 JSONObject json = new JSONObject();
			 json.put("id", type.getId());
			 json.put("name", type.getName());
			 json.put("pId", type.getParentId());
			 json.put("code", type.getCode());
			 jsons.add(json);
		 }
		 try {
			response.getWriter().write(jsons.toString());
		} catch (IOException e) {
		}
		return null;
	}
	
	public String chekRecommend(){
		String recommend = request.getParameter("recommend");
		String prdCatCode = request.getParameter("prdCatCode");
		String result = "0";
		int s = productsService.getRecommendCount(Integer.valueOf(recommend), prdCatCode);
		//小图推荐，一级分类里面只能有6个小图推荐
		if("1".equals(recommend)){
			if(s>=6){
				result = "1";
			}
		}else if("2".equals(recommend)){//大图推荐，一级分类里面只能有1个大图推荐
			if(s>=1){
				result = "2";
			}
		}
		 try {
				response.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}



	public String productInput() {
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			products = new Products();
		}else{
			products = productsService.getById(Long.valueOf(id));
			String p1 = products.getPicUrl1();
			if(StringUtils.isNotEmpty(p1)){
				products.setPicUrl1(p1.substring(p1.lastIndexOf("/")+1));
			}
			String p2 = products.getPicUrl2();
			if(StringUtils.isNotEmpty(p2)){
				products.setPicUrl2(p2.substring(p2.lastIndexOf("/")+1));
			}
			String p3 = products.getPicUrl3();
			if(StringUtils.isNotEmpty(p3)){
				products.setPicUrl3(p3.substring(p3.lastIndexOf("/")+1));
			}
			String p4 = products.getPicUrl4();
			if(StringUtils.isNotEmpty(p4)){
				products.setPicUrl4(p4.substring(p4.lastIndexOf("/")+1));
			}
			String p5 = products.getPicUrl5();
			if(StringUtils.isNotEmpty(p5)){
				products.setPicUrl5(p5.substring(p5.lastIndexOf("/")+1));
			}
		}
		
		//加载门店
		ICarInfoService s = (ICarInfoService) ServerBeanFactory.getBean("carService");
		//仅加载自己地区的门店
		List<Admin> list = s.getServiceByPid(this.getCurrentAdminUser().getId());
		request.setAttribute("serviceList", list);
		request.setAttribute("serviceId", products.getServiceId());
		
		return "add";
	}
	
	public String detail() {
		String host = Version.getInstance().getNewProperty("image.server.ip");
		String port = Version.getInstance().getNewProperty("image.server.port");
		String url = "http://"+host+":"+port+"/images/";
		request.setAttribute("imgServer", url);
		String id = request.getParameter("id");
		String prdCode = request.getParameter("prdCode");
		if(StringUtils.isEmpty(id)){
			if(StringUtils.isNotEmpty(prdCode)){
				products = productsService.getByPrdCode(prdCode);
				return "detail";
			}
			return "ok";
			
		}else{
			products = productsService.getById(Long.valueOf(id));
		}
		
		//设置服务商名称
		if(products.getServiceId() != null){
			ICarInfoService ss = (ICarInfoService) ServerBeanFactory.getBean("carService");
			products.setServiceName(ss.getServiceName(products.getServiceId()));
		}
		
		return "detail";
	}
	
	public String stock(){
		stocklist();
		return "stock";
	}
	
	@SuppressWarnings("unchecked")
	public String stocklist(){
		String code = request.getParameter("code");
		Products product = productsService.findByCode(code);
		if(product == null)
			throw new CupidRuntimeException("查看的商品不存在或已被删除");
		
		Page<ProductStock> page = new Page<ProductStock>();
		this.setPageParams(page);
		page = stockService.getProductStockByPrdCode(page, code);
		
		List<City> cities = cityService.queryCitiesForSupport();
		
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/products_stocklist.Q?code=" + code;
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("productName", product.getName());
		request.setAttribute("productCode", code);
		request.setAttribute("cities", cities);
		return "stockList";
	}
	
	public void editStock() throws IOException{
		String cityCode = request.getParameter("cityCode");
		String prdCode = request.getParameter("prdCode");
		String stock = request.getParameter("stock");
		JSONObject json = new JSONObject();
		if(StringUtils.isEmpty(cityCode) || StringUtils.isEmpty(prdCode) || StringUtils.isEmpty(stock)){
			json.put("result", "failed");
		}else{
			stockService.saveOrUpdateProductStock(prdCode, Integer.parseInt(cityCode), Integer.parseInt(stock));
			json.put("result", "success");
		}
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT, user.getUsername(), "设置商品编码【" + prdCode + "】城市编" +
				"码【" + cityCode + "】的库存为" + stock + "件");
		systemLogService.saveSystemLog(systemLog);
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
		response.getWriter().flush();
		
	}
	
	public String save() throws IOException{
		products.setNormal(1);
		String opdescr = "";
		if(products.getId()==null||products.getId()<1){
			String prdCode = productsService.getMaxCodeByCatCode(products.getPrdCatCode());
			products.setPrdCode(prdCode);
			products.setCreateDate(new Date());
			products.setLastModifyDate(new Date());
			opdescr = "新增";
			
		}else{
			Products p = productsService.getById(products.getId());
			products.setLastModifyDate(new Date());
			products.setCreateDate(p.getCreateDate());
			products.setPicUrl1(p.getPicUrl1());
			products.setPicUrl2(p.getPicUrl2());
			products.setPicUrl3(p.getPicUrl3());
			products.setPicUrl4(p.getPicUrl4());
			products.setPicUrl5(p.getPicUrl5());
			opdescr = "编辑";
		}
		//先保存基本信息
		productsService.saveOrUpdateEntity(products);
		
		//取得需要上传的文件数组
		// -----------------------------上传照片部分-----------------------------
		if (myFileFileName != null) {
			uploadUserPic(products);
			productsService.saveOrUpdateEntity(products);
		}
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT, user.getUsername(), opdescr + "商品【" + products.getPrdCode() + "】");
		systemLogService.saveSystemLog(systemLog);
		return "ok";
	}
	
	
	/**
	 * 上传用户照片 
	 * @param user 
	 */
	private void uploadUserPic(Products prd) {
		// 为该用户创建一个文件夹
		String userDir = ServletActionContext.getServletContext().getRealPath("/private/UploadImages") + "/Prd"	+ prd.getId();
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 要更新或上传的图片序号
		String index1 = request.getParameter("pic1Index");
		String index2 = request.getParameter("pic2Index");
		String index3 = request.getParameter("pic3Index");
		String index4 = request.getParameter("pic4Index");
		String index5 = request.getParameter("pic5Index");
		List<String> indexList = new ArrayList<String>();
		if (!"0".equals(index1)) {
			indexList.add(index1);
		}
		if (!"0".equals(index2)) {
			indexList.add(index2);
		}
		if (!"0".equals(index3)) {
			indexList.add(index3);
		}
		if (!"0".equals(index4)) {
			indexList.add(index4);
		}
		if (!"0".equals(index5)) {
			indexList.add(index5);
		}
		// 用户的照片文件夹名称
		String udoc = "/Prd" + prd.getId() + "/";
		for (int i = 0; i < myFileFileName.length; i++) {
			// 图片的真实序号
			int n = Integer.valueOf(indexList.get(i)).intValue() - 1;
			String fName = myFileFileName[i];
			if (!StringUtils.isEmpty(fName)) {
				imageFileName = "pic" + (n + 1) + SysUtil.getExtention(fName);
				File destFile = new File(userDir + "/" + imageFileName);
				SysUtil.copy(myFile[i], destFile);
				// 显示刚刚上传的图片
				// getRequest().setAttribute("imgFile", imageFileName);
				if (n == 0) {
					prd.setPicUrl1(udoc + imageFileName);
				}
				if (n == 1) {
					prd.setPicUrl2(udoc + imageFileName);
				}
				if (n == 2) {
					prd.setPicUrl3(udoc + imageFileName);
				}
				if (n == 3) {
					prd.setPicUrl4(udoc + imageFileName);
				}
				if (n == 4) {
					prd.setPicUrl5(udoc + imageFileName);
				}
			}
		}
	}
	/**
	 * 上传爆款照片 
	 * @param user 
	 */
	private void uploadUserPic(OneProduct prd) {
		// 为该用户创建一个文件夹
		String userDir = ServletActionContext.getServletContext().getRealPath("/private/UploadImages") + "/OnePrd"	+ prd.getId();
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		// 要更新或上传的图片序号
		String index1 = request.getParameter("pic1Index");
		String index2 = request.getParameter("pic2Index");
		String index3 = request.getParameter("pic3Index");
		String index4 = request.getParameter("pic4Index");
		String index5 = request.getParameter("pic5Index");
		String index6 = request.getParameter("pic6Index");
		String index7 = request.getParameter("pic7Index");
		String index8 = request.getParameter("pic8Index");
		String index9 = request.getParameter("pic9Index");
		String index10 = request.getParameter("pic10Index");
		List<String> indexList = new ArrayList<String>();
		if (!"0".equals(index1)) {
			indexList.add(index1);
		}
		if (!"0".equals(index2)) {
			indexList.add(index2);
		}
		if (!"0".equals(index3)) {
			indexList.add(index3);
		}
		if (!"0".equals(index4)) {
			indexList.add(index4);
		}
		if (!"0".equals(index5)) {
			indexList.add(index5);
		}
		if (!"0".equals(index6)) {
			indexList.add(index6);
		}
		if (!"0".equals(index7)) {
			indexList.add(index7);
		}
		if (!"0".equals(index8)) {
			indexList.add(index8);
		}
		if (!"0".equals(index9)) {
			indexList.add(index9);
		}
		if (!"0".equals(index10)) {
			indexList.add(index10);
		}
		// 用户的照片文件夹名称
		String udoc = "/OnePrd" + prd.getId() + "/";
		for (int i = 0; i < myFileFileName.length; i++) {
			// 图片的真实序号
			int n = Integer.valueOf(indexList.get(i)).intValue() - 1;
			String fName = myFileFileName[i];
			if (!StringUtils.isEmpty(fName)) {
				imageFileName = "pic" + (n + 1) + SysUtil.getExtention(fName);
				File destFile = new File(userDir + "/" + imageFileName);
				SysUtil.copy(myFile[i], destFile);
				// 显示刚刚上传的图片
				// getRequest().setAttribute("imgFile", imageFileName);
				if (n == 0) {
					prd.setPic1(udoc + imageFileName);
				}
				if (n == 1) {
					prd.setPic2(udoc + imageFileName);
				}
				if (n == 2) {
					prd.setPic3(udoc + imageFileName);
				}
				if (n == 3) {
					prd.setPic4(udoc + imageFileName);
				}
				if (n == 4) {
					prd.setPic5(udoc + imageFileName);
				}
				if (n == 5) {
					prd.setPic6(udoc + imageFileName);
				}
				if (n == 6) {
					prd.setPic7(udoc + imageFileName);
				}
				if (n == 7) {
					prd.setPic8(udoc + imageFileName);
				}
				if (n == 8) {
					prd.setPic9(udoc + imageFileName);
				}
				if (n == 9) {
					prd.setPic10(udoc + imageFileName);
				}
			}
		}
	}
	
	
	/**
	 * 进入打造爆款页面 
	 * @return
	 */
	public String oneProduct(){
		OneProductDao onePrdDao = (OneProductDao) ServerBeanFactory.getBean("onePrdDao");
		OneProduct one = onePrdDao.getUniqueEntityByOneProperty("orgId", this.getCurrentOrgAdmin().getId());
		request.setAttribute("one", one);
		return "onePrd";
	}
	/**
	 * 打造爆款页面保存 
	 * @return
	 */
	public String saveOnePrd(){
		String opdescr = "";
		if(one.getId()==null||one.getId()<1){
			one.setOrgId(this.getCurrentOrgAdmin().getId());
			one.setPublishDate(DateUtil.getLongCurrentDate());
			opdescr = "新增";
		}else{
			String prdCode = one.getPrdCode();
			String pic1PrdCode = one.getPic1PrdCode();
			String pic2PrdCode = one.getPic2PrdCode();
			String pic3PrdCode = one.getPic3PrdCode();
			String pic4PrdCode = one.getPic4PrdCode();
			String pic5PrdCode = one.getPic5PrdCode();
			String pic6PrdCode = one.getPic6PrdCode();
			String pic7PrdCode = one.getPic7PrdCode();
			String pic8PrdCode = one.getPic8PrdCode();
			String pic9PrdCode = one.getPic9PrdCode();
			String pic10PrdCode = one.getPic10PrdCode();
			OneProduct p = productsService.getOnePrdById(one.getId());
			try {
				BeanUtils.copyProperties(one, p);
			} catch (Exception e) {
				e.printStackTrace();
			}
			one.setPrdCode(prdCode);
			one.setPic1PrdCode(pic1PrdCode);
			one.setPic2PrdCode(pic2PrdCode);
			one.setPic3PrdCode(pic3PrdCode);
			one.setPic4PrdCode(pic4PrdCode);
			one.setPic5PrdCode(pic5PrdCode);
			one.setPic6PrdCode(pic6PrdCode);
			one.setPic7PrdCode(pic7PrdCode);
			one.setPic8PrdCode(pic8PrdCode);
			one.setPic9PrdCode(pic9PrdCode);
			one.setPic10PrdCode(pic10PrdCode);
			one.setPublishDate(DateUtil.getLongCurrentDate());
			opdescr = "编辑";
		}
		//先保存基本信息
		productsService.saveOrUpdateOnePrd(one);
		
		//取得需要上传的文件数组
		// -----------------------------上传照片部分-----------------------------
		if (myFileFileName != null) {
			uploadUserPic(one);
			productsService.saveOrUpdateOnePrd(one);
		}
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT, user.getUsername(), opdescr + "爆款商品【" + one.getTitle() + "】");
		systemLogService.saveSystemLog(systemLog);
		
		return "onePrd";
	}
	
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		productsService.deleteByIds(idList);
		
		Admin user = getCurrentAdminUser();
		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PRODUCT, user.getUsername(), "删除商品");
		systemLogService.saveSystemLog(systemLog);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 修改销量 
	 * @return
	 * @throws IOException 
	 */
	public String modSaleCount() throws IOException{
		String prdId = request.getParameter("prdId");
		String saleCount = request.getParameter("saleCount");
		try {
			productsService.modSaleCount(prdId, saleCount);
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("fail");
		}
		return null;
	}
	

	/**
	 * 生成会员略图并使大图带上水印，大幅提高浏览速度 
	 * @return
	 */
	public String reducePic(){
		String bigUrl = this.getAbsoluteRootPath() + "/private/UploadImages";
//		String bigUrl = this.getAbsoluteRootPath() + "/private/test";
		
		MarkConfig config = new MarkConfig();
		config.setAlpha(0.5f);
		config.setSrcImgType("1");//1-本地 ，2 -网络
		config.setColor("#FF69B4");
		config.setMarkText("吴方商城");//水印文字
//		config.setFontSize(200);
//		config.setOutputImageDir("d:/test/output3");
		config.setRootPath(bigUrl);
		try {
			MarkTool.batchMarkImageByText(config);
			
			//缩小图片88*100  自动在该会员目录下生成一个thunmb.jpg缩略图，
			MarkTool.reduceImage(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "tool";
	}
	
	public String tool(){
		return "tool";
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}

	public String[] getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String[] myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String[] getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String[] myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	
	public OneProduct getOne() {
		return one;
	}

	
	public void setOne(OneProduct one) {
		this.one = one;
	}
	

	
}
