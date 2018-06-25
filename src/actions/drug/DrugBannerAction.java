package actions.drug;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import model.bo.drug.DrugBanner;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.drug.IDrugBannerService;
import util.UploadUtil;

import com.base.action.DispatchPagerAction;

@Controller("DrugBannerAction")
@Scope("prototype")
public class DrugBannerAction extends DispatchPagerAction {
	private static final long serialVersionUID = 1L;
	@Resource(name = "drugBannerService")
	private IDrugBannerService drugBannerService;
	private File file;
	private DrugBanner banner;
	
	public DrugBanner getBanner() {
		return banner;
	}

	public void setBanner(DrugBanner banner) {
		this.banner = banner;
	}

	public void setDrugBannerService(IDrugBannerService drugBannerService) {
		this.drugBannerService = drugBannerService;
	}

	/**
	 * 获取列表
	 */
	public String getList() {
		List<DrugBanner> list = drugBannerService.getAll();
		request.setAttribute("list", list);
		return "list";

	}

	/**
	 * 跳转新增页面
	 */

	public String addBanner() {
		return "add";

	}

	/**
	 * 保存新增数据
	 */
	public String saveBanner() {
		drugBannerService.saveBanner(banner, file);
		return "save";

	}

	/**
	 * 删除数据
	 * 
	 * @return
	 */

	public String delBanner() {
		String id = request.getParameter("id");
		try {
			drugBannerService.delete(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "del";
	}

	/**
	 * 通过id查找
	 */

	public String getBannerById() {

		String id = request.getParameter("id");
		try {
			 banner = drugBannerService.getBannerById(Long.parseLong(id));
			request.setAttribute("banner", banner);
			request.setAttribute("readpath",UploadUtil.getImgUrl());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "getone";

	}

	/**
	 * 修改
	 * 
	 * @return
	 */

	public String modBanner() {
		String id = request.getParameter("id");
		try {
			drugBannerService.modify(banner, Long.valueOf(id), file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "update";

	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
