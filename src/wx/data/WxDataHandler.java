package wx.data;

/**
 * 
 * 微信数据获取接口
 * 
 * @author jacky
 * @version 1.0
 * @since APEX OSSWorks 5.5
 */
public interface WxDataHandler {
	/**
 	 * 根据分销商代码，获得最新的参数值，并更新缓存 
	 * @param orgCode
	 * @return
	 */
	public String getValueAndUpdate2Cache(String orgCode);
}
