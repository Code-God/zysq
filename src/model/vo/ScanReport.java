package model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScanReport implements Serializable {

	private static final long serialVersionUID = -6722145086380783151L;

	/**
	 * 数据
	 */
	private List<DataObject> datas = new ArrayList<DataObject>();
	
	//
	private int a;

	private int b;

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


	public int getTotal() {
		int total = 0;
		for (DataObject doo : this.datas) {
			total += Integer.valueOf(doo.getValue());
		}
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	
	public List<DataObject> getDatas() {
		return datas;
	}

	
	public void setDatas(List<DataObject> datas) {
		this.datas = datas;
	}
	
}
