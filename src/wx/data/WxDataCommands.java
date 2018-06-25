package wx.data;

import java.util.ArrayList;
import java.util.List;

import wx.cache.WxParamCache;

public class WxDataCommands {
	
	public static List<WxDataHandler> handlers = new ArrayList<WxDataHandler>();
	
	private static WxDataCommands instance;
	
	private WxDataCommands(){
		handlers.add(new TokenHandler());
		handlers.add(new JsApiTickedHandler());
		handlers.add(new DataCenterTokenHandler());
	}
	
	
	public static WxDataCommands getInstance(){
		if(instance == null){
			instance = new WxDataCommands();
		}
		return instance;
	}
	
	
	public List<WxDataHandler> getAllCommands(){
		return handlers;
	}


	public WxDataHandler getDataHandler(String key) {
		if(WxParamCache.JS_TICKET.equals(key)){
			return new JsApiTickedHandler();
		}else if(WxParamCache.TOKEN.equals(key)){
			return new TokenHandler();
		}else if(WxParamCache.DataCenter_TOKEN.equals(key)){
			return new DataCenterTokenHandler();
		}
		return null;
	}
	
}
