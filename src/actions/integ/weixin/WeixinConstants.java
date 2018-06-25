package actions.integ.weixin;

import com.base.tools.Version;

public class WeixinConstants {

	/** 订阅消息 */
	public static final String MSG_SUBSCRIBE = "subscribe";

	/** 取消订阅消息 */
	public static final String MSG_UNSUBSCRIBE = "unsubscribe";
	
	/** 多客服关闭会话 */
	public static final String KF_CLOSE_SESSION = "kf_close_session";

	// -------------------
	/** 订阅反馈信息 */
	public static final String RTMSG_SUBSCRIBE = "您好，感谢您关注竹叶青茶业官方微信。竹叶青茶以峨眉山高山茶芽为载体、用45000颗的精选，造就每一颗竹叶青都拥有不凡的清醇淡雅，每一颗竹叶青都为你收藏阳光、雨露，还有清明前最美的峨眉风光。"
			+ "【竹叶青茶】现已开通自定义回复菜单，欢迎点击您感兴趣的内容，老竹将定期更新精彩内容以飨茶友，期待您一如既往的支持。"
			+ "回复数字“1”至“6”的任意数字，均可进入相应点播，欢迎进一步咨询。1＝品牌文化 2＝产品介绍 3＝门店信息 4=产品价格 5=明星产品 6＝促销活动";

	/** 命令列表 */
	// public static final String CMDLIST = "♥欢迎使用以下命令(不区分大小写)进行互动，如果您是VIP用户，还可以使用各种增值交友服务：\n\t#help 帮助\n\t#act 查询活动\n\t#bind
	// 用爱情密码绑定VIP账号\n\t#maleN(增值) 查询年龄为N的男性资料\n\t#femaleN(增值) 查询年龄为N的女性资料\n\t#my 我的账号\n\t#sign 活动报名\n\t#reg 注册个人信息\n\t#total
	// 平台会员总数\n\t";
	/**
	 * 获取CMD列表，从配置文件获取
	 */
	public static String getCMDList() {
		return Version.getInstance().getNewProperty("wx_cmdList");
	}

	/**
	 * 获取错误信息
	 */
	public static String getErrorInfo() {
		return Version.getInstance().getNewProperty("wx_error");
	}

	/**
	 * 获取注册成功信息
	 */
	public static String getRegOk() {
		return Version.getInstance().getNewProperty("wx_regok");
	}
	/**
	 * 获取注册提醒信息
	 */
	public static String getRegTip() {
		return Version.getInstance().getNewProperty("wx_regtip");
	}

	public static String getBirthErr() {
		return Version.getInstance().getNewProperty("wx_birth_err");
	}

	public static String getRegFormatErr() {
		return Version.getInstance().getNewProperty("wx_regfmt_err");
	}
}
