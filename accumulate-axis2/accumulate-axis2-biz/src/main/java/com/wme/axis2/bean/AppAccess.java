package com.wme.axis2.bean;

/**
 * Created by Wangmingen on 2015/10/30.
 * 接口访问权限检验对象
 */
public class AppAccess implements java.io.Serializable {

	private String userName;
	
	private String password;
	
	private String sign;
	
	/** 时间戳 格式yyyyMMddHHmmss */
	private String timestamp;
	
	public AppAccess() {
	
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
