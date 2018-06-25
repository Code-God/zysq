package actions.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import model.bo.drug.DrugItemClassification;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugProjectService;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
/**
 * 项目类型
 * @author Administrator
 *
 */
@Controller("DrugItemClassificationAction")
@Scope("prototype")
public class DrugItemClassificationAction extends DispatchPagerAction {	
	private static final long serialVersionUID = -5046449231752437143L;
	@Autowired
	private IDrugProjectService drugProjectService;
	private DrugItemClassification classification;
	/**
	 * 项目管理列表
	 * @return
	 */
	public String itemClassManager(){
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "itemClassManager";
	}
	
	/**
	 * 项目类型列表
	 * @return
	 */
	public String getList(){
		try{
		Page<DrugItemClassification> page = new Page<DrugItemClassification>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String classification_name = request.getParameter("classificationName");
		if (StringUtils.isNotEmpty(classification_name)) {
			paramap.put("classificationName", classification_name);
			request.setAttribute("classificationName", classification_name);
		}

		page = drugProjectService.findPageForDrugItemClassification(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/itemClass_getList.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("itemClasslist", page.getData());
		request.setAttribute("pop", request.getParameter("pop"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "itemClassList";
	}
	/**
	 * 打开新增或者修改页面
	 * @return
	 */
	public String inputItemClass(){
		String id=request.getParameter("id");
		if(id!=null&&!id.equals("")){
			DrugItemClassification classification=drugProjectService.getDrugItemClassificationById(Long.valueOf(id));
			request.setAttribute("classification", classification);
		}
		return "itemClassInput";
	}
	
	
	/**
	 * 保存项目类型信息
	 * @return
	 */
	public String saveItemClass(){
		
		drugProjectService.saveOrUpdateDrugItemClassification(classification);
		
		return "ok";
	}
	
	public String delDiseaseItem() {
		try {
			String itemIds = request.getParameter("itemIds");
			String[] ids = itemIds.split(",");
			for(int i=0;i<ids.length;i++){
				drugProjectService.deleteDrugItemClassification(Long.valueOf(ids[i]));
			}
		
//		Admin user = getCurrentAdminUser();
//		SystemLog systemLog = new SystemLog(SystemLog.MODULE_PERMISSION, user.getUsername(), "删除管理员");
//		systemLogService.saveSystemLog(systemLog);

			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}
	

	public DrugItemClassification getClassification() {
		return classification;
	}

	public void setClassification(DrugItemClassification classification) {
		this.classification = classification;
	}

	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}
	
	
}
