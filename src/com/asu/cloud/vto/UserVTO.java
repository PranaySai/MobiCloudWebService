package com.asu.cloud.vto;

public class UserVTO {
	
	private String userName;
	private String password;
	private String mobileUid;
	private String currentStatus;
	private String userId;
	private double lattitude;
	private double longitude;
	private String strMobileIP;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getLattitude() {
		return lattitude;
	}
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getStrMobileIP() {
		return strMobileIP;
	}
	public void setStrMobileIP(String strMobileIP) {
		this.strMobileIP = strMobileIP;
	}

}
