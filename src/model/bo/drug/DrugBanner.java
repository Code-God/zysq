package model.bo.drug;

/**
 * 
 * @author Administrator 图片设置
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="drug_banner"
 *                  lazy="false"
 */
public class DrugBanner implements java.io.Serializable {
	/** 主键 */
	private Long id;
	/** 图片名称 */
	private String imgname;
	/** 图片链接地址 */
	private String imglink;
	/** 图片保存路径 */
	private String imgpath;
	/**
	 * @return
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getImglink() {
		return imglink;
	}

	public void setImglink(String imglink) {
		this.imglink = imglink;
	}
	/**
	 * @return
	 * @hibernate.property type="string"
	 */
	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

}
