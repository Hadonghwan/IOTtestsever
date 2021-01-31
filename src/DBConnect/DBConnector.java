package DBConnect;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector { // DB접근제어
   // DB접속
   private String dbURL = "jdbc:mariadb://localhost:3307/securitylab_andb?serverTimezone=UTC"; // db주소
   private String dbID = "root"; // db아이디
   private String dbPassword = "security915!"; // db비밀번호
   

   public DBConnector() {}

   public Connection getConn() {
      try {
         Class.forName("org.mariadb.jdbc.Driver");
         return DriverManager.getConnection(dbURL, dbID, dbPassword);
      } catch (SQLException e) {
         System.err.println("DBconnectorIoT Connection error");
         return null;
      } catch (ClassNotFoundException e) {
         // TODO Auto-generated catch block
         System.err.println("DBConnector  ClassNotFoundException error");
         return null;
      }
   }
   

	public String getURL() {
		return dbURL;
	}
	
	public String getID() {
		return dbID;
	}
	
	public String getPW() {
		return dbPassword;
	}		


}
