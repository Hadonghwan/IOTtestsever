package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Imgup { // 게시판 관리
	private static Imgup instance = new Imgup();

	public static Imgup getInstance() {
		return instance;
	}
	
	
	private DBConnector dbc = new DBConnector();
	private Connection conn;
	private String sql = "";
	private String sql2 = "";
	private String sql3 = "";
	private PreparedStatement pstmt;
	private PreparedStatement pstmt2;
	private PreparedStatement pstmt3;
	private ResultSet rs;
	private ResultSet rs2;
	private String returns;

	public String filePath_Download(String fullpath) { 
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "insert into imagefile values (?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fullpath);
			rs = pstmt.executeQuery();
			
			return returns = "FileDownloaded";
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return returns;		
	}
	
	
}
