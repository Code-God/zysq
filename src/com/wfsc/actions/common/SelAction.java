package com.wfsc.actions.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wfsc.actions.vo.City;
import com.wfsc.actions.vo.Province;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 下拉框,联动下拉框的数据源
 * 
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public class SelAction extends ActionSupport {

	private List<Integer> age;

	private List<Integer> height;

	/** 学历下拉框 */
	private Map<Integer, String> diploma;

	/** 月薪下拉框 */
	private Map<Integer, String> salary;
	
	/** 月份下拉框 */
	private Map<String, String> month;

	private Map<Integer, String> house;

	/** 省份 */
	private List<Province> province;

	private Map<Province, List<City>> citys;
	
	/** 体型 */
	private Map<Integer, String> body;
	/** 相貌 */
	private Map<Integer, String> face;
	
	/** 上海地区下拉框 */
	private Map<Integer, String> area;
	
	public SelAction() {
		age = new ArrayList<Integer>();
		for (int i = 20; i < 71; i++) {
			age.add(i);
		}
		height = new ArrayList<Integer>();
		for (int i = 130; i < 220; i++) {
			height.add(i);
		}
		buildDiploma();
		buildSalary();
		buildHouse();
		buildProvince();
		buildArea();
		buildBody();
		buildFace();
		buildMonth();
	}

	private void buildFace() {
//		0:'50分,一般', 1:'60分,普通',2:'70分,还过得去',3:'80分,中上水平',4:'90分,俊俏靓丽',5:'100分,天使面孔'
		face = new HashMap<Integer, String>();
		face.put(0, "50分,一般");
		face.put(1, "60分,普通");
		face.put(2, "70分,还过得去");
		face.put(3, "80分,中上水平");
		face.put(4, "90分,俊俏靓丽");
		face.put(5, "100分,天使面孔");
	}
	private void buildBody() {
//		0:'弱不禁风', 1:'偏瘦',2:'中等',3:'标准',4:'丰满',5:'微胖',6:'强壮',7:'偏胖',8:'魔鬼身材'
		body = new HashMap<Integer, String>();
		body.put(0, "弱不禁风");
		body.put(1, "偏瘦");
		body.put(2, "中等");
		body.put(3, "标准");
		body.put(4, "丰满");
		body.put(5, "微胖");
		body.put(6, "强壮");
		body.put(7, "偏胖");
		body.put(8, "魔鬼身材");
	}

	private void buildArea() {
		area = new HashMap<Integer, String>();
		area.put(0, "--不限--");
		area.put(1, "黄浦");
		area.put(2, "南区");
		area.put(3, "卢湾");
		area.put(4, "徐汇");
		area.put(5, "长宁");
		area.put(6, "静安");
		area.put(7, "普陀");
		area.put(8, "闸北");
		area.put(9, "虹口");
		area.put(10, "闵行");
		area.put(11, "宝山");
		area.put(12, "嘉定");
		area.put(13, "浦东新区");
		area.put(14, "金山");
		area.put(15, "松江");
		area.put(16, "南汇");
		area.put(17, "奉贤");
		area.put(18, "青浦");
		area.put(19, "崇明");
	}

	/**
	 * 构建省份
	 */
	private void buildProvince() {
		province = new ArrayList<Province>();
		citys = new HashMap<Province, List<City>>();
		Province p1 = new Province(11, "北京");	Province p2 = new Province(12, "天津");	Province p3 = new Province(13, "河北");
		Province p4 = new Province(14, "山西");	Province p5 = new Province(15, "内蒙古");	Province p6 = new Province(21, "辽宁");
		Province p7 = new Province(22, "吉林");	Province p8 = new Province(23, "黑龙江");	Province p9 = new Province(31, "上海");
		Province p10 = new Province(32, "江苏");	Province p11 = new Province(33, "浙江");	Province p12 = new Province(34, "安徽");
		Province p13 = new Province(35, "福建");	Province p14 = new Province(36, "江西");	Province p15 = new Province(37, "山东");
		Province p16 = new Province(41, "河南");	Province p17 = new Province(42, "湖北");	Province p18 = new Province(43, "湖南");
		Province p19 = new Province(44, "广东");	Province p20 = new Province(45, "广西");	Province p21 = new Province(46, "海南");
		Province p22 = new Province(44, "重庆");	Province p23 = new Province(45, "四川");	Province p24 = new Province(46, "贵州");
		Province p25 = new Province(53, "云南");	Province p26 = new Province(54, "西藏");	Province p27 = new Province(61, "陕西");
		Province p28 = new Province(62, "甘肃");	Province p29 = new Province(63, "青海");	Province p30 = new Province(64, "宁夏");
		Province p31 = new Province(65, "新疆");	Province p32 = new Province(71, "台湾");	Province p33 = new Province(81, "香港");
		Province p34 = new Province(82, "澳门");	Province p35 = new Province(99, "外国");
		province.add(p1);		province.add(p2);		province.add(p3);		province.add(p4);		province.add(p5);
		province.add(p6);		province.add(p7);		province.add(p8);		province.add(p9);		province.add(p10);
		province.add(p11);		province.add(p12);		province.add(p13);		province.add(p14);		province.add(p15);
		province.add(p16);		province.add(p17);		province.add(p18);		province.add(p19);		province.add(p20);
		province.add(p21);		province.add(p22);		province.add(p23);		province.add(p24);		province.add(p25);
		province.add(p26);		province.add(p27);		province.add(p28);		province.add(p29);		province.add(p30);
		province.add(p31);		province.add(p32);		province.add(p33);		province.add(p34);		province.add(p35);
		// 北京市
		// COK[11][0] = '-请选择-';
		List<City> p1City = new ArrayList<City>();
		p1City.add(new City(0, "-请选择-"));
		p1City.add(new City(1101, "东城"));
		p1City.add(new City(1102, "西城"));
		p1City.add(new City(1103, "崇文"));
		p1City.add(new City(1104, "宣武"));
		p1City.add(new City(1105, "朝阳"));
		p1City.add(new City(1106, "丰台"));
		p1City.add(new City(1107, "石景山"));
		p1City.add(new City(1108, "海淀"));
		p1City.add(new City(1109, "门头沟"));
		p1City.add(new City(1111, "房山"));
		p1City.add(new City(1112, "通州"));
		p1City.add(new City(1113, "顺义"));
		p1City.add(new City(1121, "昌平"));
		p1City.add(new City(1124, "大兴"));
		p1City.add(new City(1126, "平谷"));
		p1City.add(new City(1127, "怀柔"));
		p1City.add(new City(1128, "密云"));
		p1City.add(new City(1129, "延庆"));
		// 天津
		List<City> p2City = new ArrayList<City>();
		p2City.add(new City(0, "-请选择-"));
		p2City.add(new City(1201, "和平"));
		p2City.add(new City(1202, "河东"));
		p2City.add(new City(1203, "河西"));
		p2City.add(new City(1204, "南开"));
		p2City.add(new City(1205, "河北"));
		p2City.add(new City(1206, "红桥"));
		p2City.add(new City(1207, "塘沽"));
		p2City.add(new City(1208, "汉沽"));
		p2City.add(new City(1209, "大港"));
		p2City.add(new City(1210, "东丽"));
		p2City.add(new City(1211, "西青"));
		p2City.add(new City(1212, "津南"));
		p2City.add(new City(1213, "北辰"));
		p2City.add(new City(1221, "宁河"));
		p2City.add(new City(1222, "武清"));
		p2City.add(new City(1223, "静海"));
		p2City.add(new City(1224, "宝坻"));
		p2City.add(new City(1225, "蓟县"));
		// 上海
		List<City> p9City = new ArrayList<City>();
		p9City.add(new City(0, "-请选择-"));
		p9City.add(new City(3101, "黄浦"));
		p9City.add(new City(3102, "南区"));
		p9City.add(new City(3103, "卢湾"));
		p9City.add(new City(3104, "徐汇"));
		p9City.add(new City(3105, "长宁"));
		p9City.add(new City(3106, "静安"));
		p9City.add(new City(3107, "普陀"));
		p9City.add(new City(3108, "闸北"));
		p9City.add(new City(3109, "虹口"));
		p9City.add(new City(3110, "杨浦"));
		p9City.add(new City(3112, "闵行"));
		p9City.add(new City(3113, "宝山"));
		p9City.add(new City(3114, "嘉定"));
		p9City.add(new City(3115, "浦东新区"));
		p9City.add(new City(3116, "金山"));
		p9City.add(new City(3117, "松江"));
		p9City.add(new City(3125, "南汇"));
		p9City.add(new City(3126, "奉贤"));
		p9City.add(new City(3127, "青浦"));
		p9City.add(new City(3128, "崇明"));
		
		
		// 安徽
		List<City> p12City = new ArrayList<City>();
		p12City.add(new City(0, "-请选择-"));
		p12City.add(new City(3101, "合肥"));
		p12City.add(new City(3102, "芜湖"));
		p12City.add(new City(3103, "蚌埠"));
		p12City.add(new City(3104, "淮南"));
		p12City.add(new City(3104, "马鞍山"));
		p12City.add(new City(3104, "淮北"));
		p12City.add(new City(3104, "铜陵"));
		p12City.add(new City(3104, "安庆"));
		p12City.add(new City(3104, "黄山"));
		p12City.add(new City(3104, "滁州"));
		p12City.add(new City(3104, "阜阳"));
		p12City.add(new City(3104, "宿州"));
		p12City.add(new City(3104, "巢湖"));
		p12City.add(new City(3104, "六安"));
		p12City.add(new City(3104, "亳州"));
		p12City.add(new City(3104, "池州"));
		p12City.add(new City(3104, "宣城"));
		
		citys.put(p1, p1City);
		citys.put(p2, p2City);
		citys.put(p3, p9City);
//		citys.put(p4, p4City);
//		citys.put(p5, p5City);
//		citys.put(p6, p6City);
//		citys.put(p7, p7City);
//		citys.put(p8, p8City);
		citys.put(p9, p9City);
		citys.put(p12, p12City);
	}

	private void buildHouse() {
		house = new HashMap<Integer, String>();
		house.put(0, "-请选择-");
		house.put(1, "暂未购房");
		house.put(2, "计划购置");
		house.put(3, "已购住房");
		house.put(4, "与人合租");
		house.put(5, "独自租房");
		house.put(6, "与父母同住");
		house.put(7, "住亲朋家");
		house.put(8, "单位提供");
	}

	private void buildSalary() {
		salary = new HashMap<Integer, String>();
		salary.put(0, "--请选择--");
		salary.put(1, "2000以下");
		salary.put(2, "2001-3000");
		salary.put(3, "3001-4000");
		salary.put(4, "4001-5000");
		salary.put(5, "5001-6000");
		salary.put(6, "6001-7000");
		salary.put(7, "7001-8000");
		salary.put(8, "8001-9000");
		salary.put(9, "9001-10000");
		salary.put(10, "10001-15000");
		salary.put(11, "15001-20000");
		salary.put(12, "20001-25000");
		salary.put(13, "25001-30000");
		salary.put(14, "30001-50000");
		salary.put(15, "50000+");
	}
	
	
	private void buildMonth() {
		month = new HashMap<String, String>();
		month.put("0", "--请选择--");
		month.put("1", "1月份");
		month.put("2", "2月份");
		month.put("3", "3月份");
		month.put("4", "4月份");
		month.put("5", "5月份");
		month.put("6", "6月份");
		month.put("7", "7月份");
		month.put("8", "8月份");
		month.put("9", "9月份");
		month.put("10", "10月份");
		month.put("11", "11月份");
		month.put("12", "12月份");
	}
	private void buildDiploma() {
		diploma = new HashMap<Integer, String>();
		diploma.put(0, "--请选择--");
		diploma.put(1, "初中");
		diploma.put(2, "高中/中专/职校");
		diploma.put(3, "大专");
		diploma.put(4, "本科");
		diploma.put(5, "硕士");
		diploma.put(6, "博士");
		diploma.put(7, "博士后");
	}

	public List<Integer> getAge() {
		return age;
	}

	public void setAge(List<Integer> age) {
		this.age = age;
	}

	public List<Integer> getHeight() {
		return height;
	}

	public void setHeight(List<Integer> height) {
		this.height = height;
	}

	public Map<Integer, String> getDiploma() {
		return diploma;
	}

	public void setDiploma(Map<Integer, String> diploma) {
		this.diploma = diploma;
	}

	public Map<Integer, String> getSalary() {
		return salary;
	}

	public void setSalary(Map<Integer, String> salary) {
		this.salary = salary;
	}

	public Map<Integer, String> getHouse() {
		return house;
	}

	public void setHouse(Map<Integer, String> house) {
		this.house = house;
	}

	public List<Province> getProvince() {
		return province;
	}

	public void setProvince(List<Province> province) {
		this.province = province;
	}

	public Map<Province, List<City>> getCitys() {
		return citys;
	}

	public void setCitys(Map<Province, List<City>> citys) {
		this.citys = citys;
	}

	
	public Map<Integer, String> getArea() {
		return area;
	}

	public String getAre(Integer key){
		return area.get(key);
	}
	
	public String getProvince(Integer key) {
		for (Province p : this.province) {
			if(p.getId().intValue() == key.intValue()){
				return p.getName();
			}
		}
		return "";
	}
	
	public void setArea(Map<Integer, String> area) {
		this.area = area;
	}

	
	public Map<Integer, String> getBody() {
		return body;
	}

	
	public void setBody(Map<Integer, String> body) {
		this.body = body;
	}

	
	public Map<Integer, String> getFace() {
		return face;
	}

	
	public void setFace(Map<Integer, String> face) {
		this.face = face;
	}

	
	public Map<String, String> getMonth() {
		return month;
	}

	
	public void setMonth(Map<String, String> month) {
		this.month = month;
	}

}
