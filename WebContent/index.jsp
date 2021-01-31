<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html>
<head>
<meta content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
	<form action ="Board.jsp" enctype = "multipart/form-data" method = "post">
		타입<input type = "text" name = "type">
		아이디<input type = "text" name = "id">
		제목<input type = "text" name = "title">
		내용<input type = "text" name = "contents">
		업로드<input type="file" name="filename1">
		<input type ="submit" value ="게시글 작성">
</form>
<body>
</body>
</html>
