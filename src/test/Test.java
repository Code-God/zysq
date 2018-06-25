package test;

import java.io.File;
import java.net.URLEncoder;
import java.util.UUID;

import net.sf.json.JSONObject;
import util.HttpUtils;
import util.TwoDimensionCode;
import util.UploadUtil;



public class Test {
	public static void main(String[] args) throws Exception{
//		int days = 1;
//		double currentPrice = 9.86;
//		double destPrice = 22.0; 
//		while(true){
//			double d = currentPrice* Math.pow((1+0.1), days);
//			if(d >= destPrice){
//				System.out.println("经过" + days + "天的涨停达到目标价格！！");
//				break;
//			}
//			System.out.println(currentPrice + "经过" + days + "天的连续涨停，价格为：" + String.format("%.3f", d));
//			days ++;
//		}
		
//		
//		JSONObject jo = new JSONObject();
//		JSONObject datas = new JSONObject();
//		JSONObject subJo1 = new JSONObject();
//		subJo1.put("value", "test");
//		subJo1.put("color", "#173177");
//		JSONObject subJo2 = new JSONObject();
//		subJo2.put("value", "test");
//		subJo2.put("color", "#173177");
//		JSONObject subJo3 = new JSONObject();
//		subJo3.put("value", "test");
//		subJo3.put("color", "#173177");
//		JSONObject subJo4 = new JSONObject();
//		subJo4.put("value", "test");
//		subJo4.put("color", "#173177");
//		JSONObject subJo5 = new JSONObject();
//		subJo5.put("value", "test");
//		subJo5.put("color", "#173177");
//		
//		datas.put("first", subJo1);
//		datas.put("keynote1", subJo2);
//		datas.put("keynote2", subJo3);
//		datas.put("keynote3", subJo4);
//		datas.put("remark", subJo5);
//		
//		jo.put("touser", "adfadFDFSsafdasdf123123");
//		jo.put("template_id", "sdfsdf123123132");
//		jo.put("url", "");//推送消息的链接
//		jo.put("data", datas);
//		
//		System.out.print(jo.toString());
		
		String appId = "wx43884964a4fec494";
		String secret = "eb5e17d37d71ba4e09af4506ce4ea33e";
		String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
//		String doPost = HttpUtils.doPost(tokenUrl, null, null);
//		System.out.println(doPost);
//		
		
		
		String access_token="Mc26iX5OWxn5r7GMbq9ERelSrww3ZLhOwUzeoP0cJRmWWSCVs71HF72j7fQfVycDy9-P9ZyK7GwVWGTVKjnoMeNR8PNwt3fZX5cWbe6WlS0A6LdjadaabeTxt4j-yxitEJWdAFAXCP";
		String url2="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
		JSONObject param=new JSONObject();
		param.put("expire_seconds", 2592000);//最大三十天
		param.put("action_name", "QR_SCENE");//临时二维码
		JSONObject jsonscene=new JSONObject();
		jsonscene.put("scene_id", "1340");
		JSONObject action_info=new JSONObject();
		action_info.put("scene", jsonscene);
		param.put("action_info", action_info);
//		System.out.println(param.toString());
		
//		String ticketpost=HttpUtils.doPost(url2, null, param.toString());
		
//		System.out.println(ticketpost);
		
		//{"ticket":"gQGF8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2RUX2RCT1RsaE56OUo1NEZ3aEZCAAIE38q\/VwMEAI0nAA==","expire_seconds":2592000,"url":"http:\/\/weixin.qq.com\/q\/dT_dBOTlhNz9J54FwhFB"}
	
	
//		String ticket = WxPayUtil.getValueFromJson(ticketpost, "ticket");
//		System.out.println(ticket);
		
//		String url=WxPayUtil.getValueFromJson(ticketpost, "url");
//		System.out.println(url);
//		gQFS8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2pUX0s1b3ZsN3R5WFVtYWExUkZCAAIEnNy/VwMEAI0nAA==
//				http://weixin.qq.com/q/jT_K5ovl7tyXUmaa1RFB
//		
//		String ticket="gQFS8ToAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2pUX0s1b3ZsN3R5WFVtYWExUkZCAAIEnNy/VwMEAI0nAA==";
//		System.out.println(URLEncoder.encode(ticket));
//		String qr_code_url="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ URLEncoder.encode(ticket, "utf-8");
//		
//		String qrCodePost=HttpUtils.doPost(qr_code_url, null, null);
//		System.out.println(qrCodePost);
		
		String ticketurl="http://weixin.qq.com/q/jT_K5ovl7tyXUmaa1RFB";
		String saveDir=UploadUtil.getSaveUrl()+UploadUtil.UPLOADSIMAGES+"\\QR_code";
		String picname=UUID.randomUUID().toString()+".png";
		String picSavePath = saveDir + File.separator + picname; //保存路径
		System.out.println(picSavePath);
		File fileDirect = new File(saveDir);
		if (!fileDirect.exists()) {
			// 该目录不存在，则创建目录
			fileDirect.mkdirs();
		}
		//二维码不存在，需要生成二维码
		TwoDimensionCode code=new TwoDimensionCode();
		String content=ticketurl;
		System.out.println(content);
		code.encoderQRCode(content,picSavePath,"png" ,20);
	}
	
}
