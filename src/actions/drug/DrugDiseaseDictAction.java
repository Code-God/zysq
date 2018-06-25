package actions.drug;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.bo.drug.DrugDiseaseDict;
import model.bo.drug.DrugDiseaseItem;
import model.bo.drug.DrugItemClassification;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugProjectService;
import util.UploadUtil;

import com.base.action.DispatchPagerAction;
import com.base.util.Page;

/**
 * 捷信项目action
 * 
 * @author Administrator
 * 
 */

@Controller("DrugDiseaseDictAction")
@Scope("prototype")
public class DrugDiseaseDictAction extends DispatchPagerAction {
	@Autowired
	private IDrugProjectService drugProjectService;
	private DrugDiseaseDict drugDiseaseDict;

	/**
	 * 疾病管理列表
	 * 
	 * @return
	 */
	public String diseaseDictManager() {
		getList();
		if (request.getParameter("pop") != null) {
			request.setAttribute("pop", "1");
		}
		return "diseaseDictManager";
	}

	/**
	 * 项目列表
	 * 
	 * @return
	 */
	public String getList() {
		try {
			Page<DrugDiseaseDict> page = new Page<DrugDiseaseDict>();
			Map<String, Object> paramap = new HashMap<String, Object>();
			this.setPageParams(page);
			page.setPaginationSize(7);
			String dicDiseaseName = request.getParameter("dicDiseaseName");
			if (StringUtils.isNotEmpty(dicDiseaseName)) {
				paramap.put("dicDiseaseName", dicDiseaseName);
				request.setAttribute("dicDiseaseName", dicDiseaseName);
			}

			page = drugProjectService.findPageForDiseaseDict(page, paramap);
			List<Integer> li = page.getPageNos();
			String listUrl = request.getContextPath()
					+ "/admin/drugDiseaseDict_getList.Q";
			request.setAttribute("listUrl", listUrl);
			request.setAttribute("page", page);
			request.setAttribute("li", li);
			request.setAttribute("diseaseDictList", page.getData());
			request.setAttribute("pop", request.getParameter("pop"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugDiseaseDictList";
	}

	/**
	 * 打开新增或者修改页面
	 * 
	 * @return
	 */
	public String inputDiseaseDict() {
		String id = request.getParameter("id");
		if (id != null && !id.equals("")) {
			DrugDiseaseDict drugDiseaseDict = drugProjectService
					.findDiseaseDictById(Long.valueOf(id));
			request.setAttribute("drugDiseaseDict", drugDiseaseDict);
		}
		return "drugDiseaseDict";
	}

	/**
	 * 保存疾病信息
	 * 
	 * @return
	 */
	public String saveDiseaseDict() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		drugDiseaseDict.setPublishDate(sdf.format(new Date()));
		drugProjectService.saveOrUpdateDiseaseDict(drugDiseaseDict);

		return "ok";
	}
	/**
	 * 删除
	 * @return
	 */

	public String delDiseaseDict() {
		try {
			String itemIds = request.getParameter("itemIds");
			String[] ids = itemIds.split(",");
			for (int i = 0; i < ids.length; i++) {
				drugProjectService.deleteDiseaseDict(Long.valueOf(ids[i]));
			}
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public DrugDiseaseDict getDrugDiseaseDict() {
		return drugDiseaseDict;
	}

	public void setDrugDiseaseDict(DrugDiseaseDict drugDiseaseDict) {
		this.drugDiseaseDict = drugDiseaseDict;
	}

	public void setDrugProjectService(IDrugProjectService drugProjectService) {
		this.drugProjectService = drugProjectService;
	}

}
