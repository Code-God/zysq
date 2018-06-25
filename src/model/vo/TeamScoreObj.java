package model.vo;

import java.io.Serializable;
import java.util.List;

import model.bo.auth.Org;

/**
 * 积分查询结果对象 
 *北区北京组120000分名列北区小组第一名
张三20000分第一名
李四18000分第二名
王五16000分第三名
赵六14000分第四名
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class TeamScoreObj implements Serializable{
	
	private static final long serialVersionUID = -7538083212050511540L;

	/** 本组分数 */
	private int totalScore;
	
	/** 本组排名 */
	private int scoreNo;
	
	/** 各组排名 */
	private List<Org> list;

	
	public int getTotalScore() {
		return totalScore;
	}

	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	
	public int getScoreNo() {
		return scoreNo;
	}

	
	public void setScoreNo(int scoreNo) {
		this.scoreNo = scoreNo;
	}

	
	public List<Org> getList() {
		return list;
	}

	
	public void setList(List<Org> list) {
		this.list = list;
	}
	
	
}
