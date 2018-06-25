package actions.integ.weixin;

/**
 * 微信公众平台命令解析接口 
 *  $帮助	$help		显示所有命令以及说明
	$活动	$act		查询最近发起的活动
	$绑定	$bind		绑定会员账号命令，微信会员在爱觅网成为付费会员后会得到一个6位的“爱密码”xxxxxx，可以在公众平台进行绑定，通过"$bind xxxxxx"或"$绑定 xxxxxx"命令来成为VIP用户,绑定后可以获得增值服务。
	$男N		$maleN		查询3位男性年龄为N岁的会员信息, 如$male28（等同于$男28）
	$女N		$femaleN	查询3位女性年龄为N岁的会员信息, 如$female28（等同于$女28）
	$账户	$my			"查询自己的账号等级信息以及享受的增值服务有效期。如：
							------------------------------
							您在爱觅网的账号为：lily123, 
							会员级别：VIP1
							会员有效期至：2014-07-02
							------------------------------
							"
	$报名	$sign		活动报名。
	$注册	$reg		注册用户信息，为了系统更好的提供婚恋匹配服务。
	$人数	$total		显示平台所有的注册会员数
 * @author jacky
 * @version 1.0
 * @since cupid 1.0
 */
public interface WxCmdInterceptor {
	//命令集， 忽略大小写
	/** 帮助 */
	public static final String $HELP = "help";
	/** 查询最近活动 */
	public static final String $ACT = "act";
	/** 会员账号绑定， bind  xxxxxx */
	public static final String $BIND = "bind";
	/** 我的账号信息 */
	public static final String $MY = "my";
	/** 活动自助报名 */
	public static final String $SG = "sign";
	/** 自助注册 */
	public static final String $REG = "reg";
	/** 自助注册信息修改 */
	public static final String $REGMOD = "regmod";
	/** 平台会员总数 */
	public static final String $TOTAL = "total";
	
	
	/** 推送男会员（增值） */
	public static final String $maleN = "male";
	/** 推送女会员（增值） */
	public static final String $femaleN = "female";
	
	//调查命令
	public static final String $survey = "调查";
	
	/**
	 * 命令解析，返回服务器响应 
	 * 
	 * @param openId - 用户微信openId
	 * @param cmd
	 * @return
	 */
	public String intercept(String openId, String cmd);
	
}
