package dao.youzhen;

import model.bo.VoteRecord;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("voteRecordDao")
public class VoteRecordDao extends EnhancedHibernateDaoSupport<VoteRecord> {

	@Override
	protected String getEntityName() {
		return VoteRecord.class.getName();
	}
}
