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
public class BindCodeEmailGenerator extends HtmlEmailGenerator {


	public BindCodeEmailGenerator(Email email) {
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
			extStr = "您很幸运的获得了微信平台绑定密码为：" + email.getParam() + ", 您可以登录官方认证微信服务号（amylove-cn，或直接搜索公众号：上海爱觅），回复'绑定'即可进入绑定页面。<br/>";
		}
		return extStr;
	}

	@Override
	protected String getMailTitle() {
		StringBuilder buffer = new StringBuilder("["+ Version.getInstance().getSystemTitle() +"] ");
		buffer.append(email.getAction());
		buffer.append(": "); 
		buffer.append("爱觅网绑定码");
		return buffer.toString();
	}
}