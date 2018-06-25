package dao.car;

import model.bo.car.CarLib;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("carLibDao")
public class CarLibDao extends EnhancedHibernateDaoSupport<CarLib> {

	@Override
	protected String getEntityName() {
		return CarLib.class.getName();
	}
}
