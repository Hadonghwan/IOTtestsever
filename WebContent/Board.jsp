 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.oreilly.servlet.MultipartRequest,com.oreilly.servlet.multipart.DefaultFileRenamePolicy,java.util.*,java.io.*" %>
<%@ page import="DBConnect.*"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.util.Properties"%>
<%@ page import="java.sql.*" %>
<%

		request.setCharacterEncoding("UTF-8");
		String realFolder = "";
		String filename1 = "";
		int maxSize = 1024*1024*5;
		String encType = "utf-8";
		String savefile = "img";
		ServletContext scontext = getServletContext();
		realFolder = scontext.getRealPath(savefile);
		MultipartRequest multi=new MultipartRequest(request, realFolder, maxSize, encType, new DefaultFileRenamePolicy());
		String type = multi.getParameter("type");
	    String title = multi.getParameter("title"); 
	    String contents = multi.getParameter("contents");
	    String id = multi.getParameter("id");
		 
		Board bor = Board.getInstance();
		
		if(type.equals("noimage_Add")) {
			String fullpath = multi.getParameter("filename1");
			String returns = bor.board_Add(id, title, contents, fullpath);
			out.print(returns);
			out.flush();
		}	
		else if(type.equals("board_Add")){
			
			try{
				Enumeration<?> files = multi.getFileNames();
				String file1 = (String)files.nextElement();
				filename1 = multi.getFilesystemName(file1);
		
			    String fullpath = realFolder + "\\" + filename1;
			
			String returns = bor.board_Add(id, title, contents, fullpath);
			out.clear();
			out.print(returns);
			out.flush();
			}
				catch(Exception e) {
				 e.printStackTrace();
			}
		}
		else if(type.equals("board_Update")){	
			try{
				   
			    Enumeration<?> files = multi.getFileNames();
			    String file1 = (String)files.nextElement();
			    filename1 = multi.getFilesystemName(file1);
			    String fullpath = realFolder + "\\" + filename1;	
			    
			int num = Integer.parseInt(multi.getParameter("num"));
			String returns = bor.board_Update(id, num, title, contents, fullpath);
			out.print(returns);
			out.flush();
			}
			catch(Exception e) {
			 	e.printStackTrace();
			}
		}
		else if(type.equals("noimage_Update")) {
			String fullpath = multi.getParameter("filename1");
			int num = Integer.parseInt(multi.getParameter("num"));
			String returns = bor.noimage_Update(id, num, title, contents);
			out.print(returns);
			out.flush();
		}	
		else if(type.equals("board_Delete")) {

			int num = Integer.parseInt(multi.getParameter("num"));
			String returns = bor.board_Delete(id, num);
			out.print(returns);
			out.flush();
		}
		else if(type.equals("view_Update")){
			
			int num = Integer.parseInt(multi.getParameter("num"));
	         String returns = bor.view_Update(num);
	         out.print(returns);
	         out.flush();
		}
		else if(type.equals("imgShow")){
			
			int num = Integer.parseInt(multi.getParameter("num"));
	         String returns = bor.imgShow(num);
	         out.print(returns);
	         out.flush();
		}
		else if(type.equals("board_List")){
			
	         String returns = bor.board_List();
	         out.print(returns);
	         out.flush();
		}	
		else if(type.equals("board_View")){
	
			int num = Integer.parseInt(multi.getParameter("num"));
     		String returns = bor.board_View(num);
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