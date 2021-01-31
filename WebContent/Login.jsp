<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="DBConnect.*"%>

<%
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String mail = request.getParameter("mail");
		String type = request.getParameter("type");	//	사용자가 무슨요청을 했는지 구분하는 변수

		Login lin = Login.getInstance();
		
		if(type.equals("adminLogin")) {
  		
			String returns = lin.adminLogin(id);			

			out.clear();
			out.print(returns);
			out.flush();
		}
		else if(type.equals("login")) {
   		
   			
			String returns = lin.loginDB(id, pwd);
	
			out.clear();
			out.print(returns);
			out.flush();			
		}
		else if(type.equals("Join")) {
   		   			
			String returns = lin.createAccount(name, id, pwd, mail);	
			out.clear();
			out.print(returns);
			out.flush();
		}
		else {
			String returns = "error/nonTypeRequest";
			out.clear();
			out.print(returns);
			out.flush();
		}
%>