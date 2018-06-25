package wx.data;

import org.apache.log4j.Logger;

import model.bo.auth.Org;
import net.sf.json.JSONObject;
import service.intf.AdminService;
import util.HttpUtils;
import wx.cache.WxParamCache;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;

public class TokenHandler implements WxDataHandler {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Override
	public String getValueAndUpdate2Cache(String orgCode) {
		AdminService adminService =  (AdminService) ServerBeanFactory.getBean("adminService");
		Org orgBycode = adminService.getOrgBycode(orgCode);
		logger.info("orgCode = " + orgCode + "| orgBycode.getAppId=" + orgBycode.getAppid());
//		String APPID = Version.getInstance().getNewProperty("APPID");
//		String APPSECRET = Version.getInstance().getNewProperty("APPSECRET");
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+orgBycode.getAppid() + "&secret="+ orgBycode.getAppsecret();
		logger.info("tokenurl---------" + url);
		String doGet = HttpUtils.doGet(url, null);
		if("".equals(doGet)){
			return null;
		}
		JSONObject fromObject = JSONObject.fromObject(doGet);
		if(fromObject.get("access_token") != null){
			String token = (String) fromObject.get("access_token");
			logger.info("final token get----------------" + token);
			WxParamCache.getInstance().updateParam(orgCode, WxParamCache.TOKEN, token, System.currentTimeMillis());
			return token;
		}else{
			return null;
		}
	}
}
