package dao.youzhen;

import model.bo.PublishedTesting;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("publishedTestingDao")
public class PublishedTestingDao extends EnhancedHibernateDaoSupport<PublishedTesting> {

	@Override
	protected String getEntityName() {
		return PublishedTesting.class.getName();
	}
}
