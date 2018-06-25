package com.wfsc.msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
/**
 * 短消息发送 
 *
 * @author Administrator
 * @version 1.0
 * @since cupid 1.0
 */
public class Sender {

	private String comName;

	private String comPwd;

	private String Server;

	private static Sender sender;
	
	protected  Sender() {
		
	}
	
	public synchronized  static Sender getInstance(){
		if(sender == null){
			sender = new Sender();
		}
		return sender;
	}

	public Sender(String name, String pwd) {
		comName = name;
		comPwd = pwd;
		Server = "http://www.china-sms.com";
	}

	public Sender(String name, String pwd, int serverNum) {
		comName = name;
		comPwd = pwd;
		if (serverNum == 2)
			Server = "http://www6.china-sms.com";
		else
			Server = "http://www.china-sms.com";
	}

	public String massSend(String dst, String msg, String time, String subNo) {
		String sUrl = null;
		try {
			sUrl = Server + "/send/gsend.asp?name=" + comName + "&pwd=" + comPwd + "&dst=" + dst + "&msg="
					+ URLEncoder.encode(msg, "GB2312") + "&time=" + time + "&sender=" + subNo;// �������GB2312���򷢵��ֻ�����
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}
		return getUrl(sUrl);
	}

	public String readSms() {
		String sUrl = null;
		sUrl = Server + "/send/readsms.asp?name=" + comName + "&pwd=" + comPwd;
		try {
			URLEncoder.encode(sUrl, "GB2312");// linux�±����GB18030��UTF-8
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}
		return getUrl(sUrl);
	}

	public String getFee() {
		String sUrl = null;
		sUrl = Server + "/send/getfee.asp?name=" + comName + "&pwd=" + comPwd;
		return getUrl(sUrl);
	}

	public String changePwd(String newPwd) {
		String sUrl = null;
		sUrl = Server + "/send/cpwd.asp?name=" + comName + "&pwd=" + comPwd + "&newpwd=" + newPwd;
		try {
			URLEncoder.encode(sUrl, "GB2312");// linux�±����GB18030��UTF-8
		} catch (UnsupportedEncodingException uee) {
			System.out.println(uee.toString());
		}
		return getUrl(sUrl);
	}

	public String getUrl(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;)
				sb.append(line + "\n");
			reader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return sb.toString();
	}
	/**
	 * 发送手机验证码 
	 * @param code
	 * @param tel
	 */
	public void sendVerifyCode(String code, String tel){
		// 存放调用返回的结果,判断成功与否应该分析这个字符串
		String returnCode;
		// 这里修改成你自己的用户名和密码
		Sender sms = new Sender("huevan", "wy982932");
		//
		// 这里修改成要发送的手机号码和发送内容
		returnCode = sms.massSend(tel, "您好,手机验证码为:"+ code +",请登录丘比特在线进行验证.", "时间", "特服代码");
		String encode = null;
		try {
			encode = new String(returnCode.getBytes("GBK"), "GB2312");
			System.out.println(encode);
		} catch (Exception e) {
			System.out.println("异常");
		}
	}
	

	public void testSend() {
		// 存放调用返回的结果,判断成功与否应该分析这个字符串
		String returnCode;
		// 这里修改成你自己的用户名和密码
		Sender sms = new Sender("huevan", "wy982932");
		//
		// 这里修改成要发送的手机号码和发送内容
		returnCode = sms.massSend("15821754802", "您好,手机验证码为2345,请登录丘比特在线进行验证.", "时间", "特服代码");
		String encode = null;
		try {
			encode = new String(returnCode.getBytes("GBK"), "GB2312");
			System.out.println(encode);
		} catch (Exception e) {
			System.out.println("异常");
		}
	}

	public static void main(String[] args) {
		Sender s = new Sender();
		s.testSend();
	}
}
