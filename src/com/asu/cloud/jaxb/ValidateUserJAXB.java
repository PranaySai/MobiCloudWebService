package com.asu.cloud.jaxb;

import javax.xml.bind.annotation.XmlElement;

public class ValidateUserJAXB {
	private String mobileUid;
	private String userName;
	private String password;
	private String mobileIP;

	@XmlElement(name="MobileUid",required=true)
	public String getMobileUid() {
		return mobileUid;
	}
	public void setMobileUid(String mobileUid) {
		this.mobileUid = mobileUid;
	}

	@XmlElement(name="UserName",required=true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@XmlElement(name="Password",required=true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@XmlElement(name="MobileIP")
	public String getMobileIP() {
		return mobileIP;
	}
	public void setMobileIP(String mobileIP) {
		this.mobileIP = mobileIP;
	}

}
