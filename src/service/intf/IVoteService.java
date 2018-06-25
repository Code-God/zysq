package service.intf;

import java.util.List;

import model.bo.TP;
import model.bo.Vote;
import model.bo.VoteItem;

/**
 * 在线调研相关的service
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public interface IVoteService {

	
	List<Vote> getVoteList();

	boolean delVoteById(String id);

	boolean importVote(List<VoteItem> vilist);

	Vote saveVote(Vote v);

	List<TP> getTPList();

	boolean delTPById(String id);

	
}
