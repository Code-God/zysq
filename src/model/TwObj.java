package model;

import java.io.Serializable;

/**
 * 图文对象
 * 
 * @author jacky
 */
public class TwObj implements Serializable {

	private static final long serialVersionUID = 3348868645612937111L;

	private String title;

	private String twdesc;

	private String picUrl;

	private String twUrl;

	public TwObj(String title, String desc, String picUrl, String twUrl) {
		this.title = title;
		this.twdesc = desc;
		this.picUrl = picUrl;
		this.twUrl = twUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTwdesc() {
		return twdesc;
	}

	public void setTwdesc(String twdesc) {
		this.twdesc = twdesc;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getTwUrl() {
		return twUrl;
	}

	public void setTwUrl(String twUrl) {
		this.twUrl = twUrl;
	}
}
