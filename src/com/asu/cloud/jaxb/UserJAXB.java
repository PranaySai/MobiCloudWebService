package com.asu.cloud.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserJAXB {
	private String mobileUid;
	private String userName;
	private String password;
	private String currentStatus;
	private double lattitude;
	private double longitude;
	private String strMobileIP;
	@XmlElement(required=true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@XmlElement(required=true)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@XmlElement(name="MobileUID",required=true)
	public String getMobileUid() {
		return mobileUid;
	}
	public void setMobileUid(String mobileUid) {
		this.mobileUid = mobileUid;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	@XmlElement(name="Lattitude",required=true)
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	@XmlElement(name="Longitude",required=true)
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	@XmlElement(name="MobileIP",required=true)
	public String getStrMobileIP() {
		return strMobileIP;
	}
	public void setStrMobileIP(String strMobileIP) {
		this.strMobileIP = strMobileIP;
	}

}
