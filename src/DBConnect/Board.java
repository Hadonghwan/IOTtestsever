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

public class Board { // 게시판 관리
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
			
			
			if (rs.next()) { // id가 존재해서 게시글을 쓸 때
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
			 else { // id가 존재하지 않아서 게시글을 쓸 수 없을때
				
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
	
	public String view_Update(int num){ //조회수 업데이트
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

	
	public String board_Delete(String id, int num) { // 게시판 게시글 삭제 - board 테이블
		try {
			
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from board where id=? and num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			if (rs.next()) { // board 테이블에 id와 number가 존재하는지 확인
				sql2 = "delete from board where id=? and num=?"; // board 테이블 레코드 삭제
				pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setString(1, id);
				pstmt2.setInt(2, num);
				pstmt2.executeUpdate(); // db에 쿼리문 입력
				returns = "board_Deleted";
			} else { // board테이블에 number가 존재 하지 않을때
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
			 else { // id가 존재하지 않아서 게시글을 쓸 수 없을때
				
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
			 else { // id가 존재하지 않아서 게시글을 쓸 수 없을때
				
				returns = "board_valueNotExistToUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}
		return returns;		
	}
	public String board_List() { // board 테이블 리스트
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(dbc.getURL(), dbc.getID(), dbc.getPW()); // 데이터베이스 접근을 위한 로그인
			sql = "select * from board order by num asc"; // board테이블로부터 모든 정보를 불러오기
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); // db에 쿼리문 입력

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
				// https://offbyone.tistory.com/373 (참고하기 - json데이터)
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
			rs = pstmt.executeQuery(); // db에 쿼리문 입력
		
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
				// https://offbyone.tistory.com/373 (참고하기 - json데이터)
				// https://dololak.tistory.com/625
			}
			returns = jary.toJSONString(); // json형태로 리스트 보내기
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
			if (rs.next()) { // 업로드한 이미지 파일의 경로가 존재할 때
				String filePath = rs.getString("image_path");
				File imgFile = new File(filePath);
				FileInputStream fis = new FileInputStream(imgFile);

				byte[] encoding = new byte[(int) imgFile.length()];
				fis.read(encoding);
				String encoded = Base64.encodeBase64String(encoding);
				fis.close();

				returns = encoded;
			} else { // 업로드한 이미지 파일이 존재하지 않을 때
				returns = "fileNotExist";
			}

		} catch (Exception e) {
			e.printStackTrace();
			returns = "error";
		}

		return returns;
	}
	
}
