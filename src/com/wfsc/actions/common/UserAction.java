package com.wfsc.actions.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import model.bo.drug.DrugScoreLog;
import model.bo.drug.DrugUDRelation;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugScoreLogService;
import sun.misc.BASE64Encoder;
import util.CommonUtil;

import com.base.action.DispatchPagerAction;
import com.base.log.LogUtil;
import com.base.util.Page;
import com.wfsc.actions.vo.PicRecObj;
import com.wfsc.common.bo.system.City;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;
import com.wfsc.services.city.ICityService;

@Controller("UserAction")
@Scope("prototype")
public class UserAction extends DispatchPagerAction {

	private static final long serialVersionUID = -1039247888666512706L;

	private Logger logger = LogUtil.getLogger(LogUtil.SYSTEM_LOG_ID);
	
	@Resource(name = "userService")
	private IUserService userService;
	@Resource(name="drugScoreLogService")
	private IDrugScoreLogService drugScoreLogService ;

	private File[] myFile;

	private String[] myFileContentType;

	private String[] myFileFileName;

	private String imageFileName;
	
	private User user;
	
	private String certType;
	
	private PicRecObj picobj;

	

	public User getUser() {
		return user;
	}
	 
 
	 
//	/**
//	 * 生成,发送验证码
//	 * @return
//	 */
//	public String sendVerifyCode(){
//		Long userId = this.getCurrentUser().getId();
//		String tel = this.getRequest().getParameter("telephone");
//		//生成随机6位验证码并存入用户表
//		String verifyCode = SysUtil.createVerifyCode();
//		logger.info(tel + " - " + verifyCode);
//		try{
//			boolean isOk = this.userService.updateUserTelVerifyCode(userId, tel, verifyCode);
//			String isMock = Version.getInstance().getNewProperty("mock");
//			//如果是测试,不调用短信接口,直接返回
//			if("true".equals(isMock)){
//				return "sendFinish";
//			}
//			
//			if(isOk){
//				//发送验证码到用户手机
//				Sender.getInstance().sendVerifyCode(verifyCode, tel);
//			} else {
//				return "error";
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return "sendFinish";
//	}
	
	private static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}
	
	
	public String photo(){
		System.out.println("照片管理....");
		return SUCCESS;
	}
	/**
	 * 修改密码 
	 * @return
	 */
	public String modifyPass(){
		String flag = request.getParameter("flag");
		if("enter".equals(flag)){
			return SUCCESS;
		}
		String oldPass = request.getParameter("oldPass");
		String newPass = request.getParameter("newPass");
		String confirmPass = request.getParameter("confirmPass");
		if(!newPass.equals(confirmPass)){
			request.setAttribute("info", "对不起, 两次密码输入不匹配, 请重新输入!");
			return ERROR;
		}
		//加密后的密码
		String encrptPassword = new BASE64Encoder().encode(oldPass.getBytes());
		if(!this.getCurrentUser().getPassword().equals(encrptPassword)){
			request.setAttribute("info", "对不起, 旧密码输入错误, 请重新输入!");
		}else{
			this.userService.modifyPass(this.getCurrentUser().getId(), newPass);
			request.setAttribute("info", "密码修改成功!");
		}
		return SUCCESS;
	}
	
	public String manager(){
		list();
		return "manager";
	}
	
	public String list(){
		Page<User> page = new Page<User>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String status = request.getParameter("status");
		String realName = request.getParameter("realName");
		String ageStart = request.getParameter("ageStart");
		String ageEnd = request.getParameter("ageEnd");
		String mobilePhone = request.getParameter("mobilePhone");
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String address = request.getParameter("address");
		String auditStatus = request.getParameter("auditStatus");
		String sex = request.getParameter("sex");
		// 根据用户标识查询：患者/医生/护士列表
		String userType = request.getParameter("userType");
		if(StringUtils.isNotEmpty(status)){
			paramap.put("status", status);
			request.setAttribute("status", status);
		}
		if(StringUtils.isNotEmpty(realName)){
			paramap.put("realName", realName);
			request.setAttribute("realName", realName);
		}
		if(StringUtils.isNotEmpty(sex)){
			paramap.put("sex", sex);
			request.setAttribute("sex", sex);
		}
		if(StringUtils.isNotEmpty(ageStart)){
			paramap.put("ageStart", ageStart);
			request.setAttribute("ageStart", ageStart);
		}
		if(StringUtils.isNotEmpty(ageEnd)){
			paramap.put("ageEnd", ageEnd);
			request.setAttribute("ageEnd", ageEnd);
		}
		if(StringUtils.isNotEmpty(mobilePhone)){
			paramap.put("mobilePhone", mobilePhone);
			request.setAttribute("mobilePhone", mobilePhone);
		}
		if(StringUtils.isNotEmpty(province)){
			paramap.put("province", province);
			request.setAttribute("province", province);
		}
		if(StringUtils.isNotEmpty(city)){
			paramap.put("city", city);
			request.setAttribute("city", city);
		}
		if(StringUtils.isNotEmpty(address)){
			paramap.put("address", address);
			request.setAttribute("address", address);
		}
		if(StringUtils.isNotEmpty(auditStatus)){
			paramap.put("auditStatus", auditStatus);
			request.setAttribute("auditStatus", auditStatus);
		}
		if(StringUtils.isNotEmpty(userType)){
			paramap.put("userType", userType);
			request.setAttribute("userType", userType);
		}
		
		page = userService.findForPage(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/user_list.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("userlist", page.getData());
		return "list";
	}
	public String save() throws IOException{
		userService.saveOrUpdateEntity(user);
		return "ok";
	}
	public String deleteByIds(){
		String ids = request.getParameter("ids");
		String[] idArray = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for(String id : idArray){
			idList.add(Long.valueOf(id));
		}
		userService.deleteByIds(idList);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	
	public String detail() {
		String id = request.getParameter("id");
		user = userService.getUserById(Long.valueOf(id));
		int status = user.getStatus();
		String statusStr = "启用";
		if(0==status){
			statusStr = "禁用";
		}
		user.setStatusStr(statusStr);
		return "detail";
	}
	public String enableAccount(){
		String adminIds = request.getParameter("ids");
		String[] ids = adminIds.split(",");
		userService.enableUsers(ids);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public String disableAccount(){
		String adminIds = request.getParameter("ids");
		String[] ids = adminIds.split(",");
		userService.disableUsers(ids);
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 进入用户审核页面
	 * @return
	 */
	public String setAuditStatus(){
		String userIds = request.getParameter("userIds");
		request.setAttribute("userIds", userIds);
		return "audit";
	}

	/**
	 * 进入用户审核页面
	 * @return
	 */
	public String saveAuditStatus(){
		try {
			User user=null;
			String userIds = request.getParameter("userIds");
			Long referrerId=(long) 0;
			String auditStatus = request.getParameter("auditStatus");
			System.out.println(auditStatus+"tttttttttttttttt");
			List<Long> ids = CommonUtil.string2LongList(userIds, ",");
			String [] id=userIds.split(",");
			if (auditStatus.equals("41")) {
			    for (int i = 0; i < id.length; i++) {
				 user=userService.getUserById(Long.valueOf(id[i]));
					DrugUDRelation udRelation=userService.findDrugUDRelationByUId(user.getId().toString());
					if (udRelation!=null) {
						referrerId=udRelation.getReferrerId();
				}
				if(referrerId!=null && referrerId!=0){
						DrugScoreLog drugScoreLog=new DrugScoreLog();
						user=userService.getUserById(Long.valueOf(referrerId));
						drugScoreLog.setOpenId(user.getOpenId());
						drugScoreLog.setScore(IDrugScoreLogService.SCORE_PHYSICIAN_CERTIFICATION);
						drugScoreLog.setAction(IDrugScoreLogService.ACTION_ADD);
						drugScoreLog.setOpdate(new Date());
						drugScoreLog.setSource(IDrugScoreLogService.SOURC_ACTION_AUDITING_INFO);
						drugScoreLogService.saveOrUpdateDrugScore(drugScoreLog);
						
					}else{
						DrugScoreLog drugScoreLog=new DrugScoreLog();
						drugScoreLog.setOpenId(user.getOpenId());
						drugScoreLog.setScore(IDrugScoreLogService.SCORE_PHYSICIAN_CERTIFICATION);
						drugScoreLog.setAction(IDrugScoreLogService.ACTION_ADD);
						drugScoreLog.setOpdate(new Date());
						drugScoreLog.setSource(IDrugScoreLogService.SOURC_ACTION_AUDITING_INFO);
						drugScoreLogService.saveOrUpdateDrugScore(drugScoreLog);
				}
			}
		}
			
			userService.updateAuditStatus(ids, Integer.valueOf(auditStatus));
			response.getWriter().write("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取用户积分详细
	 * @return
	 */
	
	public String getUserPoints(){
		try{
		String userid=request.getParameter("userid");
		User user=userService.getUserById(Long.valueOf(userid));
		request.setAttribute("user",user);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return "pointsDetail";
		
	}
	
	/**
	 *  获取积分列表管理
	 * @return
	 */
	
	public String pointsObtainManager(){
		 pointsObtainList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "pointsObtainManager";
		
	}
	
	
	/**
	 * 用户获取积分列表
	 * @return
	 */
	public String pointsObtainList(){
		Page<DrugScoreLog> page = new Page<DrugScoreLog>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String openid = request.getParameter("openid");
		User user=userService.getUserByOpenid(openid);
		if(user!=null){
			request.setAttribute("user", user);
		}
		if(StringUtils.isNotEmpty(openid)){
			paramap.put("openId", openid);
			request.setAttribute("openid", openid);
		}
		
		page = drugScoreLogService.findPageObtainForDrugScoreLog(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/user_pointsObtainList.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("obtainList", page.getData());
		request.setAttribute("pop", request.getParameter("pop"));
		return "pointsObtainList";
	}
	/**
	 * 兑换积分列表管理
	 * @return
	 */
	public String pointsExchangeManager(){
		pointsExchangeList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "pointsExchangeManager";
		
	}
	/**
	 * 兑换积分列表
	 * @return
	 */
	
	public String pointsExchangeList(){
		
		Page<DrugScoreLog> page = new Page<DrugScoreLog>();
		Map<String,Object> paramap = new HashMap<String,Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String openid = request.getParameter("openid");
		User user=userService.getUserByOpenid(openid);
		if(user!=null){
			request.setAttribute("user", user);
		}
		if(StringUtils.isNotEmpty(openid)){
			paramap.put("openId", openid);
			request.setAttribute("openid", openid);
		}
		
		page = drugScoreLogService.findPageExchangeForDrugScoreLog(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/user_pointsExchangeList.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("exchangeList", page.getData());
		request.setAttribute("pop", request.getParameter("pop"));
		return "pointsExchangeList";
	}
	/**
	 * 保存兑换记录并更新用户总积分
	 * @return
	 */
	public String saveUpatePoints(){
		String userid=request.getParameter("userid");
		String openid=request.getParameter("openid");
		String consumepoints=request.getParameter("consumepoints");
		String surpluspoints=request.getParameter("surpluspoints");
		//保存兑换积分记录
		DrugScoreLog drugScoreLog=new DrugScoreLog();
		drugScoreLog.setOpenId(openid);
		drugScoreLog.setScore(Integer.valueOf(consumepoints));
		drugScoreLog.setAction(IDrugScoreLogService.ACTION_DECREASE);
		drugScoreLog.setOpdate(new Date());
		drugScoreLog.setSource(IDrugScoreLogService.SOURC_ACTION_EXCHANGE_CODE);
		drugScoreLogService.saveOrUpdateDrugScore(drugScoreLog);
		//更新用户总积分
		User user=userService.getUserById(Long.valueOf(userid));
		if(user!=null && !consumepoints.equals(""))
		{
			user.setPoints(Integer.parseInt(surpluspoints));
			userService.updateUser(user);
		}
		return "ok";
		
	}
	
	
	
	
	
	//------------------->>>>>>>>>>>>>>>>>>>>>>> 微信平台  <<<<<<<<<<<<<<<<<<<<<<<<<--------------------

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
	public void setDrugScoreLogService(IDrugScoreLogService drugScoreLogService) {
		this.drugScoreLogService = drugScoreLogService;
	}



	public File[] getMyFile() {
		return myFile;
	}

	public void setMyFile(File[] myFile) {
		this.myFile = myFile;
	}
	public void setMyFileContentType(String[] contentType) {
		this.myFileContentType = contentType;
	}

	public void setMyFileFileName(String[] fileName) {
		this.myFileFileName = fileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	
	public String[] getMyFileContentType() {
		return myFileContentType;
	}

	
	public String[] getMyFileFileName() {
		return myFileFileName;
	}
	
	public String getCertType() {
		return certType;
	}

	
	public void setCertType(String certType) {
		this.certType = certType;
	}

	
	public PicRecObj getPicobj() {
		return picobj;
	}

	
	public void setPicobj(PicRecObj picobj) {
		this.picobj = picobj;
	}
}
