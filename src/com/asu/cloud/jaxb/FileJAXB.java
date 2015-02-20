package com.asu.cloud.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class FileJAXB {
	private String strFileId;
	private String mobileUid;
	private String fileName;
	private float fileSize;
	private String syncStatus;
	private String type;
	private int source;
	private String strFileContent;
	private String sourceType;
	private double lattitude;
	private double longitude;
	private String strFileType;
	@XmlElement(name="FileType",required=true)
	public String getStrFileType() {
		return strFileType;
	}
	public void setStrFileType(String strFileType) {
		this.strFileType = strFileType;
	}
	@XmlElement(name="FileName",required=true)
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
	@XmlElement(required=true)
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

	@XmlElement(name="MobileUID",required=true)
	public String getMobileUid() {
		return mobileUid;
	}
	public void setMobileUid(String mobileUid) {
		this.mobileUid = mobileUid;
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
	@XmlElement(name="FileContent",required=true)
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
