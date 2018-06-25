package model.bo.wxmall;

import java.io.Serializable;

/**
 * 
 * 微信菜单，只支持二级
 * 
 * @author jacky
 * @version 1.0
 * @since Resint 1.0
 * @hibernate.class dynamic-insert="true" dynamic-update="true" table="wxMenu" lazy="false"
 */
public class WxMenu implements Serializable {

	private static final long serialVersionUID = -2217930705151905592L;

	private Long id;

	private WxMenu parent;

	/** 总销商ID， 不同的分销商菜单是不同的 */
	private Long orgId;

	private String name;

	/**
	 * 1、click：点击推事件 用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event 的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互；
	 * 2、view：跳转URL 用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。 
	 * 3、scancode_push：扫码推事件
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。 
	 * 4、scancode_waitmsg：扫码推事件且弹出“消息接收中”提示框
	 * 用户点击按钮后，微信客户端将调起扫一扫工具，完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。 
	 * 5、pic_sysphoto：弹出系统拍照发图
	 * 用户点击按钮后，微信客户端将调起系统相机，完成拍照操作后，会将拍摄的相片发送给开发者，并推送事件给开发者，同时收起系统相机，随后可能会收到开发者下发的消息。 
	 * 6、pic_photo_or_album：弹出拍照或者相册发图
	 * 用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。用户选择后即走其他两种流程。 
	 * 7、pic_weixin：弹出微信相册发图器
	 * 用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。 
	 * 8、location_select：弹出地理位置选择器
	 * 用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息。 
	 * 9、media_id：下发消息（除文本消息）
	 * 用户点击media_id类型按钮后，微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
	 * 10、view_limited：跳转图文消息URL
	 * 用户点击view_limited类型按钮后，微信客户端将打开开发者在按钮中填写的永久素材id对应的图文消息URL，永久素材类型只支持图文消息。请注意：永久素材id必须是在“素材管理/新增永久素材”接口上传后获得的合法id。
	 */
	private String type;
	
	public final static String type_click = "click";
	public final static String type_view = "view";
	public final static String type_scancode_push = "scancode_push";
	public final static String type_pic_sysphoto = "pic_sysphoto";
	public final static String type_pic_photo_or_album = "pic_photo_or_album";
	public final static String type_pic_weixin = "pic_weixin";
	public final static String type_location_select = "location_select";
	public final static String type_media_id = "media_id";
	public final static String type_view_limited = "view_limited";

	private String key;

	private String url;

	/**
	 * hibernate中的主键
	 * 
	 * @hibernate.id column="id" generator-class="native" type="long" unsaved-value="0"
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @hibernate.many-to-one class="model.bo.wxmall.WxMenu" column="pid" not-null="false"
	 */
	public WxMenu getParent() {
		return parent;
	}

	public void setParent(WxMenu parent) {
		this.parent = parent;
	}

	/**
	 * @hibernate.property type="long"
	 * @return
	 */
	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @hibernate.property type="string" column="mkey"
	 * @return
	 */
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @hibernate.property type="string"
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
