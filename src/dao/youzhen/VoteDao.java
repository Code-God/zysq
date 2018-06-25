package dao.youzhen;

import model.bo.Vote;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("voteDao")
public class VoteDao extends EnhancedHibernateDaoSupport<Vote> {

	@Override
	protected String getEntityName() {
		return Vote.class.getName();
	}
}
