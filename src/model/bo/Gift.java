package model.bo;

import java.io.Serializable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * 礼品表
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="gifts" lazy="false"
 */
public class Gift implements Serializable {

	private static final long serialVersionUID = -3401518753173536435L;

	private Long id;

	/** 名称 */
	private String giftName;
	
	/** 礼品描述 */
	private String giftDesc;
	private String giftDescStr;

	/** 图片路径 */
	private String giftPic;

	/** 礼品兑换所需积分 */
	private int score;
	
	/** 库存量 */
	private int stock = 999;
	
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
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getGiftPic() {
		return giftPic;
	}

	public void setGiftPic(String giftPic) {
		this.giftPic = giftPic;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getStock() {
		return stock;
	}

	
	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getGiftDesc() {
		return giftDesc;
	}

	
	public void setGiftDesc(String giftDesc) {
		this.giftDesc = giftDesc;
	}

	
	public String getGiftDescStr() {
		if (this.giftDesc != null) {
			Document parse = Jsoup.parse(giftDesc);
			if(parse.text().length() > 20){
				return parse.text().substring(0, 20) + "...";
			}else{
				return parse.text();
			}
		}
		return "";
	}

	
	public void setGiftDescStr(String giftDescStr) {
		this.giftDescStr = giftDescStr;
	}

}
