package com.asu.cloud.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FileFTP {
	private static FTPClient ftpClient=new FTPClient();
	private static void connect(String strServer){
		int port = 21;
	    InputStream input = FileFTP.class.getClassLoader().getResourceAsStream("/resources/FTPResource.properties");
	    Properties prop = new Properties();
	    try {
			prop.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    String user = prop.getProperty("ftpusername");
	    String pass = prop.getProperty("ftppassword");
	    try {
	         ftpClient.connect(strServer, port);
	         System.out.println(ftpClient.getReplyCode());
	         ftpClient.login(user, pass);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	}
	public static boolean ftpUpload(String firstRemoteFile,String server,byte[] bytearray){
		boolean done = false;
		 try {
			 connect(server);
	         ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	         ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
	         ByteArrayInputStream bais = new ByteArrayInputStream(bytearray);
	         System.out.println("Start uploading first file");
	         done=ftpClient.storeFile(firstRemoteFile, bais);
	         bais.close();
	         if (done) {
	             System.out.println("The first file is uploaded successfully.");
	         }
	     } catch (IOException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	     }finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	     }		
	     return done;
	}
	public static byte[] downloadFile(String firstRemoteFile,String server){
		byte[] strFileContent=null;
		try {
			 connect(server);
	         ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	         ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
	         ByteArrayOutputStream baos = new ByteArrayOutputStream();
	         boolean done= ftpClient.retrieveFile(firstRemoteFile, baos);
	         strFileContent = baos.toByteArray();
	         if (done) {
	             System.out.println("The first file is downloaded successfully.");
	         }
	     } catch (IOException ex) {
	            System.out.println("Error: " + ex.getMessage());
	            ex.printStackTrace();
	     }finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	     }		
		return strFileContent;
	}
	
}
