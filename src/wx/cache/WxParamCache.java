package wx.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import model.vo.WxParamObj;

import org.apache.log4j.Logger;

import wx.data.WxDataCommands;
import wx.data.WxDataHandler;

import com.base.log.LogUtil;

/**
 * 
 * 由于微信接口调用频率的限制，部分参数必须有缓存机制，而不是每次都重新获取消耗调用频次。<br>
 * 微信部分参数都有有效期，在有效期内，可以保持在缓存里，快到有效期时，再去重新获取并更新到缓存里。<br>
 * ----------------------------------------------------------------------------------<br>
 * 服务上线之后无法获取jsapi_ticket，自己测试时没问题。（因为access_token和jsapi_ticket必须要在自己的服务器缓存，否则上线后会触发频率限制。
 * 请确保一定对token和ticket做缓存以减少2次服务器请求，不仅可以避免触发频率限制，还加快你们自己的服务速度。
 * 目前为了方便测试提供了1w的获取量，超过阀值后，服务将不再可用，请确保在服务上线前一定全局缓存access_token和jsapi_ticket，两者有效期均为7200秒， 否则一旦上线触发频率限制，服务将不再可用）
 * 
 * @author jacky
 * @version 1.0
 */
public class WxParamCache implements Serializable {

	private static Logger logger = LogUtil.getLogger(LogUtil.SERVER);

	private static final long serialVersionUID = 1896175097809200576L;

	//----------------------------- 参数 -----------------------------------
	public static final String TOKEN = "access_token";

	public static final String JS_TICKET = "jsapi_ticket";
	
	public static final String DataCenter_TOKEN = "datacenter_token";

	// 参数失效时间
	public static final int timeout = 7200;// 秒

	private static WxParamCache instance;

	/** 按分销商保存的总的缓存， key - 分销商的code */
	private Map<String, Map<String, WxParamObj>> allCache = new ConcurrentHashMap<String, Map<String,WxParamObj>>();
	
	/**
	 * key - 参数名称，比如access_token, jsapi_ticket value - WxParamObj
	 */
	private Map<String, WxParamObj> paramCache = new ConcurrentHashMap<String, WxParamObj>();

	private WxParamCache() {
	}

	public static WxParamCache getInstance() {
		if (instance == null) {
			instance = new WxParamCache();
		}
		return instance;
	}

	/**
	 * 根据分销商代码，更新各公众号缓存
	 * @param key
	 * @param newTime
	 */
	public void updateParam(String orgCode, String key, String value, Long newTime) {
		//先从大缓存里取出某个共众号的缓存map
		Map<String, WxParamObj> paramCache = allCache.get(orgCode);
		logger.info("paramCache===" + paramCache);
		if(paramCache != null){
			WxParamObj WxParamObj = paramCache.get(key);
			if (WxParamObj != null) {
				WxParamObj.setParamvalue(value);
				WxParamObj.setTimestamp(System.currentTimeMillis());
				logger.info("参数" + key + "更新完成...");
			} else {
				logger.info("111无此参数["+ key +"]，新增, value=" + value);
				WxParamObj = new WxParamObj(value, newTime);
				paramCache.put(key, WxParamObj);
			}
		}else{
			//缓存里不存在， 先增加公众号缓存 
			paramCache = new ConcurrentHashMap<String, WxParamObj>();
			allCache.put(orgCode, paramCache);
			//再增加参数缓存
			logger.info("222无此参数["+ key +"]，新增, value=" + value);
			WxParamObj WxParamObj = new WxParamObj(value, newTime);
			paramCache.put(key, WxParamObj);
		}
	}

	/**
	 * 根据分销商code和 key获得参数值
	 * 
	 * @param key
	 * @return
	 */
	public String getWxJsParam(String orgCode, String key) {
		try{
			logger.info("param-:" + orgCode + "|" + key);
			Map<String, WxParamObj> map = this.allCache.get(orgCode);
			if(map == null){//还没有该分销商的缓存
				Map<String, WxParamObj> tm = new ConcurrentHashMap<String, WxParamObj>();
				allCache.put(orgCode, tm);
				map = tm;
			}
			WxParamObj WxParamObj = map.get(key);
			if (WxParamObj != null) {
				return WxParamObj.getParamvalue();
			} else {
				logger.info(key + "in getWxJsParam--重新获取token--" + orgCode);
				//重新获取
				WxDataHandler handler = WxDataCommands.getInstance().getDataHandler(key);
				return handler.getValueAndUpdate2Cache(orgCode);
			}
		}catch(Exception e){
			logger.info("=====-error in getWxJsParam=====");
			e.printStackTrace();
			return null;
		}
	}

	public List<WxParamObj> getAllParams() {
		try{
			List<WxParamObj> list = new ArrayList<WxParamObj>();
			Set<String> keySet = paramCache.keySet();
			for (String string : keySet) {
				list.add(paramCache.get(string));
			}
			return list;
		}catch(Exception e){
			logger.info("=====-error in getAllParams=====");
			e.printStackTrace();
			return null;
		}
	}

//	public Map<String, WxParamObj> getWxParamMap() {
//		return paramCache;
//	}
	
	public Map<String, Map<String, WxParamObj>>  getFullCache() {
		return allCache;
	}
	
	
	
}
