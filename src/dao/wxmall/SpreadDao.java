package dao.wxmall;

import model.bo.food.Spreader;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("spreadDao")
public class SpreadDao extends EnhancedHibernateDaoSupport<Spreader> {

	@Override
	protected String getEntityName() {
		return Spreader.class.getName();
	}
}
