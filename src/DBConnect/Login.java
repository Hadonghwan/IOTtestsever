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

public class Login { // �α��� ȸ������ ����
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
	private Random rn; // ��й�ȣ�� ��߱� ������ �������� ���� �����Լ�;


	public String adminLogin(String id) { // ������ �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // id�� admin id �ֱ�
			rs = pstmt.executeQuery();
			if (rs.next()) { // getString(�ش� ���̺� �÷��� �ʵ尪).equals(�޼ҵ� ����)
				returns = rs.getString("password") + " adminSuccess"; // ������ �α��� ����
			} else {
				returns = "adminFailed"; // ������ �α��� ����
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

	public String loginDB(String id, String pwd) { // �α���
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from user where id=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			if (rs.next()) { // �Է��� id, password�� ��ġ�ϴ� ȸ���� ������ ��
				String name = rs.getString("name");
				returns = name+" "+"loginSuccess";
			} else { // �α��� ����
				returns = "loginFailed"; // user ���̺� ������ ����� ��
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

	public String createAccount(String name, String id, String pwd, String mail) { // ȸ������
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from add_user where id=? and name=?"; // ��������
			pstmt = conn.prepareStatement(sql); // db�� �����ϱ� ���� ����(sql����)�� ����
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			rs = pstmt.executeQuery();
			if (rs.next()) { // ȸ������ ������� ��
				sql2 = "select * from user where id=? and password=? and name=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setString(2, pwd);
				pstmt2.setString(3, name);
				rs2 = pstmt2.executeQuery();
				if (rs2.next()) { // �̹� ȸ�������� �Ǿ� ���� ��
					returns = "acountAleadyExist";
				} else { // ȸ�������� ���� ��
					sql3 = "insert into user (id, password, name, mail) values (?, ?, ?, ?)";
					pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setString(1, id);
					pstmt3.setString(2, pwd);
					pstmt3.setString(3, name);
					pstmt3.setString(4, mail);
					pstmt3.executeUpdate();
					returns = "accountCreated";
				}
			} else { // ȸ������ ����ڰ� �ƴҶ�
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