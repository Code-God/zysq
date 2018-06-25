package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.drug.DrugDrugsDao;
import model.bo.drug.DrugDrugs;
import service.drug.IDrugMedicineService;

/**
 * 项目对应的药物信息接口
 * @author mdm
 *
 */
@Service("drugMedicineService")
public class DrugMedicineServiceImpl implements IDrugMedicineService {

	/**
	 * 药物dao
	 */
	@Autowired
	private DrugDrugsDao drugDrugDao;
	
	@Override
	public List<DrugDrugs> getDrugMedicineById(Long diseaseId) {
		return drugDrugDao.getDrugMedicineById(diseaseId);
	}

}
