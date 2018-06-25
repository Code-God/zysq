package actions.drug;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import model.bo.auth.Org;
import model.bo.drug.DrugBanner;
import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugDrugs;
import model.bo.drug.DrugItemClassification;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import model.bo.drug.DrugResearchCenter;
import service.drug.IDrugBannerService;
import service.drug.IDrugMedicineService;
import service.drug.IDrugProjectService;
import util.UploadUtil;

import com.base.action.DispatchPagerAction;
import com.constants.CupidStrutsConstants;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;

import model.bo.drug.DrugProjectConter;

@Controller("drugIndexAction")
@Scope("prototype")
public class DrugIndexAction extends DispatchPagerAction {
	@Autowired
	private IDrugBannerService bannerService;
	@Autowired
	private IDrugProjectService drugProjectService;
	@Autowired
	private IDrugMedicineService drugMedicineService;
	@Autowired
	private IUserService userService;
	
	public IDrugBannerService getBannerService() {
		return bannerService;
	}

	public void setBannerService(IDrugBannerService bannerService) {
		this.bannerService = bannerService;
	}
	
	public IDrugProjectService getDrugProjectService() {
		return drugProjectService;
	}

	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}
	
	public String toIndex(){
		//广告图片
		this.getBannerImg();
		//项目图片
		this.getDiseaseImg();
		//获取图片路径
		request.setAttribute("imgurlpath",UploadUtil.getImgUrl());
		return "index";
		
		
		
	}
	
	
	/**
	 * 首页获取广告图片
	 */
	public void getBannerImg(){
		List<DrugBanner> list = bannerService.getAll();
		System.out.println(list.size()+"ttttttttttttttttttt");
		request.setAttribute("bannerList", list);
		
	}
	
	

	/**
	 * 首页获取推荐项目图片
	 */
	public void getDiseaseImg(){
		List diseaseList=drugProjectService.showDiseaseImg();
		request.setAttribute("diseaseList", diseaseList);
		request.setAttribute("diseaseCount", diseaseList.isEmpty()?0:diseaseList.size());
		
	}
	
	/**
	 * 查看自有疾病项目详细
	 */
	
	public String getDetailed(){
		String id=request.getParameter("id");
		if(id!=null&&!id.equals("")){
			//查看项目详细信息
			DrugDiseaseItem disease=drugProjectService.getDrugDiseaseById(Long.valueOf(id));
			//查看药物信息
//			List<DrugDrugs> medicineList = drugMedicineService.getDrugMedicineById(Long.valueOf(id));
//			if (medicineList != null && medicineList.size() > 0) {
//				StringBuffer sb = new StringBuffer();
//				for (DrugDrugs m : medicineList) {
//					sb.append(StringUtils.isBlank(sb.toString()) ? "" : ",");
//					sb.append(StringUtils.isBlank(m.getMedicineName()) ? "" : m.getMedicineName());
//				}
//				request.setAttribute("medicineName", sb.toString());
//			}
			//查看项目中心信息
			List<DrugProjectConter> projectConterList=drugProjectService.getProjectConterByDiseaseid(id);
			request.setAttribute("projectList", projectConterList);
			
			request.setAttribute("disease", disease);
			request.setAttribute("readpath",UploadUtil.getImgUrl());
			
			String openid = (String) request.getSession().getAttribute(
					CupidStrutsConstants.WXOPENID);
			if (openid == null || openid.equals("")) {
				openid = "ohXRbxJRAvMg3avhgMgfatRrQRdU";
			}
			
			int isSubscrib = 0;
			
		
			User userByOpenId = userService.getUserByOpenid(openid);
			if(userByOpenId!=null){
				isSubscrib = userByOpenId.getSubstate();
			}
			isSubscrib = isSubscrib > 0 ? 1 : 0;
			request.setAttribute("isSubscrib", isSubscrib);
		}
		return "detailed";
		
		
	}
	
	/**
	 * 查询首页更多项目
	 */
	
	public String getMoreDisease(){
		Map<String,List> maplist=new HashMap();
		List<DrugDiseaseItem>diseaseItemsList=new ArrayList<>();
		List<DrugItemClassification> classificationList=drugProjectService.getAllDrugItemClassification();
		if (classificationList!=null && classificationList.size()>0) {
			for (int i = 0; i < classificationList.size(); i++) {
		     diseaseItemsList=drugProjectService.getClassificationinDisease(String.valueOf(classificationList.get(i).getId()));
				if (diseaseItemsList!=null) {
					maplist.put(classificationList.get(i).getClassificationName(), diseaseItemsList);
				}
			}
		}
		request.setAttribute("maplist",maplist);
		return "more";
	}
	
	/**
	 * 首页搜索
	 */
	
	public String indexSearch(){
		
		Map<String,List> mapDiseaseItem=new HashMap();
		//Map<String,List> mapDiseaseDict=new HashMap<>();
		String itemName = request.getParameter("itemname");
		String pageIndex = request.getParameter("pageindex");
		if(pageIndex==null)
		{
			pageIndex="1";
		}
		
		if(itemName==null){
			itemName="";
		}
		
		List diseaseItemList=drugProjectService.getDiseaseItemByName(itemName);
		
		diseaseItemList=drugProjectService.getDiseaseItemByName(itemName);
			//List<DrugDiseaseDict>diseaseDictList=drugProjectService.getDrugDiseaseDictByName(itemName,pageIndex);
			if (diseaseItemList!=null) {
				mapDiseaseItem.put("diseaseItem", diseaseItemList);	
				}
			//if (diseaseDictList!=null) {
				//mapDiseaseDict.put("diseaseDict", diseaseDictList);	
			//}
			request.setAttribute("mapDiseaseItem", mapDiseaseItem);
			//request.setAttribute("mapDiseaseDict",mapDiseaseDict);
			request.setAttribute("diseaseItemSize",diseaseItemList.size());
			//request.setAttribute("diseaseDictSize",diseaseDictList.size());
			request.setAttribute("itemName", itemName);
			//request.setAttribute("pageIndex", pageIndex);
				
		return "indexsearch";
		
	}
	
	/**
	 * 获得数据
	 * 
	 * @return
	 * @throws IOException
	 * @throws IOException
	 */
	public String getCDEData() throws IOException {
		response.setCharacterEncoding("UTF-8");
		String key = request.getParameter("key");
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		List<DrugDiseaseDict> diseaseDictList = drugProjectService.getDrugDiseaseDictByName(key,pageIndex);
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = JSONArray.fromObject(diseaseDictList);
		out.write(jsonArray.toString());
		return null;
	}
	
	/**
	 * 查看捷信项目详情
	 */
	public String getJsureDetailed(){
		List<DrugResearchCenter> drCenterList=null;
		String id=request.getParameter("id");
		if (id!=null && !id.equals("")) {
			DrugDiseaseDict diseaseDict=drugProjectService.findDiseaseDictById(Long.valueOf(id));
			if (diseaseDict!=null) {
			 drCenterList=drugProjectService.getResearchCenterByCtrId(diseaseDict.getCtrId());
			}
			request.setAttribute("drCenterList", drCenterList);
			request.setAttribute("diseaseDict", diseaseDict);
		}
		return "jsureDetailed";
		
	}

}
