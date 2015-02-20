package com.asu.cloud.EndPointImpl;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import com.asu.cloud.Endpoint.LBSEndpoint;
import com.asu.cloud.Service.LBSServiceImpl;
import com.asu.cloud.jaxb.FileJAXB;
import com.asu.cloud.jaxb.UserJAXB;
import com.asu.cloud.jaxb.ValidateUserJAXB;
import com.asu.cloud.vto.FileVTO;
import com.asu.cloud.vto.UserVTO;

@WebService(endpointInterface="com.asu.cloud.Endpoint.LBSEndpoint",portName="LBSWebServicePort",serviceName="LBSEndpointService")
public class LBSEndpointImpl implements LBSEndpoint {
	
	private LBSServiceImpl service = new LBSServiceImpl();
	
	public List<FileJAXB> viewFiles(String mobileUid) {
		List<FileJAXB> lstFile = new ArrayList<FileJAXB>();
		if(mobileUid!=null )
			lstFile=service.viewFiles( mobileUid);
		return lstFile;
	}

	@Override
	public boolean validateUser(ValidateUserJAXB user) {
		boolean isValidUser=false;
		String username =user.getUserName();
		String password = user.getPassword();
		String mobileUID = user.getMobileUid();
		String mobIP=user.getMobileIP();
		if(username!=null && password!=null)
			isValidUser=service.validateUser(username, password,mobileUID,mobIP);
		return isValidUser;
	}
	
	public String registerUser(UserJAXB user) {
		String successIndicator = "failed";
		UserVTO userVTO =  new UserVTO();
		if(user!=null ){
		userVTO.setUserName(user.getUserName());
		userVTO.setPassword(user.getPassword());
		userVTO.setMobileUid(user.getMobileUid());
		if(user.getCurrentStatus()!=null)
			userVTO.setCurrentStatus(user.getCurrentStatus());
		else
			userVTO.setCurrentStatus("");
		userVTO.setLattitude(user.getLattitude());
		userVTO.setLongitude(user.getLongitude());
		userVTO.setStrMobileIP(user.getStrMobileIP());
		if(user.getUserName()!=null && user.getPassword()!=null && user.getMobileUid()!=null)
			successIndicator=service.registerUser(userVTO);
		}
		return successIndicator;
	}

	public String uploadFile(FileJAXB file) {
		String successIndicator = "failed";
		FileVTO fVTO = new FileVTO();
		if(file!=null){
			fVTO.setMobileUID(file.getMobileUid());
			fVTO.setFileName(file.getFileName());
			fVTO.setFileSize(file.getFileSize());
			fVTO.setType(file.getType());
			fVTO.setSource(file.getSource());
			fVTO.setSourceType(file.getSourceType());
			fVTO.setStrFileContent(file.getStrFileContent());
			fVTO.setLattitude(file.getLattitude());
			fVTO.setLongitude(file.getLongitude());
			fVTO.setStrFileType(file.getStrFileType());
				successIndicator=service.uploadFile(fVTO);
		}
		return successIndicator;
	}
	
	public List<String> downloadFile(FileJAXB file) {
		List<String> lstFileContent =  null;
		FileVTO fVTO = new FileVTO();
		if(file!=null){
			fVTO.setStrFileId(file.getStrFileId());
			fVTO.setMobileUID(file.getMobileUid());
			fVTO.setFileName(file.getFileName());
			fVTO.setFileSize(file.getFileSize());
			fVTO.setType(file.getType());
			fVTO.setSource(file.getSource());
			fVTO.setSourceType(file.getSourceType());
			fVTO.setStrFileContent(file.getStrFileContent());
			fVTO.setLattitude(file.getLattitude());
			fVTO.setLongitude(file.getLongitude());
			fVTO.setStrFileType(file.getStrFileType());
			lstFileContent=service.downloadFile(fVTO);
		}			
		return lstFileContent;
	}	
}
