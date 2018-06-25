package actions.drug;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugQualification;
import model.bo.drug.DrugScoreLog;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugQualificationService;
import service.drug.IDrugScoreLogService;
import util.UploadUtil;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
import com.wfsc.common.bo.user.User;
import com.wfsc.common.bo.user.WeiChat;
import com.wfsc.daos.WeiChatDao;
import com.wfsc.services.account.IUserService;
/**
 * 医疗专业认证 action
 *
 *
 */
@Controller("DrugQualificationAction")
@Scope("prototype")
public class DrugQualificationAction extends DispatchPagerAction {
	private static final long serialVersionUID = 1526078947340698666L;
	//医疗专业认证Service
	@Autowired
	private IDrugQualificationService drugQualificationService;
	
	private DrugQualification qualification;
	
	private File qualificationPic;
	
	@Autowired
	private IDrugScoreLogService drugScoreLogService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private WeiChatDao weiChatDao;
	
	
	/**
	 * 通过用户id查看医疗认证信息
	 * @return
	 */
	public String getQualificationByUserId(){
		String userid=request.getParameter("userid");
		if(userid!=null&&!userid.equals("")){
			DrugQualification qualification=drugQualificationService.getQualificationByUserId(userid);		
			request.setAttribute("qualification", qualification);
			request.setAttribute("readpath", UploadUtil.getImgUrl());
			request.setAttribute("userid", userid);
		}
		return "certificate";
	}
	
	
	/**
	 * 保存或者修改认证信息
	 * @return
	 */
	public String saveQualification(){
		try{	
			response.setCharacterEncoding("UTF-8");
			String qualification_id=request.getParameter("id");
			String hospital=request.getParameter("hospital");
			String office=request.getParameter("office");
			String professionalTitle=request.getParameter("professionalTitle");
			String userid=request.getParameter("userid");
			String qualificationPic=request.getParameter("pic");
			DrugQualification qualification;
			if(qualification_id!=null&&!qualification_id.equals("")){
				qualification=drugQualificationService.getQualificationById(Long.valueOf(qualification_id));
			}else{
				qualification=new DrugQualification();				
			}
			qualification.setReviewState(0);//认证修改后需要重新审核，认证审核状态默认未审核
			qualification.setHospital(hospital);
			qualification.setOffice(office);
			qualification.setProfessionalTitle(professionalTitle);
			qualification.setUserId(Long.valueOf(userid));
			if(qualificationPic!=null&&!qualificationPic.equals("")){
				qualification.setQualificationPic(qualificationPic);
			}			
			drugQualificationService.saveOrUpdateQualification(qualification);
			response.getWriter().write("true");
		}catch(Exception e){
			e.printStackTrace();			
		}
		return null;
	}

	/**
	 * 上传认证图片
	 * @return
	 */
	public String uploadQualification(){
		try{
			response.setContentType("text/html");
			String picpath="";
			if (qualificationPic!=null) {
				 picpath=UploadUtil.upLoadImage(qualificationPic, "qualification");
			}
			response.getWriter().write(picpath);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 认证管理
	 * @return
	 */
	public String qualManager(){
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "qualificationManager";
	}
	
	/**
	 * 认证列表
	 * @return
	 */
	public String getList(){
		try{
			Page<DrugQualification> page = new Page<DrugQualification>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String hospital = request.getParameter("hospital");
			if (StringUtils.isNotEmpty(hospital)) {
				paramap.put("hospital", hospital);
				request.setAttribute("hospital", hospital);
			}

			String office = request.getParameter("office");
			if (StringUtils.isNotEmpty(office)) {
				paramap.put("office", office);
				request.setAttribute("office", office);
			}
			String professionalTitle = request.getParameter("professionalTitle");
			if (StringUtils.isNotEmpty(professionalTitle)&&!professionalTitle.equals("100")) {
				paramap.put("professionalTitle", professionalTitle);
				request.setAttribute("professionalTitle", professionalTitle);
			}
			String reviewState = request.getParameter("reviewState");
			if (StringUtils.isNotEmpty(reviewState)&&!reviewState.equals("100")) {
				paramap.put("reviewState", reviewState);
				request.setAttribute("reviewState", reviewState);
			}
	
			page = drugQualificationService.findPageForQualification(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath() + "/admin/qualification_getList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("qulifiList", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "qualificationList";
	}
	
	/**
	 * 查看认证信息
	 * @return
	 */
	public String getDrugQualification(){
		try{
			String id=request.getParameter("id");
			Object[] ob=(Object[])drugQualificationService.findQualificationById(id);
			String imagesmall=null;
			if(ob!=null){
				String imgpath=ob[7].toString();
				imagesmall=imgpath.replace(".png", "")+"SMALL.png";
			}
			request.setAttribute("readpath", UploadUtil.getImgUrl());
			System.out.println(UploadUtil.getImgUrl());
			request.setAttribute("smallpic",imagesmall);
			request.setAttribute("qualification", ob);
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "qualificationdetail";
	}
	
	/**
	 * 审核认证照片
	 * @return
	 */
	public String reviewQualification(){
		try{
			DrugQualification quli=drugQualificationService.getQualificationById(qualification.getId());
			quli.setReviewState(qualification.getReviewState());
			
			//审核通过添加积分1000
			DrugScoreLog score=new DrugScoreLog();
			User user=userService.getUserById(quli.getUserId());	
			//判断是否已经添加过积分
			boolean isexist=drugScoreLogService.isExsit(user.getOpenId(), "3");
			if(!isexist&&qualification.getReviewState()==1){
				//不存在时，并且是审核通过的状态，加分
				score.setOpenId(user.getOpenId());
				score.setAction(IDrugScoreLogService.ACTION_ADD);
				score.setScore(IDrugScoreLogService.SCORE_PHYSICIAN_CERTIFICATION);
				score.setSource(IDrugScoreLogService.SOURC_ACTION_PHYSICIAN_CERTIFICATION);
				score.setOpdate(new Date());
				drugScoreLogService.saveOrUpdateDrugScore(score);
			}
			drugQualificationService.saveOrUpdateQualification(quli);
			//更新用户总积分
			user.setPoints(user.getPoints()+IDrugScoreLogService.SCORE_PHYSICIAN_CERTIFICATION);
			userService.saveOrUpdateEntity(user);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "ok";
	}
	
	public String imageWordsManager() {
		getNewsList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "newsExamineIndex";
	}
	
	/**
	 * 审核图片消息列表
	 * @return
	 */
	public String getNewsList(){
		try{
			Page<Object> page = new Page<Object>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String hospital = request.getParameter("hospital");
			if (StringUtils.isNotEmpty(hospital)) {
				paramap.put("hospital", hospital);
				request.setAttribute("hospital", hospital);
			}
			String reviewState = request.getParameter("reviewState");
			if (StringUtils.isNotEmpty(reviewState) && !reviewState.equals("100")) {
				paramap.put("reviewState", reviewState);
				request.setAttribute("reviewState", reviewState);
			}
			String startTransDate = request.getParameter("startTransDate");
			if (StringUtils.isNotEmpty(startTransDate)) {
				paramap.put("startTransDate", startTransDate);
				request.setAttribute("startTransDate", startTransDate);
			}
			String endTransDate = request.getParameter("endTransDate");
			if (StringUtils.isNotEmpty(endTransDate)) {
				paramap.put("endTransDate", endTransDate);
				request.setAttribute("endTransDate", endTransDate);
			}
			page = drugQualificationService.findPageForNews(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath() + "/admin/qualification_getNewsList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("qulifiList", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			//获取图片路径
			request.setAttribute("imgurlpath",UploadUtil.getImgUrl());
		}catch(Exception e){
			e.printStackTrace();
		}		
		return "newsExamineList";
	}
	
	/**
	 * 查看消息信息
	 * @return
	 */
	public String getNewsExamine(){
		try{
			String id=request.getParameter("id");
			Object[] ob=(Object[])weiChatDao.findNewsById(id);
			String imagesmall=null;
			if(ob!=null){
				imagesmall=ob[6].toString();
			}
			request.setAttribute("readpath", UploadUtil.getImgUrl());
			System.out.println(UploadUtil.getImgUrl());
			request.setAttribute("smallpic",imagesmall);
			request.setAttribute("qualification", ob);
			//获取图片路径
			request.setAttribute("imgurlpath",UploadUtil.getImgUrl());
		}catch(Exception e){
			e.printStackTrace();
		}	
		return "newsExamine";
	}
	
	/**
	 * 审核图片消息
	 * @return
	 */
	public String reviewNewsExmaine(){
		try{
			WeiChat weicht = drugQualificationService.getNewsEntityById(qualification.getId());
			WeiChat weiChat = new WeiChat();
			weiChat.setReviewState(qualification.getReviewState());
			weiChat.setId(qualification.getId());
			weiChat.setToUserName(weicht.getToUserName());
			weiChat.setFromUserName(weicht.getFromUserName());
			weiChat.setMediaId(weicht.getMediaId());
			weiChat.setMsgId(weicht.getMsgId());
			weiChat.setMsgType(weicht.getMsgType());
			weiChat.setPicUrl(weicht.getPicUrl());
			weiChat.setCreateTime(weicht.getCreateTime());
			drugQualificationService.updateNewsById(weiChat);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "okNews";
	}
	
	public void setDrugQualificationService(
			IDrugQualificationService drugQualificationService) {
		this.drugQualificationService = drugQualificationService;
	}



	public DrugQualification getQualification() {
		return qualification;
	}



	public void setQualification(DrugQualification qualification) {
		this.qualification = qualification;
	}


	public File getQualificationPic() {
		return qualificationPic;
	}


	public void setQualificationPic(File qualificationPic) {
		this.qualificationPic = qualificationPic;
	}


	public void setDrugScoreLogService(IDrugScoreLogService drugScoreLogService) {
		this.drugScoreLogService = drugScoreLogService;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	
}
