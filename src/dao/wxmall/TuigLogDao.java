package dao.wxmall;

import model.bo.food.TuigLog;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("tuigLogDao")
public class TuigLogDao extends EnhancedHibernateDaoSupport<TuigLog> {

	@Override
	protected String getEntityName() {
		return TuigLog.class.getName();
	}
}
