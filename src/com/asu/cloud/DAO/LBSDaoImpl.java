package com.asu.cloud.DAO;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.asu.cloud.jaxb.FileJAXB;
import com.asu.cloud.utils.FileFTP;

public class LBSDaoImpl {
	
	public LBSDaoImpl(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection getConnection(){
		Connection conn = null;
		try {
			InputStream input = FileFTP.class.getClassLoader().getResourceAsStream("/resources/DBResource.properties");
		    Properties prop = new Properties();
		    try {
				prop.load(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String DBName=prop.getProperty("DBName");
		    String DBHost = prop.getProperty("DBHost");
		    String username = prop.getProperty("DBUser");
		    String pwd = prop.getProperty("password");
		    conn =
		    		DriverManager.getConnection("jdbc:mysql://"+DBHost+":3306/"+DBName+"?",username,pwd);
		    		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return conn;
	}
	
	public boolean validateUser(String username,String password, String mobileUID, String mobIP){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement updtStmt = null;
		ResultSet rs = null;
		int noOfRows = 0;
		try {
		    conn =getConnection();
		    stmt = conn.prepareStatement("SELECT userid FROM usertable where UserName=? and Password=?");
		    stmt.setString(1, username);
		    stmt.setString(2, password);
		    rs = stmt.executeQuery();
		    if( rs.next()){
		    	int userid=rs.getInt("userid");
		    	updtStmt = conn.prepareStatement("update usertable set MobileIP=? where userid=?");
		    	updtStmt.setString(1, mobIP);
		    	updtStmt.setInt(2, userid);
		    	noOfRows=updtStmt.executeUpdate();
		    	if(noOfRows>=0)
		    		return true;
		    }else
		    	return false;
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}finally{
			try{
			if(!rs.isClosed())
				rs.close();
			if(!stmt.isClosed())
				stmt.close();
			if(!conn.isClosed())
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return false;
	}

	public int registerUser(String username, String password,
			String mobileUid, String currentStatus, double xcord, double ycord,String strMobileIP) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement cStmt = null;
		String result=null;
		int noOfRows=0;
		try {
		    conn =getConnection();
		    cStmt = conn.prepareCall("{call sp_createuser(?, ?, ?, ?, ?, ?, ?)}");
		    cStmt.setString(1, username);
		    cStmt.setString(2, password);
		    cStmt.setString(3,mobileUid);
		    cStmt.setDouble(4,xcord);
		    cStmt.setDouble(5,ycord);
		    cStmt.setString(6, strMobileIP);
		    cStmt.registerOutParameter(7, Types.VARCHAR);
		    noOfRows = cStmt.executeUpdate();
		    result=cStmt.getString(7);
		    if(result.equals("SUCCESS"))
		    	noOfRows=1;
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}finally{
			try{
			if(!cStmt.isClosed())
				cStmt.close();
			if(!conn.isClosed())
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return noOfRows;
	}

	public int uploadFile(String mobileUid, String fileName, float fileSize,
			String type, int source, String sourceType, double xcord, double ycord, String strFileType) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement cStmt = null;
		int noOfRows=0;
		String strMessage=null;
		try {
		    conn =getConnection();
		    cStmt = conn.prepareCall("{call sp_uploadfile(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
		    cStmt.setString(1, mobileUid);
		    cStmt.setString(2, fileName);
		    cStmt.setFloat(3,fileSize);
		    cStmt.setString(4,type);
		    cStmt.setInt(5,source);
		    cStmt.setString(6,sourceType);
		    cStmt.setDouble(7,xcord);
		    cStmt.setDouble(8,ycord);
		    cStmt.setString(9, strFileType);
		    cStmt.registerOutParameter(10, Types.VARCHAR);
		    cStmt.executeUpdate();
		    strMessage=cStmt.getString(10);
		    if(strMessage.equals("SUCCESS"))
		    	noOfRows=1;
		    else
		    	noOfRows=0;
		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}finally{
			try{
			if(!cStmt.isClosed())
				cStmt.close();
			if(!conn.isClosed())
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return noOfRows;
	}

	public List<FileJAXB> viewFiles(String mobileUid) {
		List<FileJAXB> lstFile = new ArrayList<FileJAXB>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
		    conn =getConnection();
		    stmt = conn.prepareStatement("select fileid,filename,filesize,FileMIMEType,type from filetable where fileid in (select distinct(f.FileID) from filetable f,userfilemapping u where f.fileid = u.fileid  and f.type='SHARED' and u.userid !=(select userid from usertable where mobileuid=?))");
		    stmt.setString(1, mobileUid);
		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	FileJAXB file = new FileJAXB();
		    	file.setStrFileId(rs.getString("fileid"));
		    	file.setFileName(rs.getString("filename"));
		    	file.setFileSize(rs.getFloat("filesize"));
		    	file.setType(rs.getString("type"));
		    	file.setStrFileType(rs.getString("FileMIMEType"));
		    	lstFile.add(file);
		    }
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}finally{
			try{
			if(!rs.isClosed())
				rs.close();
			if(!stmt.isClosed())
				stmt.close();
			if(!conn.isClosed())
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return lstFile;	
	}

	public String downloadFile(String mobileUid, String fileId) {
		// TODO Auto-generated method stub
		Connection conn = null;
		CallableStatement cStmt = null;
		String decision=null;
		float nearDistance=0;
		String strFileName=null;
		String strFilePresentVMIP = null;
		String strFileRequestedVMIP = null;
		String strFilePresentMoblieIP = null;
		String strStatusMessage = null;				
		try {
		    conn =getConnection();
		    cStmt = conn.prepareCall("{call sp_decisionAlg(?, ?, ?, ?, ?, ?, ?, ?)}");
		    cStmt.setString(1, fileId);
		    cStmt.setString(2, mobileUid);
		    cStmt.registerOutParameter(3, Types.FLOAT);
		    cStmt.registerOutParameter(4, Types.VARCHAR);
		    cStmt.registerOutParameter(5, Types.VARCHAR);
		    cStmt.registerOutParameter(6, Types.VARCHAR);
		    cStmt.registerOutParameter(7, Types.VARCHAR);
		    cStmt.registerOutParameter(8, Types.VARCHAR);
		    //register an out parameter to get the decision;
		    cStmt.executeUpdate();
		    nearDistance=cStmt.getFloat(3);
		    strFileName=cStmt.getString(4);
		    strFilePresentVMIP=cStmt.getString(5);
		    strFileRequestedVMIP=cStmt.getString(6);
		    strFilePresentMoblieIP=cStmt.getString(7);
		    strStatusMessage=cStmt.getString(8);
		    decision=nearDistance+"#"+strFileName+"#"+strFilePresentVMIP+"#"+strFileRequestedVMIP+"#"+strFilePresentMoblieIP+"#"+strStatusMessage;
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}finally{
			try{
			if(!cStmt.isClosed())
				cStmt.close();
			if(!conn.isClosed())
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return decision;
	}

	public String getVMDetails(String mobileUID) {		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String ipAddress=null;
		try {
		    conn =getConnection();
		    stmt = conn.prepareStatement("SELECT ipaddress FROM vmdetail where vmid=(select vmid from user_vm where userid=(select userid from usertable where mobileuid=?))");
		    stmt.setString(1, mobileUID);
		    rs = stmt.executeQuery();
		    while(rs.next()){
		    	ipAddress=rs.getString(1);
		    }
		    
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}finally{
			try{
			if(!rs.isClosed())
				rs.close();
			if(!stmt.isClosed())
				stmt.close();
			if(!conn.isClosed())
				conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return ipAddress;
	}
}
