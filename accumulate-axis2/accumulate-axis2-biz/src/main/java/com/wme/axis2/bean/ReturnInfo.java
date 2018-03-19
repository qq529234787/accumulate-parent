package com.wme.axis2.bean;

import java.io.Serializable;

public class ReturnInfo implements Serializable {

	private Integer code;
	
	private String msg;
	
	public ReturnInfo() {

	}

	public ReturnInfo(Integer code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
