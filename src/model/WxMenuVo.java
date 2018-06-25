package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 微信自定义菜单：一级菜单
 * @author jacky
 *
 */
public class WxMenuVo implements Serializable{

	private static final long serialVersionUID = -1770841085541011237L;
	
	private String name;
	
	private List<Button> sub_button = new ArrayList<Button>();

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public List<Button> getSub_button() {
		return sub_button;
	}

	
	public void setSub_button(List<Button> sub_button) {
		this.sub_button = sub_button;
	}
	

}
