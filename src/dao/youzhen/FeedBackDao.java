package dao.youzhen;

import model.bo.Feedback;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;

/**
 *
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
@Repository("feedBackDao")
public class FeedBackDao extends EnhancedHibernateDaoSupport<Feedback> {

	@Override
	protected String getEntityName() {
		return Feedback.class.getName();
	}
}
