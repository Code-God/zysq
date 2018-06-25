package dao.fenxiao;

import model.bo.fenxiao.PrdSpec;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("prdSpecDao")
public class PrdSpecDao extends EnhancedHibernateDaoSupport<PrdSpec> {

	@Override
	protected String getEntityName() {
		return PrdSpec.class.getName();
	}
}
