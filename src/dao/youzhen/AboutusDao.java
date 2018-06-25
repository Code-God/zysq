package dao.youzhen;

import model.bo.AboutUs;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
@Repository("aboutusDao")
public class AboutusDao extends EnhancedHibernateDaoSupport<AboutUs> {

	@Override
	protected String getEntityName() {
		return AboutUs.class.getName();
	}
}
