package com.asu.cloud.Model;

public class User {
	
	private String userName;
	private String password;
	private String mobileUid;
	private String currentStatus;
	private String userId;
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

}
