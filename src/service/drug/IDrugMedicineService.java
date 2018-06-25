package service.drug;

import java.util.List;

import model.bo.drug.DrugDrugs;

/**
 * 项目对应的药物信息接口
 * @author mdm
 *
 */
public interface IDrugMedicineService {

	/**
	 * 根据项目编号获取对应的药物信息
	 * @param diseaseId
	 */
	public List<DrugDrugs> getDrugMedicineById(Long diseaseId);
}
