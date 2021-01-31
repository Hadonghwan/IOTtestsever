<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "DBConnect.*"%>
<%
      request.setCharacterEncoding("UTF-8");
      String name = request.getParameter("name");
      String id = request.getParameter("id");
      String pwd = request.getParameter("pwd");
      String mail = request.getParameter("mail");
      String type = request.getParameter("type");   //   사용자가 무슨요청을 했는지 구분하는 변수
      
		User user = User.getInstance();
	if (type.equals("addUser_List")) {
		String returns = user.addUser_List(id); //add-user테이블에 추가된 정보 찾기
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("user_List")) {
		String returns = user.user_List(name, id); //user테이블에 추가된 정보 찾기
		out.clear();
		out.print(returns);
		out.flush();
	}else if (type.equals("user_Delete")) {
			String returns = user.user_Delete(name, id); //삭제할 이름, 추가할 아이디
			out.clear();
			out.print(returns);
			out.flush();
	} else if (type.equals("addUser_Add")) {
		String returns = user.addUser_Add(name, id); //추가할 이름, 추가할 아이디
		out.clear();
		out.print(returns);
		out.flush();
	} else if (type.equals("addUser_Delete")) {
		String returns = user.addUser_Delete(name, id); //삭제할 이름, 삭제할 아이디
		out.clear();
		out.print(returns);
		out.flush();
	} /*
	else if (type.equals("user_Modify")) {	//add_user와 user 수정을 할 때 모두 이 메소드를 이용
		String returns = user.user_Modify(b_name, b_id, name, id, pwd, mail); //수정전 id,name - 수정후id,name,pwd
		out.clear();
		out.print(returns);
		out.flush();
	}*/
	else {
		String returns = "error/nonTypeRequest";
		out.clear();
		out.print(returns);
		out.flush();
	}
%>