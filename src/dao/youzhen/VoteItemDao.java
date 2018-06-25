package dao.youzhen;

import model.bo.VoteItem;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("voteItemDao")
public class VoteItemDao extends EnhancedHibernateDaoSupport<VoteItem> {

	@Override
	protected String getEntityName() {
		return VoteItem.class.getName();
	}
}
