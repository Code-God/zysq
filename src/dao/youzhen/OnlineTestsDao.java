package dao.youzhen;

import model.bo.OnlineTests;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 *  DAO 
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
@Repository("onlineTestsDao")
public class OnlineTestsDao extends EnhancedHibernateDaoSupport<OnlineTests> {

	@Override
	protected String getEntityName() {
		return OnlineTests.class.getName();
	}
}
