package util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.wfsc.util.DateUtil;

/**
 * 对字符串和集合进行判断和处理的工具类
 * @author miaodongming
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public class CommonUtil {
	
	public static final String GB2312 = "GB2312";
	public static final String UTF8 = "UTF-8";
	public static final String ISO88591 = "ISO8859-1";
	public static final String IPV4_REGEX="^(?:(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}(?:[01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\b";//ipv4正则表达式
	public static final String IPV6_REGEX="^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$|^:((:[\\da-fA-F]{1,4}){1,6}|:)$|^[\\da-fA-F]{1,4}:((:[\\da-fA-F]{1,4}){1,5}|:)$|^([\\da-fA-F]{1,4}:){2}((:[\\da-fA-F]{1,4}){1,4}|:)$|^([\\da-fA-F]{1,4}:){3}((:[\\da-fA-F]{1,4}){1,3}|:)$|^([\\da-fA-F]{1,4}:){4}((:[\\da-fA-F]{1,4}){1,2}|:)$|^([\\da-fA-F]{1,4}:){5}:([\\da-fA-F]{1,4})?$|^([\\da-fA-F]{1,4}:){6}:$";//ipv6正则表达式
	
	//**********************************************************************
	//   ***************       以下是字符串判断处理方法         ***************
	//**********************************************************************
	/**
	 * 使用utf-8编码机制对字符串编码
	 * @param s
	 * @return
	 */
	public static String encodeUseUTF8(String s) {
		String result = trim(s);
		try {
			if (isEmpty(result)) return "";
			return java.net.URLEncoder.encode(result, UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return result;
		}
	}
	
	/**
	 * 使用utf-8编码机制对字符串解码
	 * @param s
	 * @return
	 */
	public static String decodeUseUTF8(String s) {
		String result = trim(s);
		try {
			if (isEmpty(result)) return "";
			return URLDecoder.decode(result, UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return result;
		}
	}
	
	/**
	 * 去除字符串两端的空格 
	 * @param s
	 * @return
	 */
	public static String trim(String s) {
		return StringUtils.trimToEmpty(s);
	}
	
	/**
	 * 
	 * 判断字符串是否为空 
	 * @param s
	 * @return
	 */
	public static String trimToEmpty(Object s) {
	    if(null ==s )return "";
	    if (s instanceof String) {
		String new_name = (String) s;
		return trimToEmpty(new_name);
	    }else{
		throw new ClassCastException("is not String ");
	    }
	}
	/**
	 * 
	 * 判断字符串是否为空 
	 * @param s
	 * @return
	 */
	public static String trimToNull(Object s) {
	    if(null ==s )return null;
	    if (s instanceof String) {
		String new_name = (String) s;
		return trimToNull(new_name);
	    }else{
		throw new ClassCastException("is not String ");
	    }
	}
	
	/**
	 * 去掉字符串两端的控制符(control characters, char <= 32) ,如果变为 null 或""，则返回 null
	 * @param s
	 * @return
	 */
	public static String trimToNull(String s) {
		return StringUtils.trimToNull(s);
	}
	
	/**
	 * 去掉字符串两端的控制符(control characters, char <= 32) ,如果变为 null 或 "" ，则返回 ""
	 * @param s
	 * @return
	 */
	public static String trimToEmpty(String s) {
		return StringUtils.trimToEmpty(s);
	}
	
	/**
	 * 判断字符串是否为空 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null || s.length() == 0)
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否非空 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}
	
	/**
	 * 将null值处理为空字符串返回
	 * @param s
	 * @return
	 */
	public static String getNull2Value(String s) {
		return isNotEmpty(s) ? s : "";
	}
	
	//**********************************************************************
	//   ***************       以下是集合判断处理方法         ***************
	//**********************************************************************
	/**
	 * 判断集合是否为空 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(java.util.Collection coll) {
		if (coll == null || coll.size() == 0)
			return true;
		return false;
	}
	
	/**
	 * 判断集合是否非空 
	 * @param s
	 * @return
	 */
	public static boolean isNotEmpty(java.util.Collection coll) {
		return !isEmpty(coll);
	}
	
//	/**
//	 * 排序工具类
//	 * 
//	 * @param list
//	 * @param methodName
//	 * @param sortType
//	 */
//	@SuppressWarnings("unchecked")
//	public static void sortList(List list, String methodName, boolean sortType){
//		Collections.sort(list, Collections.reverseOrder(new ListComparator(methodName, sortType)));
//	}
	
	/**
	 * 排序工具类
	 * @param <E>
	 */
	public static class SortList<E> {
		public void sort(List<E> list, final String method, final String sort){
			Collections.sort(list, new Comparator<Object>(){			
				public int compare(Object a, Object b){
					int ret = 0;
					try{
						Method m1 = ((E)a).getClass().getMethod(method, null);
						Method m2 = ((E)b).getClass().getMethod(method,null);
						if(sort != null && "desc".equals(sort)){//倒序
							ret= m2.invoke(((E)b),null).toString().compareTo(m1.invoke(((E)a), null).toString());
						}else{
							ret = m1.invoke(((E)a), null).toString().compareTo(m2.invoke(((E)b), null).toString());
						}
					}catch(NoSuchMethodException e){
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					return ret;
				}
			}
			);
		}
	}
	/**
	 * 获取URL中的参数值
	 * @param url url
	 * @param parameter 参数名称
	 * @return 参数值
	 * 例如：url: http://192.168.0.1:81/itsm/workArea/main.do?method=doExecute&topMenuId=net1&my=my10&viewId=0&viewId=10
	 *  parameter: viewId
	 *  返回0
	 */
	public static String getUrlParameterValue(String url,String parameter){
		String result=null;
		String regx="(?:("+parameter+"\\=)).*?(?=(\\&|$))";
		Pattern p = Pattern.compile(regx);
		Matcher m = p.matcher(url);
        if (m.find()) {
           String str =m.group();
           result=str.split("=")[1];
        }
		return result;
	}
	/**
	 * 替换URL中指定参数值
	 * @param url url
	 * @param parameter 参数名称
	 * @param newValue 参数新值
	 * @return 替换后URL
	 * 例如:url: http://192.168.0.1:81/itsm/workArea/main.do?method=doExecute&topMenuId=net1&my=my10&viewId=0&viewId=10
	 *  parameter: viewId
	 *  newValue: 20
	 * 返回:http://192.168.0.1:81/itsm/workArea/main.do?method=doExecute&topMenuId=net1&my=my10&viewId=20&viewId=20
	 */
	public static String replaceUrlParameterValue(String url,String parameter,String newValue){
		String regex="(?:("+parameter+"\\=)).*?(?=(\\&|$))";
		String replacement=parameter+"="+newValue;
		return url.replaceAll(regex, replacement);
	}
	/**
	 * 获取当前系统所在机器IP
	 * @return IP
	 */
	public static String getRealIp() {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException se) {
            se.printStackTrace();
        }
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        if (netInterfaces != null) {
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
	/**
	 * 将List<Object>转换成SQL IN 语句中的"a,b"格式
	 * @param data 数据列表
	 * @return 字符串
	 */
	public static String getSqlInStatementFromList(List data){
		StringBuffer result=new StringBuffer();
		if(data!=null && data.size()>0){
			for(int i=0;i<data.size();i++){
				if(i!=data.size()-1){ 
					result.append(data.get(i).toString()).append(",");
				}else{
					result.append(data.get(i).toString());
				}
			}
		}
		return result.toString();
	}
//	public  static void main(String args[]){
//		String url="http://192.168.0.1:81/itsm/workArea/main.do?method=doExecute&topMenuId=net1&my=my10&viewId=0&viewId=10";
//		String newUrl=replaceUrlParameterValue(url,"viewId","20");
//		System.out.println(newUrl);
//	}
	
	
	public static List<String> getResumeColumns(){
//		序号	照片	期望薪资	姓名	性别	身份证号码	联系方式	出生年月	初始学历	毕业院校	专业
//		最高学历	毕业院校	专业	职业资格	职称	居住地	QQ号码	最近工作单位	就职时间区间	职务	
//		薪资范围	主要工作内容业绩	第二工作单位	就职时间区间	职务	薪资范围	主要工作内容业绩	第三工作单位	就职时间区间	
//		职 务	辞职原因	薪资范围	主要工作内容业绩	评价职业方向	背景调查	面试评价得分	面试者	
//		推荐公司1	推荐岗位1	推荐时间	录用情况	推荐公司2	推荐岗位2	推荐时间	录用情况	备注	推荐公司3	推荐岗位3	 推荐时间 录用情况	备注

		List<String> sheetColumn = new ArrayList<String>();
		sheetColumn.add("序号");
		sheetColumn.add("照片");
		sheetColumn.add("期望薪资");
		sheetColumn.add("姓名");
		sheetColumn.add("性别");
		sheetColumn.add("身份证号码");
		sheetColumn.add("联系方式");
		sheetColumn.add("出生年月");
		sheetColumn.add("初始学历");
		sheetColumn.add("毕业院校");
		sheetColumn.add("专业");
		sheetColumn.add("最高学历");
		sheetColumn.add("毕业院校");
		sheetColumn.add("专业");
		sheetColumn.add("职业资格");
		sheetColumn.add("职称");
		sheetColumn.add("居住地");
		sheetColumn.add("QQ号码");
		sheetColumn.add("最近工作单位");
		sheetColumn.add("就职时间区间");
		sheetColumn.add("职务");
		sheetColumn.add("薪资范围");
		sheetColumn.add("主要工作内容业绩");
		sheetColumn.add("第二工作单位");
		sheetColumn.add("就职时间区间");
		sheetColumn.add("职务");
		sheetColumn.add("薪资范围");
		sheetColumn.add("主要工作内容业绩");
		sheetColumn.add("第三工作单位");
		sheetColumn.add("就职时间区间");
		sheetColumn.add("职 务");
		sheetColumn.add("辞职原因");
		sheetColumn.add("薪资范围");
		sheetColumn.add("主要工作内容业绩");
		sheetColumn.add("评价职业方向");
		sheetColumn.add("背景调查");
		sheetColumn.add("面试评价得分");
		sheetColumn.add("面试者");
		sheetColumn.add("推荐公司1");
		sheetColumn.add("推荐岗位1");
		sheetColumn.add("推荐时间");
		sheetColumn.add("录用情况");
		sheetColumn.add("推荐公司2");
		sheetColumn.add("推荐岗位2");
		sheetColumn.add("推荐时间");
		sheetColumn.add("录用情况");
		sheetColumn.add("备注");
		sheetColumn.add("推荐公司3");
		sheetColumn.add("推荐岗位3");
		sheetColumn.add("推荐时间");
		sheetColumn.add("录用情况");
		sheetColumn.add("备注");
		return sheetColumn;
	}
	public static List<String> getCompanyColumns(){
//		序号	企业名称	地址	公司简介	联系人	联系电话	公司网址	
//		招聘职位1	职位说明及要求	招聘职位2	职位说明及要求	招聘职位3	职位说明及要求	招聘职位4	职位说明及要求	招聘职位5	职位说明及要求	
//		高端职位1	职位说明及要求	高端职位2	职位说明及要求

		List<String> sheetColumn = new ArrayList<String>();
		sheetColumn.add("序号");
		sheetColumn.add("企业名称");
		sheetColumn.add("地址");
		sheetColumn.add("公司简介");
		sheetColumn.add("联系人");
		sheetColumn.add("联系电话");
		sheetColumn.add("公司网址");
		sheetColumn.add("招聘职位1");
		sheetColumn.add("职位说明及要求");
		sheetColumn.add("招聘职位2");
		sheetColumn.add("职位说明及要求");
		sheetColumn.add("招聘职位3");
		sheetColumn.add("职位说明及要求");
		sheetColumn.add("招聘职位4");
		sheetColumn.add("职位说明及要求");
		sheetColumn.add("招聘职位5");
		sheetColumn.add("职位说明及要求");
		sheetColumn.add("高端职位1");
		sheetColumn.add("职位说明及要求");
		sheetColumn.add("高端职位2");
		sheetColumn.add("职位说明及要求");
		return sheetColumn;
	}
	
	public static List<String> getFeedBackColumns(){
		List<String> sheetColumn = new ArrayList<String>();
		sheetColumn.add("序号");
		sheetColumn.add("姓名");
		sheetColumn.add("电话");
		sheetColumn.add("反馈内容");
		sheetColumn.add("反馈时间");
		return sheetColumn;
	}
	
	/**
	 * 1 ---> 0001, 12---->0012
	 * 
	 * @param num ----需要格式化的数字
	 * @param length -- 补0的个数
	 * @return
	 */
	public static String formateCode(int num, int length) {
		return String.format("%0" + length + "d", num);
	}
	
	/**
	 * 随机生成4,5 
	 * @return
	 */
	public static int randomScore(){
		int result = 4;
		Random r = new Random();
		int i = r.nextInt(20);
		if(i > 7){
			result = 5;
		}
		return result;
	}

	/**
	 * 随机评价描述 
	 * @return
	 */
	public static String randomPjDesc() {
		String[] descList = new String[]{"不错，是正品！","东西不错，物流也快","挺好的，给好评、、",
				"物流块，发票麻烦快点哦，谢谢、、","用了， 确实不错，来给个好评","用户默认好评",
				"物流再快点就好了，好评","这么快就到啦~~~~","用户默认好评",
				"好评， 请问你们在上海有实体店吗？","好好好！！","嘿嘿， 第一次买， 给个好评。",
				"东西不错， 很快捷！好评","必须好评！！！","朋友的微商， 不错， 东西好就行！好评。",
				"担心是多余的， 东西确实好！ ","。。。。。不知道说什么， 先给好评吧","好东西，熟人推荐，比淘宝靠谱！",
				"好评好评好评！ ","这种模式挺好的，直营分销，支持， 好评！","支付很顺畅，好！",
				"好评， 另外我也想做代理怎么做？ ","挺好的， 我也想做呢！","好评， 我已经申请做分销商了， 客服快联系我哦！",
				" 用户选择好评，无评论 ","用户选择好评，无评论","用户选择好评，无评论",
				" 用户选择好评，无评论 ","用户选择好评，无评论","用户选择好评，无评论",
				" 用户选择好评，无评论 ","用户选择好评，无评论","用户选择好评，无评论",
				"是正品！ ","好！很好！","支持一个！","用户默认好评","好评","good！ ",};
		
		Random r = new Random();
		int nextInt = r.nextInt(descList.length);
		return	descList[nextInt];
	}

	/**
	 * 随机昵称 
	 * @return
	 */
	public static String randomNickName() {
		String[] nickNameLib =  {"BAEK°","along","KING。","crazyMaybe","BlaVer","Fairy°Story°","star |","人生如梦","吕泽001","曹阳","Crazy゜",
				"Summer*","Demon、微光","Angle、微眸","Aries°气质","Abandon丶","Sadness。","—— end °","Sunny°刺眼","	Sunny°刺眼","Moment °superman",
				"	Sam| 绝情△","	Black 黑色","Devil·心碎","玩心少年、boy","Death、－宁","	Sily°小晴天","So what、","One day *",".·　Alone°",
				"Story！剧终°baby","Fairy°","Tears","Cut love！","Shoulderˆ","HEY1 贵人诱惑","Amour · 旧爱","Sunshine °","· 迷情queen°","- superman",
				"starry.","凉兮* Armani","暮夏-Gentle°","我心有BIGBANG","hate you 彡","心动则痛 Oath°","Sandm ° 旧颜","经年°reminis","Aomr゛心渃相依゜",
				"肆虐 *Raging","	黛文 Make-up","潮牌Pet miss","浮夸生 Easonら","Redundant°","安分°　Moon","	Freedom","Let me go °","肆虐ヽ Raging"};
		
		Random r = new Random();
		int nextInt = r.nextInt(nickNameLib.length);
		return	nickNameLib[nextInt];
	}

	/**
	 * 随机日期 
	 * @return
	 */
	public static String randomDate() {
		//过去3个月的时间
		Date d = new Date();
		long start = d.getTime() - 3* 30 * 24 * 60 * 60 * 1000;
		//随机增加80天内的毫秒数
		Random r = new Random();
		int days = r.nextInt(80);
		long randomDateLong = start + days * 24 * 60 * 60 * 1000;
		
		//随机减去几个小时
		int hours = r.nextInt(20);
		
		//随机加上几秒
		return DateUtil.getLongDate(randomDateLong - hours * 60 * 60 * 1000 + r.nextInt(60) * 1000);
	}
	
	/**
	 * 将数字格式的字符串按指定分隔符处理后，转为list
	 * 
	 * @param string 需要处理的字符串，该字符串传入的都是数字
	 * @param regex 分割符
	 * @return
	 */
	public static List<Long> string2LongList(String string, String regex) {
		if (isNotEmpty(string)) {
			try {
				List<Long> list = new ArrayList<Long>();
				String[] temp = string.split(regex);
				for (String s : temp) {
					list.add(Long.valueOf(s));
				}
				return list;
			} catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		}	
		return null;
	}
	
	public static void main(String[] args){
		for(int i=0; i<100; i++){
			System.out.println(randomDate());
		}
	}
	
}
