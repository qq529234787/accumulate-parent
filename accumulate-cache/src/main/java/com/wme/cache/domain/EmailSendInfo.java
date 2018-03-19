package com.wme.cache.domain;


public class EmailSendInfo implements java.io.Serializable {

	private String userName;
	
	private String userAddress;
	
	private String subject;
	
	private String message;
	
	private boolean htmlFormat;

	private String encoding = "utf-8";

	
	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHtmlFormat() {
		return htmlFormat;
	}

	public void setHtmlFormat(boolean htmlFormat) {
		this.htmlFormat = htmlFormat;
	}

	public String getTo() {
		if (userName != null)
			return "\"" + userName + "\"<" + userAddress + ">";
		return userAddress;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}
