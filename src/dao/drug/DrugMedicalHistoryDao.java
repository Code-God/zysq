package dao.drug;

import model.bo.drug.DrugMedicalHistory;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 * 病例附件存放路径
 * @author
 *
 */
@Repository("drugMedicalHistoryDao")
public class DrugMedicalHistoryDao extends EnhancedHibernateDaoSupport<DrugMedicalHistory>{

	@Override
	protected String getEntityName() {
		return DrugMedicalHistory.class.getName();
	}
	
}
