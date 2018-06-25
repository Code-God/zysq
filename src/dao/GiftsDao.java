package dao;

import model.bo.Gift;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("giftsDao")
public class GiftsDao extends EnhancedHibernateDaoSupport<Gift> {

	@Override
	protected String getEntityName() {
		return Gift.class.getName();
	}
}
