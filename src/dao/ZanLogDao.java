package dao;

import model.bo.ZanLog;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("zanLogDao")
public class ZanLogDao extends EnhancedHibernateDaoSupport<ZanLog> {

	@Override
	protected String getEntityName() {
		return ZanLog.class.getName();
	}
}
