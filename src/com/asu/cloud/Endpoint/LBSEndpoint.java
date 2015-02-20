package com.asu.cloud.Endpoint;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.asu.cloud.jaxb.FileJAXB;
import com.asu.cloud.jaxb.UserJAXB;
import com.asu.cloud.jaxb.ValidateUserJAXB;

@WebService(name="LBSWebService",targetNamespace="http://www.lbswebservice.com")
public interface LBSEndpoint {	
    
	public abstract boolean validateUser(@WebParam(name="ValidateUser")ValidateUserJAXB user);
	
	public abstract String registerUser(@WebParam(name="RegisterUser")UserJAXB user);

	public abstract String uploadFile(@WebParam(name="UploadFile")FileJAXB file);
	
	@WebResult(name="File")
	public abstract List<FileJAXB> viewFiles(@WebParam(name="MobileUID")String strMobId);
	
	public abstract List<String> downloadFile(@WebParam(name="DownloadFile")FileJAXB file);
	

}