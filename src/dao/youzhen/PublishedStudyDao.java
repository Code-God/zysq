package dao.youzhen;

import model.bo.PublishedStudy;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("publishedStudyDao")
public class PublishedStudyDao extends EnhancedHibernateDaoSupport<PublishedStudy> {

	@Override
	protected String getEntityName() {
		return PublishedStudy.class.getName();
	}
}
