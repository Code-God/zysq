package dao.youzhen;

import model.bo.TP;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("tpDao")
public class TPDao extends EnhancedHibernateDaoSupport<TP> {

	@Override
	protected String getEntityName() {
		return TP.class.getName();
	}
}
