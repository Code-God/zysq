package dao.fenxiao;

import model.bo.fenxiao.FxApplyConfig;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("fxApplyConfigDao")
public class FxApplyConfigDao extends EnhancedHibernateDaoSupport<FxApplyConfig> {

	@Override
	protected String getEntityName() {
		return FxApplyConfig.class.getName();
	}
}
