package model.bo;

import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 文章表，
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="showCase" lazy="false"
 */
public class ShowCase implements Serializable {

	private static final long serialVersionUID = -4733676120157615669L;

	private Long id;

	/**  标题 */
	private String title;

	/** 文章内容，支持图片 */
	private String thecontent;
	
	private String thecontentStr;

	/** 作者姓名 */
	private String authorName;

	/** 上传时间 */
	private String createDate;
	
	/** 外键：CID */
	private Columns columns;

	/** 0-邮政资讯 1-业务学习 2-生活常识 */
	private int docType;

	/** 缩略图url，目前只有针对行业纵览里的文章 */
	private String picUrl;

	/** 赞 */
	private int zan;

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
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getDocType() {
		return docType;
	}

	public void setDocType(int docType) {
		this.docType = docType;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getZan() {
		return zan;
	}

	public void setZan(int zan) {
		this.zan = zan;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getThecontent() {
		return thecontent;
	}

	
	public void setThecontent(String thecontent) {
		this.thecontent = thecontent;
	}

	
	public String getThecontentStr() {
		if (this.thecontent != null) {
			Document parse = Jsoup.parse(thecontent);
			if(parse.text().length() > 31){
				return parse.text().substring(0, 30) + "...";
			}else{
				return parse.text();
			}
		}
		return "";
	}

	
	public void setThecontentStr(String thecontentStr) {
		this.thecontentStr = thecontentStr;
	}
 

	/**
	 * 此文章对应的栏目
	 * @hibernate.many-to-one class="model.bo.Columns" column="cid" cascade="save-update"
	 * @return
	 */
	public Columns getColumns() {
		return columns;
	}

	
	public void setColumns(Columns columns) {
		this.columns = columns;
	}
}
