package model.bo.car;

import java.io.Serializable;

/**
 * 车辆保险信息
 * 
 * @author jacky
 * @version 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="car_carInsInfo" lazy="false"
 */
public class CarInsurenceInfo implements Serializable {

	private static final long serialVersionUID = 1859917058549570104L;

	private Long id;

	/** 此保险方案关联的车辆信息 */
	private CarInfo carInfo;

	private int jiaoqiangxian;

	private int chechuan;

	private int cheliangsunshi;
	
	/** 不计免赔 */
	private int bujimianpei;
	
	/** 司机不计免赔 */
	private int sijibujimianpei;
	
	/** 乘客不计免赔 */
	private int chengkebujimianpei;
	
	private int disanfang;

	private int quanchedaoqiang;

	private int bolidandu;

	private int ziransunshi;

	private int sheshuisunshi;

	private int huaheng;

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
	 * @hibernate.many-to-one class="model.bo.car.CarInfo" column="carInfoId" not-null="false"
	 */
	public CarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getJiaoqiangxian() {
		return jiaoqiangxian;
	}

	public void setJiaoqiangxian(int jiaoqiangxian) {
		this.jiaoqiangxian = jiaoqiangxian;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getChechuan() {
		return chechuan;
	}

	public void setChechuan(int chechuan) {
		this.chechuan = chechuan;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getCheliangsunshi() {
		return cheliangsunshi;
	}

	public void setCheliangsunshi(int cheliangsunshi) {
		this.cheliangsunshi = cheliangsunshi;
	}
	

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getBujimianpei() {
		return bujimianpei;
	}

	
	public void setBujimianpei(int bujimianpei) {
		this.bujimianpei = bujimianpei;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getDisanfang() {
		return disanfang;
	}

	public void setDisanfang(int disanfang) {
		this.disanfang = disanfang;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getQuanchedaoqiang() {
		return quanchedaoqiang;
	}

	public void setQuanchedaoqiang(int quanchedaoqiang) {
		this.quanchedaoqiang = quanchedaoqiang;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getBolidandu() {
		return bolidandu;
	}

	public void setBolidandu(int bolidandu) {
		this.bolidandu = bolidandu;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getZiransunshi() {
		return ziransunshi;
	}

	public void setZiransunshi(int ziransunshi) {
		this.ziransunshi = ziransunshi;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getSheshuisunshi() {
		return sheshuisunshi;
	}

	public void setSheshuisunshi(int sheshuisunshi) {
		this.sheshuisunshi = sheshuisunshi;
	}
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getHuaheng() {
		return huaheng;
	}

	public void setHuaheng(int huaheng) {
		this.huaheng = huaheng;
	}
	
	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getSijibujimianpei() {
		return sijibujimianpei;
	}

	
	public void setSijibujimianpei(int sijibujimianpei) {
		this.sijibujimianpei = sijibujimianpei;
	}

	/**
	 * @hibernate.property type="int"
	 * @return
	 */
	public int getChengkebujimianpei() {
		return chengkebujimianpei;
	}

	
	public void setChengkebujimianpei(int chengkebujimianpei) {
		this.chengkebujimianpei = chengkebujimianpei;
	}
}
