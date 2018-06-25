package model.bo;

import java.io.Serializable;

/**
 * 
 * 发布的考试辅导题 每次管理员发布时，将清空此表， 并将最新发布的题目插入到此表。
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="publishedStudy" lazy="false"
 */
public class PublishedStudy implements Serializable {

	private static final long serialVersionUID = -4069375158318306633L;

	private Long id;

	/** 发布的问题ID, 多对一 */
	private Question question;

	/** 序号 预留*/
	private int seqno = 0;

	private String publishDate;

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
	 * @hibernate.many-to-one  class="model.bo.Question" column="qid"
	 * @return
	 */
	public Question getQuestion() {
		return question;
	}

	
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * @hibernate.property type="int"
	 */
	public int getSeqno() {
		return seqno;
	}

	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}

	/**
	 * @hibernate.property type="string"
	 */
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
}
