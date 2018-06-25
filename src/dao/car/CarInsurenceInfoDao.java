package dao.car;

import model.bo.car.CarInsurenceInfo;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("carInsInfoDao")
public class CarInsurenceInfoDao extends EnhancedHibernateDaoSupport<CarInsurenceInfo> {

	@Override
	protected String getEntityName() {
		return CarInsurenceInfo.class.getName();
	}

	 
}
