package com.wfsc.actions.vo;

import java.io.Serializable;

public class PicRecObj implements Serializable {

	private static final long serialVersionUID = -4673881350719430367L;

	private int x1;

	private int y1;

	private int w;

	private int h;
	/** 非常重要的属性，等比缩放后的图片宽度 */
	private float recw;
	
	/** 非常重要的属性，等比缩放后的图片高度 */
	private float rech;

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	
	public float getRecw() {
		return recw;
	}

	
	public void setRecw(float recw) {
		this.recw = recw;
	}

	
	public float getRech() {
		return rech;
	}

	
	public void setRech(float rech) {
		this.rech = rech;
	}
}
