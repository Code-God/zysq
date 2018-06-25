package actions.integ.weixin;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;

/**
 * 基本解析类 
 * 
 * 
 * 
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

 *
 * @author jacky
 * @version 1.0
 * @since cupid 1.0
 */
public class BaseInterceptor implements WxCmdInterceptor {
	
	private Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	public static final String TEMP = "♥暂未开放...即将推出...敬请期待♥ ^_^";
	public static final String MSG_1 = "♥对不起，请先通过#bind命令绑定成为VIP会员。";
	
	@Override
	public String intercept(String openId, String cmd) {
		
		//帮助
		if($HELP.equalsIgnoreCase(cmd)){
			logger.info("-----help");
			return WeixinConstants.getCMDList();
		}
		//活动查询
		if($ACT.equalsIgnoreCase(cmd)){
			return "♥活动正在酝酿中，敬请关注。";
		}
		
		//个人账户
		if($MY.equalsIgnoreCase(cmd)){
			return "♥您的账号类型：\n\t• 普通 \n\t• 是否绑定平台：否 \n\t• 余额：0元";
		}
		
		//平台人数：300基数+天数*10
		if($TOTAL.equalsIgnoreCase(cmd)){
			Calendar cl = Calendar.getInstance();
			cl.set(2014, 0, 23);
			long time = cl.getTime().getTime();
			long t = System.currentTimeMillis() - time;
			int days = Math.round(t/(1000 * 60 * 60 * 24));
//			Random r = new Random();
			return "♥当前平台活跃人数： " + (300 + days * 10)  + "人";
		}
		
		return "♥系统暂不支持此命令♥ ^_^";
	}
	
//	public static void main(String[] args){
//        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=Sv1MA_nwKURUfNUWEusf0IOu6DpAJTQIuR0kLZ4xnj283_o9wDTS8WsLRM-AQ9q36OoWj09gbLgJit5LCZwfV9eHpbn3f4ARrlUsU-JaOxn_khR4Qnm0UDXz6NCG06PP6J3U4rP-x-kJvl87Nzb87Q";
//        /**
//         * 设置菜单
//         * 在为什么用\"你懂得,这是java代码
//         */
//        String responeJsonStr = "{"+
//                "\"button\":["+
//                    "{\"name\":\"菜单名称1\","+
//                    "\"type\":\"click\"," +
//                    "\"key\":\"V01_S01\"" +
//                    "},"+
//                    "{\"name\":\"菜单名称2\","+
//                    "\"type\":\"click\"," +
//                    "\"key\":\"V02_S01\"" +
//                    "},"+
//                    "{\"name\":\"菜单名称1\","+
//                    "\"type\":\"click\"," +
//                    "\"key\":\"V03_S01\"" +
//                    "}"+
//                "]"+
//            "}";
//         
//                     
//        HttpClient client = new HttpClient();
//        PostMethod post = new PostMethod(url);
//        post.setRequestBody(responeJsonStr);
//        post.getParams().setContentCharset("utf-8");
//        //发送http请求
//        String respStr = "";
//        try {
//            client.executeMethod(post);
//            respStr = post.getResponseBodyAsString();
//        } catch (HttpException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(responeJsonStr);
//        System.out.println(respStr);
//    }
	
//	public static void main(String[] args){
//		String s = "#reg#您的姓名#性别#出生年月日(YYYYMMDD)#身高(如183)#学历#职业#手机号码#所在地区#";
////		String[] split = s.split("#");
//		String[] split = StringUtils.split(s, "#");
//		int i=1;
//		for (String string : split) {
//			System.out.println(i + "=" + string);
//			i++;
//		}
//		System.out.println(split.length);
//		System.out.println(split[4]);
//	}
}
