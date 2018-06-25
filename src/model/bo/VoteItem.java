package model.bo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 调查问卷里面包含的题目
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="voteItem" lazy="false"
 */
public class VoteItem implements Serializable {

	private static final long serialVersionUID = 6915097565567421364L;

	private Long id;

	/** 问题所属问卷 */
	private Vote vote;

	/** 问题标题 */
	private String ititle;

	/** 问题内容， 如："A、非常满意 B、比较满意 C、基本满意 D、不满意 E、非常不满意" */
	private String icontent;
	
	/** 在页面上显示用，自动加上单选框 */
	private String icontentHtml;

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
	 * @hibernate.many-to-one  class="model.bo.Vote" column="vid"
	 * @return
	 */
	public Vote getVote() {
		return vote;
	}

	
	public void setVote(Vote vote) {
		this.vote = vote;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getItitle() {
		return ititle;
	}

	
	public void setItitle(String ititle) {
		this.ititle = ititle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getIcontent() {
		return icontent;
	}

	
	public void setIcontent(String icontent) {
		this.icontent = icontent;
	}

	
	/**
	 * 为了在页面上显示单选框
	 * @return the icontentHtml
	 */
	public String getIcontentHtml() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "A");
		map.put(2, "B");
		map.put(3, "C");
		map.put(4, "D");
		map.put(5, "E");
		map.put(6, "F");
		StringBuffer html = new StringBuffer();
		String[] split = this.icontent.split("\\s+");
		int i = 0;
		for (String opt : split) {
			i++;
			html.append("<input type='radio' value='"+ this.getId() +"|"+ map.get(i) +"' name='answer_"+ this.getId() +"' onclick='setValue("+ this.getId() +",this.value)'>").append(opt);
		}
		return html.toString();
	}

	
	/**
	 * @param icontentHtml the icontentHtml to set
	 */
	public void setIcontentHtml(String icontentHtml) {
		this.icontentHtml = icontentHtml;
	}
	
	public static void main(String[] args){
		String s = "A、非常满意  B、比较满意 C、基本满意 D、不满意 E、非常不满意";
		String[] split = s.split("\\s+");
		System.out.println(split.length);
	}
	
}
