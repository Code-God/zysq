/**
 * 
 */
package com.wfsc.mail;

import java.util.List;

import com.base.mail.Email;
import com.base.mail.HtmlEmailGenerator;
import com.base.tools.Version;

/**
 * 普通邮件生成器
 * @version 1.0
 */
public class CommonEmailGenerator extends HtmlEmailGenerator {


	public CommonEmailGenerator(Email email) {
		super(email);
	}

	@Override
	protected String getHttpUrl() {
		return "http://www.amylove.cn";
	}

	@Override
	protected List<String> getMailReceivers() {
		return this.email.getAdditionalAddresses();
	}
	

	@Override
	public String getMailContent() {
		String extStr = "亲爱的会员您好! ";
		if(this.email.getParam() != null){
			extStr = "您很幸运的获得了微信平台绑定密码为：" + email.getParam() + ", 您可以登录官方微信（amylove-cn），回复'绑定'即可进入绑定页面。<br/>";
		}
		
		String helpUrl1 = Version.getInstance().getNewProperty("helpUrl1");
		String helpUrl2 = Version.getInstance().getNewProperty("helpUrl2");
		
		return "您已经成功注册成为"+ Version.getInstance().getNewProperty("system.company") +"的会员。<br/>" 
				+ extStr
				+ "您可以点<a href='"+ getHttpUrl() +"'>【这里】</a>直接登录网站。<br/>您的用户名为邮箱地址，初始密码为11111111，请登录网站后尽快修改。<hr/>"
				+ "想了解绑定密码相关或更多帮助信息请查看：<br/>"
				+ helpUrl1 +"<br/>" 
				+ helpUrl2+"<br/>";
	}

	@Override
	protected String getMailTitle() {
		StringBuilder buffer = new StringBuilder("["+ Version.getInstance().getSystemTitle() +"] ");
		buffer.append(email.getAction());
		buffer.append(": "); 
		buffer.append("欢迎加入爱觅网");
		return buffer.toString();
	}
}