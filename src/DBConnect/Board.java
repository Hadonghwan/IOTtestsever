package DBConnect;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Board { // �Խ��� ����
	private static Board instance = new Board();

	public static Board getInstance() {
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

	
	public String board_Add(String id, String title, String contents, String fullpath) { 
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from add_user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			
			if (rs.next()) { // id�� �����ؼ� �Խñ��� �� ��
				sql2 = "insert into board values (? ,? ,? ,? ,? ,? ,?)";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setInt(2, getNext());
				pstmt2.setInt(3, 1);
				pstmt2.setString(4, title);
				pstmt2.setString(5, contents);
				pstmt2.setString(6, getDate());
				pstmt2.setString(7, fullpath);
				pstmt2.executeUpdate();
				returns = "board_AddSuccess";
			}
			 else { // id�� �������� �ʾƼ� �Խñ��� �� �� ������
				
				returns = "board_valueNotExistToAdd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return returns;		
	}
	
	
	public int getNext(){ 
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			
			sql = "select num from board order by num desc";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1) + 1;
			}
			else return 1;
			
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return -1;
	}
	
	
	public String getDate(){ 
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			
			sql = "select now()";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return rs.getString(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return "error";
	}
	
	public String view_Update(int num){ //��ȸ�� ������Ʈ
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			
			sql = "select * from board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();		
				
			if(rs.next()) {
				sql = "update board set viewcnt = viewcnt+1 where num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();	
				returns = "viewUpdateSucess";
			}
			else returns = "viewUpdateFail";
			
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return returns;
	}

	
	public String board_Delete(String id, int num) { // �Խ��� �Խñ� ���� - board ���̺�
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from board where id=? and num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			if (rs.next()) { // board ���̺� id�� number�� �����ϴ��� Ȯ��
				sql2 = "delete from board where id=? and num=?"; // board ���̺� ���ڵ� ����
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setInt(2, num);
				pstmt2.executeUpdate(); // db�� ������ �Է�
				returns = "board_Deleted";
			} else { // board���̺� number�� ���� ���� ������
				returns = "board_valueNotExistToDelete";
			}

		} catch (Exception e) {
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
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {
				}
		}
		return returns;
	}
	
	
	public String board_Update(String id, int num, String title, String contents, String fullpath) { 
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from board where id=? and num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) { 
				sql2 = "update board set title=?, contents=?, image_path=? where id=? and num=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, title);
				pstmt2.setString(2, contents);
				pstmt2.setString(3, fullpath);
				pstmt2.setString(4, id);
				pstmt2.setInt(5, num);
		
				pstmt2.executeUpdate();
				returns = "board_UpdateSuccess";
			}
			 else { // id�� �������� �ʾƼ� �Խñ��� �� �� ������
				
				returns = "board_valueNotExistToUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return returns;		
	}
	
	public String noimage_Update(String id, int num, String title, String contents) { 
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from board where id=? and num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			
			if (rs.next()) { 
				sql2 = "update board set title=?, contents=? where id=? and num=?";
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, title);
				pstmt2.setString(2, contents);
				pstmt2.setString(3, id);
				pstmt2.setInt(4, num);
		
				pstmt2.executeUpdate();
				returns = "board_UpdateSuccess";
			}
			 else { // id�� �������� �ʾƼ� �Խñ��� �� �� ������
				
				returns = "board_valueNotExistToUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return returns;		
	}
	public String board_List() { // board ���̺� ����Ʈ
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // �����ͺ��̽� ������ ���� �α���
			sql = "select * from board order by num asc"; // board���̺�κ��� ��� ������ �ҷ�����
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // db�� ������ �Է�

			JSONArray jary = new JSONArray();
			boolean flag = true;
			while (rs.next()) {
				JSONObject jobj = new JSONObject();
				jobj.put("id", rs.getString("id"));
				jobj.put("num", rs.getInt("num"));
				jobj.put("title", rs.getString("title"));
				jobj.put("viewcnt", rs.getInt("viewcnt"));
				jary.add(jobj);
				flag = false;
				// https://offbyone.tistory.com/373 (�����ϱ� - json������)
				// https://dololak.tistory.com/625
			}
			returns = jary.toJSONString();
			if (flag) {
				returns = "board_NotExist";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException ex) {}
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException ex) {}
		}
		return returns;
	}
	
	public String board_View(int num) {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery(); // db�� ������ �Է�
		
			JSONArray jary = new JSONArray();
			boolean flag = true;
			while (rs.next()) {
				JSONObject jobj = new JSONObject();
				jobj.put("id", rs.getString("id"));
				jobj.put("num", rs.getInt("num"));
				jobj.put("viewcnt", rs.getInt("viewcnt"));
				jobj.put("title", rs.getString("title"));
				jobj.put("contents", rs.getString("contents"));
				jobj.put("date", rs.getString("date"));
				jary.add(jobj);
				flag = false;
				// https://offbyone.tistory.com/373 (�����ϱ� - json������)
				// https://dololak.tistory.com/625
			}
			returns = jary.toJSONString(); // json���·� ����Ʈ ������
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException ex) {	}
			if (conn != null)
				try {
					conn.close();
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
	
	public String imgShow(int num) {
			try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW());
			sql = "select * from board where num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) { // ���ε��� �̹��� ������ ��ΰ� ������ ��
				String filePath = rs.getString("image_path");
				File imgFile = new File(filePath);
				FileInputStream fis = new FileInputStream(imgFile);

				byte[] encoding = new byte[(int) imgFile.length()];
				fis.read(encoding);
				String encoded = Base64.encodeBase64String(encoding);
				fis.close();

				returns = encoded;
			} else { // ���ε��� �̹��� ������ �������� ���� ��
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}
	
}
