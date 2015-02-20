package com.asu.cloud.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.asu.cloud.DAO.LBSDaoImpl;
import com.asu.cloud.jaxb.FileJAXB;
import com.asu.cloud.utils.Base64;
import com.asu.cloud.utils.FileEncryptDecrypt;
import com.asu.cloud.utils.FileFTP;
import com.asu.cloud.vto.FileVTO;
import com.asu.cloud.vto.UserVTO;

public class LBSServiceImpl {
	
	private LBSDaoImpl dao = new LBSDaoImpl();
	
	private Properties properties = new Properties();
	
	public boolean validateUser(String username,String password, String mobileUID, String mobIP){		
		return dao.validateUser(username, password,mobileUID,mobIP);
	}
	
	public String registerUser(UserVTO user) {
		String strsuccessIndicator="FAIL";
		int noOfRows=dao.registerUser(user.getUserName(),user.getPassword(),user.getMobileUid(),user.getCurrentStatus(),user.getLattitude(),user.getLongitude(),user.getStrMobileIP());
		if(noOfRows!=0){
			strsuccessIndicator="SUCCESS";
		}else
			strsuccessIndicator="FAIL";
		return strsuccessIndicator;
	}

	public String uploadFile(FileVTO file) {
		String strsuccessIndicator="FAIL";
		//get the vm for the corresponding mobile device
		String vmIpAddr=dao.getVMDetails(file.getMobileUID());
		//upload a file and receive acknowledgement
			if(vmIpAddr!=null){		        
		        try{
		        	//FileEncryptDecrypt filedecrypt = new FileEncryptDecrypt(file.getMobileUID());
		        	//byte[] dataByteArray =filedecrypt.decrypt(file.getStrFileContent());
		        	byte[] dataByteArray = Base64.decode(file.getStrFileContent());
					boolean uploadSuccess=FileFTP.ftpUpload(file.getFileName(), vmIpAddr, dataByteArray);
					if(uploadSuccess){
						int noOfRows=dao.uploadFile(file.getMobileUID(), file.getFileName(), file.getFileSize(), file.getType(), file.getSource(),file.getSourceType(),file.getLattitude(),file.getLongitude(),file.getStrFileType());
						if(noOfRows!=0){
							strsuccessIndicator="SUCCESS";
						}else
							strsuccessIndicator="FAIL";		
			        }
		        }catch(Exception e){
		        	strsuccessIndicator="FAIL";
		        }
		}
		return strsuccessIndicator;
	}

	public List<FileJAXB> viewFiles(String mobileUid) {
		List<FileJAXB> lstFile = new ArrayList<FileJAXB>();
		//upload a file and receive acknowledgement
		lstFile=dao.viewFiles(mobileUid);
		return lstFile;
	}

	public List<String> downloadFile(FileVTO file) {
		List<String> lstContent= new ArrayList<String>();
		String strResponse=null;
		String strParams[]=null;
		String status = null;
		float heuristicParameter=0,intCalculatedDistance=0;
		FileEncryptDecrypt filedecrypt=null;
		//upload a file and receive acknowledgement
		strResponse=dao.downloadFile(file.getMobileUID(),file.getStrFileId());
		if(strResponse!=null && strResponse.contains("#")){
			strParams=strResponse.split("#");
		}
		if(strParams[5].equals("VM2VM")){
			//No mobile is willing to share files
			status="VM2VM";
		}else{
			intCalculatedDistance=Float.parseFloat(strParams[0]);
			loadProperties();
			heuristicParameter=Float.parseFloat(properties.get("heuristic").toString());
			if(intCalculatedDistance>heuristicParameter){
				status="VM2VM";
			}else{
				status="MOB2MOB";
			}
		}
		/*int noOfRows=*/dao.uploadFile(file.getMobileUID(), file.getFileName(), file.getFileSize(), file.getType(), file.getSource(),file.getSourceType(),file.getLattitude(),file.getLongitude(),file.getStrFileType());
		if(status.equals("MOB2MOB")){
			lstContent.add("REQUEST");
			lstContent.add(strParams[2]);			
		}else if(status.equals("VM2VM")){
				byte[] fileContent=FileFTP.downloadFile(strParams[1],strParams[2]);	
				boolean uploadSuccess=FileFTP.ftpUpload(strParams[1], strParams[3], fileContent);			
				if(uploadSuccess){
					try {
						filedecrypt = new FileEncryptDecrypt(file.getMobileUID());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					lstContent.add("ACCEPT");
					lstContent.add(Base64.encode(fileContent));
				}					
		}
		return lstContent;
	}
	
	private void loadProperties(){
		InputStream input = FileFTP.class.getClassLoader().getResourceAsStream("/resources/CommonResource.properties");
	    try {
	    	properties.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
