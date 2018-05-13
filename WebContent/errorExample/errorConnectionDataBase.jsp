<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 错误演示JSP连接数据库样例 -->
	<%
		List<String>list = new ArrayList<String>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(Exception e){
			System.out.println(e);
		}
		String user = "root";
		String password = "09043330";
		String url = "jdbc:mysql://localhost:3306/JSP";
		
		try{
			Connection conn = DriverManager.getConnection(url,user,password);
			Statement state = conn.createStatement();
			
			ResultSet rs = state.executeQuery("select * from books");
			
			while(rs.next()){
				String bookname = rs.getString("name");
				System.out.println(bookname);
				list.add(bookname);
			}
		}catch(SQLException e){
			System.out.println(e);
		}
		
	%>	
	<ul>
		<% 
			for(String bookName : list){
				%>
					<li>书名是:<%=bookName %></li>
				<%
			}
		%>
	</ul>
	
</body>
</html>