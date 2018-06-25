package model.bo;

import java.io.Serializable;

/**
 * 
 * 调研记录
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="voterecord" lazy="false"
 */
public class VoteRecord implements Serializable {

	private static final long serialVersionUID = -5405612500072350388L;

	private Long id;

	/** ◆人员类别：A、合同用工 B、派遣制员工 */
	private String empType;

	/** ◆岗位类别：A、领导、管理岗位 B、技术岗位 C、营销岗位 D、生产岗位 */
	private String jobType;

	/** 此问题对应的调查问卷 */
	private Vote vote;

	/** 调查的问题ID */
	private Long voteItemId;

	/** 用户的答案 */
	private String answer;

	/** 用户名 */
	private String username;

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	/**
	 * @hibernate.many-to-one class="model.bo.Vote" column="vid"
	 * @return
	 */
	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getVoteItemId() {
		return voteItemId;
	}

	public void setVoteItemId(Long voteItemId) {
		this.voteItemId = voteItemId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
