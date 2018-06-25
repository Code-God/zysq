package wx.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import model.bo.auth.Org;
import net.sf.json.JSONObject;
import service.intf.AdminService;
import util.HttpUtils;
import wx.cache.WxParamCache;
import actions.integ.weixin.WeiXinUtil;

import com.base.ServerBeanFactory;
import com.base.log.LogUtil;
import com.base.tools.Version;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class DataCenterTokenHandler implements WxDataHandler {
	Logger logger = LogUtil.getLogger(LogUtil.SERVER);
	@Override
	public String getValueAndUpdate2Cache(String orgCode) {

		logger.info("in DataCenterTokenHandler--" + orgCode);
		
		String dataCenterApiUrl = Version.getInstance().getNewProperty("datacenter_url").replaceFirst("api/", "")+"OAuth/Token";
		String userName = Version.getInstance().getNewProperty("datacenter_username");
		String password = Version.getInstance().getNewProperty("datacenter_password");
		
		String userpassword= userName+":"+password;
		String basicString = Base64.encode(userpassword.getBytes());
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//建立一个NameValuePair数组，用于存储欲传送的参数
		params.add(new BasicNameValuePair("grant_type","password"));
		params.add(new BasicNameValuePair("username",userName));
		params.add(new BasicNameValuePair("password",password));
		
		String responseStr = HttpUtils.doPostForToken(dataCenterApiUrl, params, basicString);
		
		JSONObject jsonObj = JSONObject.fromObject(responseStr);
		
		String accessToken = jsonObj.getString("access_token");
		
		//更新到缓存
		WxParamCache.getInstance().updateParam(orgCode, WxParamCache.DataCenter_TOKEN, accessToken, System.currentTimeMillis());
		
		return accessToken;
	}
}
