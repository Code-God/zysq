package com.exttool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 水印处理设置参数
 * 
 * @author jacky
 * 
 */
public class MarkConfig {
	/** 即用户授权token */
	private String session;

	/** 区别不同用户的唯一key */
	private String userkey = "userkey";

	/** 当前用户的图片根目录 */
	private String rootPath = "";
	
	/** 是否预览，0 - 否； 1 - 是 */
	private int preview = 0;
	
	/** 预览图片的原图片 */
	private String previewSrcDir;
	
	/** 图片来源 1-本地 2-网络 3-淘宝 */
	private String srcImgType;

	/** 透明度 */
	private float alpha = 0.5f;

	/** 水印的类型 */
	private String markType = "1";

	/** 水印图标的路径，支持本地和网络，自动兼容 */
	private String iconPath = "";

	private int iconWidth = 100;
	
	private int iconHeight = 100;
	
	private String markText = "iTOP水印";

	/** 网络图片路径 */
	private List<String> httpSrcImgPath = new ArrayList<String>();
	
	/**
	 * 店铺商品
	 * key - num_iid 商品数字ID
	 * value - 该商品对应的主图URL
	 */
	private Map<String, String> shopUrls = new HashMap<String, String>();
	

	/** 输出文件目录 */
	private String outputImageDir;

	/** 水印旋转角度 */
	private int degree = 45;

	/** 0 -正中， 1-左上， 2-右上， 3-左下，4-右上 5-自定义坐标 */
	private int location = 0;

	private int x;

	private int y;

	/** 文字大小 */
	private int fontSize = 50;

	/** 字体颜色 */
	private String color = "#fff";

	/** 是否平铺 */
	private int repeat = 0;

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getMarkText() {
		return markText;
	}

	public void setMarkText(String markText) {
		this.markText = markText;
	}

	public String getOutputImageDir() {
		return outputImageDir;
	}

	public void setOutputImageDir(String outputImageDir) {
		this.outputImageDir = outputImageDir;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getRepeat() {
		return repeat;
	}

	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<String> getHttpSrcImgPath() {
		return httpSrcImgPath;
	}

	public void setHttpSrcImgPath(List<String> httpSrcImgPath) {
		this.httpSrcImgPath = httpSrcImgPath;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	
	public String getRootPath() {
		return rootPath;
	}

	
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	
	public int getPreview() {
		return preview;
	}

	
	public void setPreview(int preview) {
		this.preview = preview;
	}

	
	public String getPreviewSrcDir() {
		return previewSrcDir;
	}

	
	public void setPreviewSrcDir(String previewSrcDir) {
		this.previewSrcDir = previewSrcDir;
	}

	
	public int getIconWidth() {
		return iconWidth;
	}

	
	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	
	public int getIconHeight() {
		return iconHeight;
	}

	
	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	
	public String getSrcImgType() {
		return srcImgType;
	}

	
	public void setSrcImgType(String srcImgType) {
		this.srcImgType = srcImgType;
	}

	
	public Map<String, String> getShopUrls() {
		return shopUrls;
	}

	
	public void setShopUrls(Map<String, String> shopUrls) {
		this.shopUrls = shopUrls;
	}

	
	public String getSession() {
		return session;
	}

	
	public void setSession(String session) {
		this.session = session;
	}
}
