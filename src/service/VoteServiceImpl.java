package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.bo.TP;
import model.bo.Vote;
import model.bo.VoteItem;
import service.intf.IVoteService;

import com.base.ServerBeanFactory;

import dao.youzhen.TPDao;
import dao.youzhen.VoteDao;
import dao.youzhen.VoteItemDao;
import dao.youzhen.VoteRecordDao;

@Service("voteService")
public class VoteServiceImpl implements IVoteService {

	@Autowired
	private VoteDao voteDao;

	@Autowired
	private VoteItemDao voteItemDao;

	@Autowired
	private VoteRecordDao voteRecordDao;

	public VoteDao getVoteDao() {
		return voteDao;
	}

	public void setVoteDao(VoteDao voteDao) {
		this.voteDao = voteDao;
	}

	public VoteItemDao getVoteItemDao() {
		return voteItemDao;
	}

	public void setVoteItemDao(VoteItemDao voteItemDao) {
		this.voteItemDao = voteItemDao;
	}

	public VoteRecordDao getVoteRecordDao() {
		return voteRecordDao;
	}

	public void setVoteRecordDao(VoteRecordDao voteRecordDao) {
		this.voteRecordDao = voteRecordDao;
	}

	@Override
	public List<Vote> getVoteList() {
		return voteDao.getAllEntities();
	}

	@Override
	public boolean delVoteById(String id) {
		try {
			this.voteDao.deleteEntity(Long.valueOf(id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean delTPById(String id) {
		TPDao tpDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		try {
			tpDao.deleteEntity(Long.valueOf(id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean importVote(List<VoteItem> vilist) {
		try {
			for (VoteItem voteItem : vilist) {
				this.voteItemDao.saveEntity(voteItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Vote saveVote(Vote v) {
		try {
			this.voteDao.saveEntity(v);
		} catch (Exception e) {
			return null;
		}
		return v;
	}

	@Override
	public List<TP> getTPList() {
		TPDao tpDao = (TPDao) ServerBeanFactory.getBean("tpDao");
		return tpDao.getAllEntities();
	}
}
