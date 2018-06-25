package com.wfsc.common.bo.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wfsc.common.bo.ad.AdvConfig;

/**
 * 商品分类
 * 商品分类描述 分类分为一级二级三级……一件商品隶属于一个商品分类，并且应该隶属于该分类的最下层分类
 * 如有机花菜，该商品隶属于有机蔬菜分类，有机蔬菜隶属于蔬菜，蔬菜隶属于蔬菜瓜果分类，蔬菜瓜果属于最上层分类
 * @author Xiaojiapeng
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wf_prdcat" lazy="false"
 * @hibernate.cache usage="read-write"
 */
public class ProductCat implements Serializable {
	
	private static final long serialVersionUID = 3222227777409877948L;
	
	/**非推荐分类**/
	public static int RECOMMEND_NO = 0;
	/**推荐分类**/
	public static int RECOMMEND_YES = 1;
	
	/**推荐分类列表**/
	private List<ProductCat> recommendList = new ArrayList<ProductCat>();
	
	/**子分类列表**/
	private List<ProductCat> childList = new ArrayList<ProductCat>();
	
	/**推荐商品列表**/
	private List<Products> proList = new ArrayList<Products>();
	
	/**大图推荐**/
	private Products bigTop;
	
	/**广告**/
	private AdvConfig ad;
	
	/**
	 * 数据库ID，自增长 
	 */
	private Long id;
	
	/**
	 * 分类名称，如蔬菜瓜果
	 */
	private String name;
	
	/**
	 * 分类编码 3位一级，比如000， 001； 第二级为001002, 001004等， 全局唯一
	 */
	private String code;
	
	/**
	 * 该分类显示的图片的URL 一级分类该字段应有值,所有分类的图片存放在服务器指定目录下（private\category\{orgCode}）
	 */
	private String picUrl;
	
	/**
	 * 父分类Id{@link ProductCat#id}}
	 */
	private Long parentId;
	
	/**
	 * 是否是推荐的分类，如果推荐，则显示在首页一级分类精品推荐栏。
	 * 0 – 不推荐  1-推荐
	 */
	private Integer recommend;
	
	/**
	 * 推荐分类背景色
	 */
	private String bgcolor;
	
	/**
	 * 商品分类等级
	 * 一级 二级 三级
	 */
	private int level;
	
	/** 分销商编码，用来隔离不同分销商的商品分类 */
	private String fxCode;

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
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
		if(code.length() == 3)
			level = 1;
		else if(code.length() ==6)
			level = 2;
		else if(code.length() == 9)
			level = 3;
	}

	/**
	 * @hibernate.property type="string" column="picurl"
	 * @return
	 */
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @hibernate.property type="long" column="pid"
	 * @return
	 */
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @hibernate.property type="int" column="recommend"
	 * @return
	 */
	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	/**
	 * @hibernate.property type="string" column="bgcolor"
	 * @return
	 */
	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public List<ProductCat> getRecommendList() {
		return recommendList;
	}

	public void setRecommendList(List<ProductCat> recommendList) {
		this.recommendList = recommendList;
	}

	public List<ProductCat> getChildList() {
		return childList;
	}

	public void setChildList(List<ProductCat> childList) {
		this.childList = childList;
	}

	public List<Products> getProList() {
		return proList;
	}

	public void setProList(List<Products> proList) {
		this.proList = proList;
	}

	public Products getBigTop() {
		return bigTop;
	}

	public void setBigTop(Products bigTop) {
		this.bigTop = bigTop;
	}

	public AdvConfig getAd() {
		return ad;
	}

	public void setAd(AdvConfig ad) {
		this.ad = ad;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getFxCode() {
		return fxCode;
	}

	
	public void setFxCode(String fxCode) {
		this.fxCode = fxCode;
	}
	
}
