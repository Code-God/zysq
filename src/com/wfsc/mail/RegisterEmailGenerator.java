/**
 * 
 */
package com.wfsc.mail;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.base.mail.Email;
import com.base.mail.HtmlEmailGenerator;
import com.base.tools.Version;

/**
 * 
 * @version 1.0
 */
public class RegisterEmailGenerator extends HtmlEmailGenerator {


	public RegisterEmailGenerator(Email email) {
		super(email);
	}

	@Override
	protected String getHttpUrl() {
		String userName = "";
		String code = "";
		if(email.getParam() != null){
			userName = email.getParam().split("=")[0];
			code = email.getParam().split("=")[1];
		}
		try {
			userName = URLEncoder.encode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 激活码是注册时随机生成的.激活成功后,激活码自动删除.
		String httpUrl = httpServerBasePath + "/public/regist_active.Q?vmRequest=true&userName=" + userName + "&activeCode=" + code;
		return httpUrl;
	}

	@Override
	protected List<String> getMailReceivers() {
		return this.email.getAdditionalAddresses();
	}
	
	

	@Override
	public String getMailContent() {
		return "您已经成功注册成为"+ Version.getInstance().getNewProperty("system.company") +"的会员，请点击此<a href='"+ getHttpUrl() +"'>链接</a>进行激活。激活后即可登录。";
	}

	@Override
	protected String getMailTitle() {
		StringBuilder buffer = new StringBuilder("["+ Version.getInstance().getSystemTitle() +"] ");
		buffer.append(email.getAction());
		buffer.append(": "); 
		buffer.append("请激活账号");
		return buffer.toString();
	}
}