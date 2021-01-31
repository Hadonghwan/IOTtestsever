 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.MultipartRequest,com.oreilly.servlet.multipart.DefaultFileRenamePolicy,java.util.*,java.io.*" %>
<%@ page import="DBConnect.*"%>
<%@ page import="java.sql.*" %>
<%
String type = request.getParameter("type");
request.setCharacterEncoding("EUC-KR");

String realFolder = "";
String filename1 = "";
int maxSize = 1024*1024*5;
String encType = "utf-8";
String savefile = "img";
ServletContext scontext = getServletContext();
realFolder = scontext.getRealPath(savefile);

try{
 MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());

 Enumeration<?> files = multi.getFileNames();
    String file1 = (String)files.nextElement();
    filename1 = multi.getFilesystemName(file1);
} catch(Exception e) {
 e.printStackTrace();
}

String fullpath = realFolder + "\\" + filename1;
Imgup img = Imgup.getInstance();

	
	String returns = img.filePath_Download(fullpath);
	out.clear();
	out.print(returns);
	out.flush();

 
%>

<title>Insert title here</title>
</head>
<body>

</body>
</html>
