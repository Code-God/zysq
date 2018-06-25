package model;

import java.io.Serializable;

public class GameDailyReport implements Serializable {

	private static final long serialVersionUID = 673457144415664772L;

	/** 深夜 0:00-8:00 */
	private int a;

	/** 上午 8:00-12:00 */
	private int b;

	/** 下午 12:01-18:00 */
	private int c;

	/** 下班睡前 18:00-24:00 */
	private int d;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}
}
