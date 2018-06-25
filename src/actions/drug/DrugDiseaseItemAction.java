package actions.drug;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugItemClassification;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugProjectService;
import util.UploadUtil;
import actions.integ.weixin.WeiXinUtil;

import com.base.action.DispatchPagerAction;
import com.base.tools.Version;
import com.base.util.Page;
import com.wfsc.util.HttpUtils;

/**
 * 疾病action
 * 
 * @author Administrator
 * 
 */
@Controller("DrugDiseaseItemAction")
@Scope("prototype")
public class DrugDiseaseItemAction extends DispatchPagerAction {
	private static final long serialVersionUID = -5046449231752437143L;
	@Autowired
	private IDrugProjectService drugProjectService;
	private DrugDiseaseItem disease;
	private File file1;

	/**
	 * 疾病管理列表
	 * 
	 * @return
	 */
	public String diseaseManager() {
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "diseaseManager";
	}

	/**
	 * 疾病列表
	 * 
	 * @return
	 */
	public String getList() {
		try {

			Page<DrugDiseaseItem> page = new Page<DrugDiseaseItem>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String disease_name = request.getParameter("diseaseName");
			if (StringUtils.isNotEmpty(disease_name)) {
				paramap.put("diseaseName", disease_name);
				request.setAttribute("diseaseName", disease_name);
			}

			page = drugProjectService.findPageForDisease(page, paramap);
			List<Integer> li = page.getPageNos();

			String listUrl = request.getContextPath()
					+ "/admin/drugDisease_getList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("diseaseList", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugDiseaseList";
	}

	/**
	 * 中心統計管理列表
	 * 
	 * @return
	 */
	public String diseaseCenterCountManager() {
		getCenterCountList();

		return "diseaseCenterCountManager";
	}

	/**
	 * 获取中心统计列表
	 * 
	 * @return
	 */
	public String getCenterCountList() {
		try {

			Page page = new Page();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);

			String disease_name = request.getParameter("diseaseName");
			if (StringUtils.isNotEmpty(disease_name)) {
				paramap.put("diseaseName", disease_name);
				request.setAttribute("diseaseName", disease_name);
			}

			page = drugProjectService.findPageForCenter(page, paramap);
			List<Integer> li = page.getPageNos();

			String listUrl = request.getContextPath()
					+ "/admin/drugDisease_getCenterCountList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			List<Object> data = page.getData();
			if(StringUtils.isNotEmpty(disease_name)){
				if (data != null && data.size() > 0) {
	                 for(Object obj : data){
	                	 Object[] arr = (Object[])obj;
	                	 if(arr[2]!=null&&arr[2].toString().length()>0){
	                		 ArrayList<String> strList = new ArrayList<>();
	                		 ArrayList<String> tempList = new ArrayList<>();
	                		 String spons = arr[2].toString();
	                		 spons = spons.replace("/", ",");
	                		 for(String item : spons.split(",")){
	                			 String prefix = "";
	                			 if(item.length()>=8){
	                				 prefix = item.substring(0,7).trim();
	                				 if(!tempList.contains(prefix)){
	                					 tempList.add(prefix);
		                				 strList.add(item.trim());
		                			 }
	                			 }
	                			 else{
	                				 if(item.trim().length()<=3){
	                					 continue;
	                				 }
	                				 strList.add(item);
	                			 }
	                		 }
	                		 spons = "";
	                		 Iterator<String> iter = strList.iterator();
	                		 while(iter.hasNext()){
	                			 spons+=iter.next()+"<br/>";
	                		 }
	                		 arr[2] = spons;
	                	 }
	                 }
				}
			}
			request.setAttribute("diseaseCenterCountList", data);
			request.setAttribute("pop", request.getParameter("pop"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugDiseaseCenterCountList";
	}

	/**
	 * 打开新增或者修改页面
	 * 
	 * @return
	 */
	public String inputDiseaseItem() {
		String id = request.getParameter("id");
		if (id != null && !id.equals("")) {
			DrugDiseaseItem disease = drugProjectService
					.getDrugDiseaseById(Long.valueOf(id));
			request.setAttribute("disease", disease);
			request.setAttribute("readpath", UploadUtil.getImgUrl());
		}
		// 查询所有分类列表
		List<DrugItemClassification> list = drugProjectService
				.getAllDrugItemClassification();
		request.setAttribute("classlist", list);
		return "drugDiseaseInput";
	}

	/**
	 * 保存疾病信息
	 * 
	 * @return
	 */
	public String saveDiseaseItem() {
		System.out.println(file1 + "pppppppppppppppppppp");
		drugProjectService.saveOrUpdateDrugDiseaseItem(disease, file1);

		return "ok";
	}

	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public String delDiseaseItem() {
		try {
			String itemIds = request.getParameter("itemIds");
			String[] ids = itemIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				drugProjectService.deleteDrugDiseaseItem(Long.valueOf(ids[i]));
			}

			// Admin user = getCurrentAdminUser();
			// SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION,
			// user.getUsername(), "删除管理员");
			// systemLogService.saveSystemLog(systemLog);

			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DrugDiseaseItem getDisease() {
		return disease;
	}

	public void setDisease(DrugDiseaseItem disease) {
		this.disease = disease;
	}

	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}

}
