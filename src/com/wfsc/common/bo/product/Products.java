package com.wfsc.common.bo.product;

import java.io.Serializable;
import java.util.Date;

import util.SysUtil;

import com.wfsc.util.DateUtil;

/**
 * 商品
 * 
 * @author hulei
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_products" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class Products implements Serializable {

	private static final long serialVersionUID = 3222227777409888948L;

	/**
	 * 数据库ID， 自增
	 */
	private Long id;

	/**
	 * 商品名称， 如：“宝宝粥米 500g”
	 */
	private String name;

	// 处理超长名称的
	private String nameStr;

	/**
	 * 商品规格，不同类商品规格有不同，如“500g”, “2L”“12只装”等
	 */
	private String prdStandard = "";

	/**
	 * 商品价格，为了保证精度，数据库存入该值，实际价格*100
	 */
	private Long price;

	/**
	 * 商品折扣价格，为了保证精度，数据库存入该值，实际价格*100
	 */
	private Long disPrice;

	/** 全网销量 */
	private Long prdSaleCount;

	/**
	 * 商品价格精确到分
	 */
	private Float prdPrice;

	/**
	 * 折扣价， 允许为空
	 */
	private Float prdDisPrice;

	/**
	 * 该商品所属分类code
	 */
	private String prdCatCode;

	private String prdCatName;

	/**
	 * 商品编号，在录入时自动生成， 全局唯一，编号规则：分类code+6位递增数字
	 */
	private String prdCode;

	/**
	 * 该商品显示的图片的URL，第一张图作为大图展示
	 */
	private String picUrl1;

	/**
	 * 该商品显示的图片的URL
	 */
	private String picUrl2;

	/**
	 * 该商品显示的图片的URL
	 */
	private String picUrl3;

	/**
	 * 该商品显示的图片的URL
	 */
	private String picUrl4;

	/**
	 * 该商品显示的图片的URL
	 */
	private String picUrl5;

	/**
	 * 商品详情，图文描述，支持富文本
	 */
	private String prdDesc;

	/**
	 * 品牌故事，支持富文本
	 */
	private String prdStory;

	/**
	 * 库存数量(列表页面商品不限制数量，默认给500库存) 这个属性不入库，只是为了页面显示编码方便
	 */
	private Integer stock = 15;

	/**
	 * 是否是推荐的商品，如果推荐，则显示在首页一级分类精品推荐栏的商品列表，一个一级分类精品商品推荐共7个，1个大图，6个小图。 0 – 不推荐 1-推荐 2-大图推荐
	 */
	private Integer recommend;

	private java.util.Date createDate;

	private String createDateStr;

	private java.util.Date lastModifyDate;

	/**
	 * 该商品支持的城市(cityCode)，多个城市以|分隔，首尾也包含|，例 |38|29| 表示该商品支持上海以及北京地区的配送
	 * 进入商品详情页面或者将商品加入购物车时，检查用户所在城市的cityCode，是否在这个字符串里面，来判断该商品是否支持用户所属区域的配送 如果所在城市不在配送范围之内，直接显示库存为0或者无货
	 */
	private String cityCode;

	private boolean favourite = false;

	/** 销售量 */
	private int scount;

	/** 上架下架 1-上架 0-下架 */
	private int tstatus;

	/** 是否是普通商品， 1 - 普通产品， 0 - 酒店行业 */
	private int normal = 1;

	// ------------------- 非持久
	private int pjcount = 0;

	/** 服务商ID，关联wf_admin表 */
	private Long serviceId;

	/** 服务商名称，非持久 */
	private String serviceName;

	/** 有效期 */
	private Integer expireDays = 30;

	// 洗车卡的属性：
	/** 卡类型：10次卡， 20次卡， 30次卡， 月卡， 半年卡， 年卡等 */
	private String cardType = "C_10";

	private String cardTypeStr;

	public String getCardTypeStr() {
		return SysUtil.getCardTypeStr(cardType);
	}

	public void setCardTypeStr(String cardTypeStr) {
		this.cardTypeStr = cardTypeStr;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getExpireDays() {
		return expireDays;
	}

	public void setExpireDays(Integer expireDays) {
		this.expireDays = expireDays;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdStandard() {
		return prdStandard;
	}

	public void setPrdStandard(String prdStandard) {
		this.prdStandard = prdStandard;
	}

	public Float getPrdPrice() {
		if (this.price != null) {
			return Float.valueOf(this.price / 100f);
		}
		return prdPrice;
	}

	public void setPrdPrice(Float prdPrice) {
		if (prdPrice != null) {
			this.price = (long) (prdPrice.floatValue() * 100);
		}
		this.prdPrice = prdPrice;
	}

	public Float getPrdDisPrice() {
		if (this.disPrice != null) {
			return Float.valueOf(this.disPrice / 100f);
		}
		return prdDisPrice;
	}

	public static void main(String[] args) {
		Float f = Float.valueOf(490 / 100f);
		System.out.print(f);
	}

	public void setPrdDisPrice(Float prdDisPrice) {
		if (prdDisPrice != null) {
			this.disPrice = (long) (prdDisPrice.floatValue() * 100);
		}
		this.prdDisPrice = prdDisPrice;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdCatCode() {
		return prdCatCode;
	}

	public void setPrdCatCode(String prdCatCode) {
		this.prdCatCode = prdCatCode;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdCode() {
		return prdCode;
	}

	public void setPrdCode(String prdCode) {
		this.prdCode = prdCode;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl1() {
		return picUrl1;
	}

	public void setPicUrl1(String picUrl1) {
		this.picUrl1 = picUrl1;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl2() {
		return picUrl2;
	}

	public void setPicUrl2(String picUrl2) {
		this.picUrl2 = picUrl2;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl3() {
		return picUrl3;
	}

	public void setPicUrl3(String picUrl3) {
		this.picUrl3 = picUrl3;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl4() {
		return picUrl4;
	}

	public void setPicUrl4(String picUrl4) {
		this.picUrl4 = picUrl4;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPicUrl5() {
		return picUrl5;
	}

	public void setPicUrl5(String picUrl5) {
		this.picUrl5 = picUrl5;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdDesc() {
		return prdDesc;
	}

	public void setPrdDesc(String prdDesc) {
		this.prdDesc = prdDesc;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdStory() {
		return prdStory;
	}

	public void setPrdStory(String prdStory) {
		this.prdStory = prdStory;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getPrdCatName() {
		return prdCatName;
	}

	public void setPrdCatName(String prdCatName) {
		this.prdCatName = prdCatName;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="createDate"
	 */
	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return
	 * @hibernate.property type="timestamp" column="lastModifyDate"
	 */
	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public boolean isFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	/**
	 * @hibernate.property type="long" column="prdPrice"
	 * @return
	 */
	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	/**
	 * @hibernate.property type="long" column="prdDisPrice"
	 * @return
	 */
	public Long getDisPrice() {
		return disPrice;
	}

	public void setDisPrice(Long disPrice) {
		this.disPrice = disPrice;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getScount() {
		return scount;
	}

	public void setScount(int scount) {
		this.scount = scount;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getTstatus() {
		return tstatus;
	}

	public void setTstatus(int tstatus) {
		this.tstatus = tstatus;
	}

	public String getNameStr() {
		if (name != null && name.length() > 16) {
			return name.substring(0, 16) + "...";
		}
		return name;
	}

	public void setNameStr(String nameStr) {
		this.nameStr = nameStr;
	}

	public int getPjcount() {
		return pjcount;
	}

	public void setPjcount(int pjcount) {
		this.pjcount = pjcount;
	}

	public String getCreateDateStr() {
		return DateUtil.getLongDate(this.createDate);
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getPrdSaleCount() {
		return prdSaleCount;
	}

	public void setPrdSaleCount(Long prdSaleCount) {
		this.prdSaleCount = prdSaleCount;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getNormal() {
		return normal;
	}

	public void setNormal(int normal) {
		this.normal = normal;
	}
}
