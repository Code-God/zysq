package dao;

import model.bo.Topics;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("topicDao")
public class TopicDao extends EnhancedHibernateDaoSupport<Topics> {

	@Override
	protected String getEntityName() {
		return Topics.class.getName();
	}
}
