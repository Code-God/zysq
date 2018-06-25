package actions.drug;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugDrugs;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugProjectService;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;
/**
 * 药物种类Action
 * @author Administrator
 *
 */
@Controller("DrugDrugsAction")
@Scope("prototype")
public class DrugDrugsAction extends DispatchPagerAction {	
	private static final long serialVersionUID = -5046449231752437143L;
	@Autowired
	private IDrugProjectService drugProjectService;
	private DrugDrugs drug;
	/**
	 * 疾病管理列表
	 * @return
	 */
	public String drugsManager(){
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "drugsManager";
	}
	
	/**
	 * 药物类型列表
	 * @return
	 */
	public String getList(){
		try{
		Page<DrugDrugs> page = new Page<DrugDrugs>();
		Map<String, Object> paramap = new HashMap<String, Object>();
		this.setPageParams(page);
		page.setPaginationSize(7);
		String drug_name = request.getParameter("medicineName");
		if (StringUtils.isNotEmpty(drug_name)) {
			paramap.put("medicineName", drug_name);
			request.setAttribute("medicineName", drug_name);
		}

		page = drugProjectService.findPageForDrugs(page, paramap);
		List<Integer> li = page.getPageNos();
		String listUrl = request.getContextPath() + "/admin/drugs_getList.Q";
		request.setAttribute("listUrl", listUrl);
		request.setAttribute("page", page);
		request.setAttribute("li", li);
		request.setAttribute("drugsList", page.getData());
		request.setAttribute("pop", request.getParameter("pop"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return "drugsList";
	}
	/**
	 * 打开新增或者修改页面
	 * @return
	 */
	public String inputDrugs(){
		String id=request.getParameter("id");
		if(id!=null&&!id.equals("")){
			DrugDrugs drug=drugProjectService.getDrugById(Long.valueOf(id));
			request.setAttribute("drug", drug);
		}
		//查询疾病列表信息
		List<DrugDiseaseItem> diseaseList=drugProjectService.getAllDisease();
		request.setAttribute("diseaseList", diseaseList);
		return "drugsInput";
	}
	
	
	/**
	 * 保存疾病信息
	 * @return
	 */
	public String saveDrug(){
		
		drugProjectService.saveOrUpdateDrug(drug);
		
		return "ok";
	}
	
	public String delDrug() {
		try {
			String itemIds = request.getParameter("itemIds");
			String[] ids = itemIds.split(",");
			for(int i=0;i<ids.length;i++){
				drugProjectService.delDrugById(Long.valueOf(ids[i]));
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
	
	
	public DrugDrugs getDrug() {
		return drug;
	}

	public void setDrug(DrugDrugs drug) {
		this.drug = drug;
	}

	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}
	
	
}
