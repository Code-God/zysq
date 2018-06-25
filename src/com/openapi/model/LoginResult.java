package com.openapi.model;

/**
 * 
 * @author jacky
 * @version 1.0
 */
public class LoginResult {

	/**
	 * 登录是否成功的标记，取值如下： true - 成功， false-登录失败
	 */
	private boolean success;

	/**
	 * 此处显示详细原因
	 */
	private String msg;

	/**
	 * 登录成功的access token，以后每次API调用，都必须传递该参数
	 */
	private String access_token;

	/**
	 * 用户真实姓名, 其实就是昵称
	 */
	private String realName;
	
	/** 当前登录用户的ID */
	private Long userId;
	

	public LoginResult() {
		super();
	}

	public LoginResult(boolean success, String msg, String access_token) {
		this.success = success;
		this.msg = msg;
		this.access_token = access_token;
	}

	public LoginResult(boolean success, String msg, String access_token, String realName) {
		this.success = success;
		this.msg = msg;
		this.access_token = access_token;
		this.realName = realName;
	}

	//true, "登录成功！", accessToken, user.getNickName(), user.getId()
	public LoginResult(boolean success, String msg, String access_token, String realName, Long id) {
		this.success = success;
		this.msg = msg;
		this.access_token = access_token;
		this.realName = realName;
		this.userId = id;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}

	/**
	 * @return the realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * @param access_token the access_token to set
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMsg(String error) {
		this.msg = error;
	}

	
	public Long getUserId() {
		return userId;
	}

	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
