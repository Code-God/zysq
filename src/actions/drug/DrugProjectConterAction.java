package actions.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugProjectConter;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugProjectService;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;

/**
 * 项目中心action
 * @author Administrator
 *
 */
@Controller("DrugProjectConterAction")
@Scope("prototype")
public class DrugProjectConterAction extends DispatchPagerAction  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private IDrugProjectService drugProjectService;
	private DrugProjectConter drugProjectConter;
	
	/**
	 * 项目中心管理列表
	 * @return
	 */
	public  String manager(){
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "projectConterManager";
		
	}
	/**
	 * 数据列表
	 * @return
	 */
	public String getList(){
		try{
			Page<DrugProjectConter> page = new Page<DrugProjectConter>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String organizationName = request.getParameter("organizationName");
			if (StringUtils.isNotEmpty(organizationName)) {
				paramap.put("organizationName", organizationName);
				request.setAttribute("organizationName", organizationName);
			}

			page = drugProjectService.findPageForProjectConter(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath() + "/admin/durgProjectConter_getList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("projectConterList", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
			}catch(Exception e){
				e.printStackTrace();
			}
			return "projectConterList";	
	}
	
	
	/**
	 * 打开新增或者修改页面
	 * @return
	 */
	public String inputProjectConter(){
		String id=request.getParameter("id");
		if(id!=null&&!id.equals("")){
			drugProjectConter=drugProjectService.getProjectConterById(Long.valueOf(id));
			request.setAttribute("drugProjectConter", drugProjectConter);
		}
		//查询所有疾病列表
		List<DrugDiseaseItem> list=drugProjectService.getAllDisease();
		request.setAttribute("diseaselist", list);
		return "projectConterInput";
	}
	
	/**
	 * 保存项目信息
	 * @return
	 */
	public String saveProjectConter(){
		drugProjectService.saveOrUpdateDrugProjectConter(drugProjectConter);
		
		return "ok";
	}
	/**
	 * 删除项目
	 * @return
	 */
	
	public String delProjectConterItem() {
		try {
			String itemIds = request.getParameter("itemIds");
			String[] ids = itemIds.split(",");
			for(int i=0;i<ids.length;i++){
				drugProjectService.deleteDrugProjectConterItem(Long.valueOf(ids[i]));
			}
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	
	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}

	public DrugProjectConter getDrugProjectConter() {
		return drugProjectConter;
	}
	public void setDrugProjectConter(DrugProjectConter drugProjectConter) {
		this.drugProjectConter = drugProjectConter;
	}
	
}
