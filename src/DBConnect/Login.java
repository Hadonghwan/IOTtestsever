package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import com.mysql.cj.Session;
import com.mysql.cj.protocol.Message;
import com.sun.xml.internal.fastinfoset.sax.Properties;
import sun.rmi.transport.Transport;

public class Login { // 로그인 회원가입 관리
	private static Login instance = new Login();

	public static Login getInstance() {
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
	private Random rn; // 비밀번호를 재발급 받을때 랜덤값을 만들 랜덤함수;


	public String adminLogin(String id) { // 관리자 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // id에 admin id 넣기
			rs = pstmt.executeQuery();
			if (rs.next()) { // getString(해당 테이블 컬럼의 필드값).equals(메소드 변수)
				returns = rs.getString("password") + " adminSuccess"; // 관리자 로그인 성공
			} else {
				returns = "adminFailed"; // 관리자 로그인 실패
			}
		} catch (Exception e) {
			returns = "error";
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String loginDB(String id, String pwd) { // 로그인
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from user where id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 입력한 id, password가 일치하는 회원이 존재할 때
				String name = rs.getString("name");
				returns = name+" "+"loginSuccess";
			} else { // 로그인 실패
				returns = "loginFailed"; // user 테이블에 정보가 비었을 때
			}
		} catch (Exception e) {
			returns = "error";
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

	public String createAccount(String name, String id, String pwd, String mail) { // 회원가입
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from add_user where id=? and name=?"; // 쿼리구문
			pstmt = conn.prepareStatement(sql); // db에 접근하기 위한 쿼리(sql변수)를 저장
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // 회원가입 대상자일 때
				sql2 = "select * from user where id=? and password=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				rs2 = pstmt2.executeQuery();
				if (rs2.next()) { // 이미 회원가입이 되어 있을 때
					returns = "acountAleadyExist";
				} else { // 회원정보가 없을 때
					sql3 = "insert into user (id, password, name, mail) values (?, ?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.setString(4, mail);
					pstmt3.executeUpdate();
					returns = "accountCreated";
				}
			} else { // 회원가입 대상자가 아닐때
				returns = "notMember";
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {
				}
			if (pstmt2 != null)
				try {
					pstmt2.close();
				} catch (SQLException ex) {
				}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}

}