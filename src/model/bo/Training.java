package model.bo;

import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * 培训
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="training" lazy="false"
 */
public class Training implements Serializable, Comparable<Training> {

	private static final long serialVersionUID = 4756681264416821565L;

	private Long id;

	/** 调研标题 */
	private String ttitle;

	/** 调研正文描述 */
	private String tcontent;
	private String tcontentStr;

	/** 发布时间* */
	private String pdate;

	public Training() {
	}

	public Training(String title, String content) {
		this.ttitle = title;
		this.tcontent = content;
	}

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
	public String getTtitle() {
		return ttitle;
	}

	/**
	 * @param ttitle the ttitle to set
	 */
	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getTcontent() {
		return tcontent;
	}

	/**
	 * @param tcontent the tcontent to set
	 */
	public void setTcontent(String tcontent) {
		this.tcontent = tcontent;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPdate() {
		return pdate;
	}

	/**
	 * @param pdate the pdate to set
	 */
	public void setPdate(String pdate) {
		this.pdate = pdate;
	}

	
	/**
	 * @return the tcontentStr
	 */
	public String getTcontentStr() {
		Document doc = Jsoup.parse(this.tcontent);
		if(doc.text().length() > 20){
			return doc.text().substring(0, 20) + "...";
		}
		return doc.text();
	}

	@Override
	public int compareTo(Training o) {
		if(this.getId().intValue() == o.getId().intValue()){
			return 0;
		}else if(this.getId().intValue() > o.getId().intValue()){
			return -1;
		}else{
			return 1;
		}
	}
}
