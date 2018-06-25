package dao.food;

import model.bo.food.ChargeFeeRecord;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

@Repository("chargeFeeRecordDao")
public class ChargeFeeRecordDao extends EnhancedHibernateDaoSupport<ChargeFeeRecord> {

	@Override
	protected String getEntityName() {
		return ChargeFeeRecord.class.getName();
	}
}
