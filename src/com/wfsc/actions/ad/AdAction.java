package com.wfsc.actions.ad;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import model.bo.auth.Org;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.intf.AdminService;

import com.base.ServerBeanFactory;
import com.base.action.DispatchPagerAction;
import com.base.exception.CupidRuntimeException;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.common.bo.ad.AdvConfig;
import com.wfsc.common.bo.system.SystemLog;
import com.wfsc.common.bo.user.Admin;
import com.wfsc.services.ad.IAdService;
import com.wfsc.services.system.ISystemLogService;
import com.wfsc.util.SysUtil;

/**
 * 广告维护后台
 * @author Xiaojiapeng
 *
 */
@SuppressWarnings("unchecked")
@Controller("AdAction")
@Scope("prototype")
public class AdAction extends DispatchPagerAction {
	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	private static final long serialVersionUID = 5145438857558748715L;
	
	@Resource(name = "adService")
	private IAdService adService;
	
	@Autowired
	private ISystemLogService systemLogService;
	
	private AdvConfig advConfig;
	
	private Org org;
	
	private String picName;
	private String bannerName;
	private String logoName;
	private String hongbaoName;
	
	private File picFile;
	private File bannerFile;
	private File hongbaoFile;
	private File logoFile;
	
	
	public File getBannerFile() {
		return bannerFile;
	}

	
	public void setBannerFile(File bannerFile) {
		this.bannerFile = bannerFile;
	}

	
	public File getLogoFile() {
		return logoFile;
	}

	
	public void setLogoFile(File logoFile) {
		this.logoFile = logoFile;
	}

	public IAdService getAdService() {
		return adService;
	}

	public void setAdService(IAdService adService) {
		this.adService = adService;
	}

	public AdvConfig getAdvConfig() {
		return advConfig;
	}

	public void setAdvConfig(AdvConfig advConfig) {
		this.advConfig = advConfig;
	}

	public File getPicFile() {
		return picFile;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	/**
	 * 广告管理页面
	 * @return
	 */
	public String index(){
		try{
			list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "index";
	}
	
	/**
	 * 仅显示总销商的广告列表 table部分
	 * @return
	 */
	public String list(){
		Page<AdvConfig> page = super.getPage();
		page.setPaginationSize(7);
		
		String adType = request.getParameter("adType");
		int type = 0;
		if(StringUtils.isNotEmpty(adType))
			type = Integer.parseInt(adType);
		
		page = adService.queryAllAds(page, type, this.getCurrentOrgAdmin().getId());
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/ad_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("adType", adType);
		return "list";
	}
	
	public String input() {
		AdvConfig advConfig=null;
		String id =(String) request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			advConfig= new AdvConfig();
		}else{
			advConfig = adService.getAdById(Long.valueOf(id));
			if(advConfig == null)
				throw new CupidRuntimeException("需要编辑的广告不存在或者已被删除");
		}
		request.setAttribute("advConfig", advConfig);
		
		request.setAttribute("orgId", this.getCurrentOrgAdmin().getId());
		return "input";
	}
	
	public String save(){
		String fileName = "";
		//编辑不更新图片
		if(advConfig.getId() == null){
			if(picFile == null || StringUtils.isBlank(picName))
				throw new CupidRuntimeException("请选择广告图片后保存");
			
			fileName = UUID.randomUUID().toString().replaceAll("-", "") + picName.substring(picName.lastIndexOf("."));
			advConfig.setPicUrl("/upload/org-"+this.getCurrentOrgAdmin().getId() + "/ppt/" +fileName);
		}
		
		adService.saveOrUpdateAd(advConfig, this.getCurrentOrgAdmin().getId());
		
		Admin admin = getCurrentAdminUser();
		String adType = advConfig.getAdType() == 1 ? "普通广告":"幻灯片广告";
		
		
		//保存成功后，再将图片上传到服务器上面
		if(StringUtils.isNotEmpty(fileName)){
			
			uploadWxMallPic(fileName, this.getCurrentOrgAdmin());
			
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_ADV, admin.getUsername(), "新增【" + adType + "】" + advConfig.getUrl());
			systemLogService.saveSystemLog(systemLog);
		}else{
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_ADV, admin.getUsername(), "编辑【" + adType + "】" + advConfig.getUrl());
			systemLogService.saveSystemLog(systemLog);
		}
		
		return SUCCESS;
	}
	
	
	/**
	 * 分销商微商城logo设置 
	 * @return
	 */
	public String fxLogo(){
		//把数据库里的值取出来
		Org org = this.getCurrentOrgAdmin();
		request.setAttribute("org", org);
		return "fxLogo";
	}
	/**
	 * 红包个性化设置 
	 * @return
	 */
	public String hbconfig(){
		//把数据库里的值取出来
		Org org = this.getCurrentOrgAdmin();
		request.setAttribute("org", org);
		return "hbconfig";
	}
	/**
	 * 分销商微商城logo,banner设置 
	 * 不同的总销商logo和banner各一张，命名固定： /upload/org-xx/banner/logo.xx  /upload/org-xx/banner/banner.xx  
	 * @return
	 */
	public String fxLogoSave(){
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Org orgBycode = service.getOrgBycode(this.getCurrentOrgCode());
		String bannerName = "/upload/org-"+orgBycode.getId()+"/banner/banner." + this.bannerName.split("\\.")[1];
		String logoname = "/upload/org-"+orgBycode.getId()+"/banner/logo." + this.logoName.split("\\.")[1];
		orgBycode.setWxMallIndexBanner(bannerName);
		orgBycode.setWxMallLogo(logoname);
		boolean b = service.updateOrg(orgBycode);
		
		//保存成功后，再将图片上传到服务器上面
		if(b && StringUtils.isNotEmpty(bannerName) && StringUtils.isNotEmpty(logoname)){
			
			uploadBannerPic("banner."+this.bannerName.split("\\.")[1], this.getCurrentOrgAdmin());
			uploadLogoPic("logo." + this.logoName.split("\\.")[1], this.getCurrentOrgAdmin());
			
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_ADV, this.getCurrentAdminUser().getUsername(), "新增banner和logo图片。");
			systemLogService.saveSystemLog(systemLog);
		}
		
		return this.fxLogo();
	}
	/**
	 * 红包背景图设置： /upload/org-xx/hongbao/hongbao.xx   
	 * @return
	 */
	public String hongbaoBgSave(){
		if(!this.hongbaoName.split("\\.")[1].equalsIgnoreCase("gif") && !this.hongbaoName.split("\\.")[1].equalsIgnoreCase("png") && !this.hongbaoName.split("\\.")[1].equalsIgnoreCase("jpg")){
			request.setAttribute("info", "只支持gif，png，jpg格式。");
			return "info";
		}
		AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
		Org orgBycode = service.getOrgBycode(this.getCurrentOrgCode());
		String hongbaoBg = "/upload/org-"+orgBycode.getId()+"/hongbao/hongbao." + this.hongbaoName.split("\\.")[1];
		orgBycode.setHongbaoPic(hongbaoBg);
		boolean b = service.updateOrg(orgBycode);
		
		//保存成功后，再将图片上传到服务器上面
		if(b && StringUtils.isNotEmpty(hongbaoName)){
			
			uploadHongBaoPic("hongbao."+this.hongbaoName.split("\\.")[1], this.getCurrentOrgAdmin());
			
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_ADV, this.getCurrentAdminUser().getUsername(), "变更红包背景图片。");
			systemLogService.saveSystemLog(systemLog);
		}
		
		return this.hbconfig();
	}
	
	

	/**
	 * 上传分销商照片 
	 * @param user 
	 */
	private void uploadWxMallPic(String fileName, Org org) {
		// 为该用户创建一个文件夹
		String userDir = this.getUserPicPath(request, "org-"+org.getId()+"/ppt/");
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		 
		//目标文件名
		File destFile = new File(userDir + "/" + fileName);
		SysUtil.copy(picFile, destFile);
		logger.info("上传微商城幻灯片图片结束////");
	}
	/**
	 * 上传banner照片 
	 * @param user 
	 */
	private void uploadBannerPic(String fileName, Org org) {
		// 为该用户创建一个文件夹
		String userDir = this.getUserPicPath(request, "org-"+org.getId()+"/banner/");
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		//目标文件名
		File destFile = new File(userDir + "/" + fileName);
		SysUtil.copy(this.bannerFile, destFile);
		logger.info("上传banner结束////");
	}
	/**
	 * 上传红包背景图片 
	 * @param fileName
	 * @param org
	 */
	private void uploadHongBaoPic(String fileName, Org org) {
		// 为该用户创建一个文件夹
		String userDir = this.getUserPicPath(request, "org-"+org.getId()+"/hongbao/");
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		//目标文件名
		File destFile = new File(userDir + "/" + fileName);
		SysUtil.copy(this.hongbaoFile, destFile);
		logger.info("上传hongbao结束////");
	}
	/**
	 * 上传logo照片 
	 * @param user 
	 */
	private void uploadLogoPic(String fileName, Org org) {
		// 为该用户创建一个文件夹
		String userDir = this.getUserPicPath(request, "org-"+org.getId()+"/banner/");
		File dir = new File(userDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		//目标文件名
		File destFile = new File(userDir + "/" + fileName);
		SysUtil.copy(this.logoFile, destFile);
		logger.info("上传LOGO结束////");
	}
	
	/**
 	 * 更新首页列表方式 
	 * @return
	 * @throws IOException 
	 */
	public String updateIndexShow() throws IOException{
		PrintWriter out = response.getWriter();
		try{
			AdminService service = (AdminService) ServerBeanFactory.getBean("adminService");
			String indexShow = request.getParameter("indexShow");
			service.updateIndexShow(this.getCurrentOrgAdmin(), indexShow);
			//更新session
			this.getCurrentOrgAdmin().setIndexShow(Integer.valueOf(indexShow));
			
			out.write("{\"result\":\"ok\"}");
		}catch(Exception e){
			out.write("{\"result\":\"fail\"}");
		}
		return null;
	}
	
	
	/**
	 * 删除广告，支持批量删除
	 */
	public void deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		JSONObject result = new JSONObject();
		try {
			adService.deleteAdByIds(idList);
			
			Admin admin = getCurrentAdminUser();
			
			SystemLog systemLog = new SystemLog(SystemLog.MODULE_ADV, admin.getUsername(), "删除广告");
			systemLogService.saveSystemLog(systemLog);
			
			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "failed");
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(result.toString());
			response.getWriter().flush();
		} catch (IOException e) {
		}
	}

	
	public Org getOrg() {
		return org;
	}

	
	public void setOrg(Org org) {
		this.org = org;
	}


	
	public String getBannerName() {
		return bannerName;
	}


	
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}


	
	public String getLogoName() {
		return logoName;
	}


	
	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}


	
	public String getHongbaoName() {
		return hongbaoName;
	}


	
	public void setHongbaoName(String hongbaoName) {
		this.hongbaoName = hongbaoName;
	}


	
	public File getHongbaoFile() {
		return hongbaoFile;
	}


	
	public void setHongbaoFile(File hongbaoFile) {
		this.hongbaoFile = hongbaoFile;
	}



}
