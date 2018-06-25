package dao.fenxiao;

import model.bo.fenxiao.CashApply;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("cashApplyDao")
public class CashApplyDao extends EnhancedHibernateDaoSupport<CashApply> {

	@Override
	protected String getEntityName() {
		return CashApply.class.getName();
	}
}
