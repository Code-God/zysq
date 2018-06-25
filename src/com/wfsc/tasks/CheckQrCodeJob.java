package com.wfsc.tasks;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import util.HttpUtils;
import util.TwoDimensionCode;
import util.UploadUtil;
import util.WxPayUtil;
import wx.data.WxDataCommands;
import wx.data.WxDataHandler;
import actions.integ.weixin.WeiXinUtil;

import com.base.ServerBeanFactory;
import com.base.job.SimpleTask;
import com.base.log.LogUtil;
import com.wfsc.common.bo.user.User;
import com.wfsc.services.account.IUserService;

/**
 * 定时检测用户二维码是否在有效期内
 * @author Administrator
 *
 */
public class CheckQrCodeJob extends SimpleTask{
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	
	@Override
	public Object execute(Object obj, Date nextFireTime) {
		logger.info(".............................检测更新临时二维码.............................下次执行时间：" + nextFireTime);
		this.execute();
		return null;
	}
	
	public void execute(){
		String access_token=WeiXinUtil.getAccessToken("000001");
		System.out.println(access_token);
		//检查用户表中的二维码是否过期
		IUserService userService=(IUserService)ServerBeanFactory.getBean("userService");
		List<User> userlist=userService.getAllUsers();
		if(userlist!=null&&userlist.size()>0){
			User user=null;
			for(int i=0;i<userlist.size();i++){
				user=userlist.get(i);
				Long userid=user.getId();
				
				String qr_code_path=user.getQr_code_url();
				Date date=user.getQr_code_time();
				Date nowdate=new Date();
				boolean effective=false;
				//计算时间差，有效时间为30天
				Long second=0l;
				if(date!=null){
					//时间差，秒
					second=(nowdate.getTime()-date.getTime())/1000;
					//30天减去10分钟
					if(second>=(30*24*60*60-10*60)){
						effective=false;
					}else{
						effective=true;
					}
				}else{
					effective=false;
				}
				
				//判断用户表中是否有二维码，并且是在有效时间内的,
				//如果没有重新获取一个有效时间二维码
				if(qr_code_path==null||qr_code_path.equals("")||!effective){
					//获取有效的token
//					String tokenUrl1="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
//					String tokenpost = HttpUtils.doPost(tokenUrl1, null, null);
//					String access_token = WxPayUtil.getValueFromJson(tokenpost, "access_token");
					
					//通过token获取临时带参数二维码url,ticket
					String ticketurl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
					JSONObject param=new JSONObject();
					param.put("expire_seconds", 2592000);//最大三十天
					param.put("action_name", "QR_SCENE");//临时二维码
					JSONObject jsonscene=new JSONObject();
					jsonscene.put("scene_id", userid);
					JSONObject action_info=new JSONObject();
					action_info.put("scene", jsonscene);
					param.put("action_info", action_info);					
					String ticketpost=HttpUtils.doPost(ticketurl, null, param.toString());
					if(ticketpost.contains("errcode")||!ticketpost.contains("url")){
						//超过更新期限了，需要重新获取一遍，并且更新到缓存
						WxDataHandler handler = WxDataCommands.getInstance().getDataHandler("access_token");
						handler.getValueAndUpdate2Cache("000001");
						access_token=WeiXinUtil.getAccessToken("000001");
						ticketurl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+access_token;
						ticketpost=HttpUtils.doPost(ticketurl, null, param.toString());
					}
					//二维码图片解析后的地址
					String url=WxPayUtil.getValueFromJson(ticketpost, "url");
					//生成二维码保存到本地
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
					TwoDimensionCode qr_code=new TwoDimensionCode();
					String content=url;
					System.out.println(content);
					//qr_code.encoderQRCode(content,picSavePath,"png" ,20);
					try{
						//qr_code.encoderQRCode(content,picSavePath,"png" ,20);
					      qr_code.createQRCode(content,picSavePath,"c:\\zhaoyao.jpg");
					}catch(Exception e){
						e.printStackTrace();
						System.out.println(e.getMessage()+"ttttttttttttt");
					}
					System.out.println(picSavePath+"7777777777777777777777777777");
					//保存到用户表中
					user.setQr_code_url(picSavePath.replace(UploadUtil.getSaveUrl(), ""));
					user.setQr_code_time(nowdate);
					userService.saveOrUpdateEntity(user);
				}				
			}
		}else{
			logger.info(".............................没有要更新的二维码............................." );
		}
	}

}
