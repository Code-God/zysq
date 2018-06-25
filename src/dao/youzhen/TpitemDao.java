package dao.youzhen;

import model.bo.TouPiaoItem;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("tpitemDao")
public class TpitemDao extends EnhancedHibernateDaoSupport<TouPiaoItem> {

	@Override
	protected String getEntityName() {
		return TouPiaoItem.class.getName();
	}
}
