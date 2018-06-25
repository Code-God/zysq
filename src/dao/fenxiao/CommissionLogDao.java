package dao.fenxiao;

import model.bo.fenxiao.CommissionLog;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("commissionLogDao")
public class CommissionLogDao extends EnhancedHibernateDaoSupport<CommissionLog> {

	@Override
	protected String getEntityName() {
		return CommissionLog.class.getName();
	}
}
