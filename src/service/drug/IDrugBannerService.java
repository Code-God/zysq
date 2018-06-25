package service.drug;

import java.io.File;
import java.util.List;

import model.bo.drug.DrugBanner;

public interface IDrugBannerService {
	/**
	 * 提交保存
	 * 
	 * @param banner
	 */
	public void saveBanner(DrugBanner drugBanner, File file);

	/**
	 * 查询列表
	 */
	public List<DrugBanner> getAll();

	/**
	 * 删除
	 */
	public void delete(Long id);

	/**
	 * 修改
	 */
	public void modify(DrugBanner drugBanner, Long id, File file);
	/**
	 * 查询详细
	 */
	public DrugBanner getBannerById(Long id);
}
