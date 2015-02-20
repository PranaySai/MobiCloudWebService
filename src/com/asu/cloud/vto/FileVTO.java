package com.asu.cloud.vto;



public class FileVTO {
	private String strFileId;
	private String mobileUID;
	private String fileName;
	private float fileSize;
	private String syncStatus;
	private String type;
	private int source;
	private String sourceType;
	private String strFileContent;
	private double lattitude;
	private double longitude;
	private String strFileType;
	public String getStrFileType() {
		return strFileType;
	}
	public void setStrFileType(String strFileType) {
		this.strFileType = strFileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public float getFileSize() {
		return fileSize;
	}
	public void setFileSize(float fileSize) {
		this.fileSize = fileSize;
	}
	public String getSyncStatus() {
		return syncStatus;
	}
	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
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
	public String getMobileUID() {
		return mobileUID;
	}
	public void setMobileUID(String mobileUID) {
		this.mobileUID = mobileUID;
	}
	public String getStrFileContent() {
		return strFileContent;
	}
	public void setStrFileContent(String strFileContent) {
		this.strFileContent = strFileContent;
	}
	public String getStrFileId() {
		return strFileId;
	}
	public void setStrFileId(String strFileId) {
		this.strFileId = strFileId;
	} 
	
}
