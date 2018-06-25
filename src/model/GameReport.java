package model;

import java.io.Serializable;

public class GameReport implements Serializable {

	private static final long serialVersionUID = 622067781825008203L;

	private int a;

	private int b;

	private int c;

	private int d;

	private int e;

	private int f;

	private int total;

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

	public int getE() {
		return e;
	}

	public void setE(int e) {
		this.e = e;
	}

	public int getTotal() {
		return this.a + this.b + this.c + this.d + this.e;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}
}
