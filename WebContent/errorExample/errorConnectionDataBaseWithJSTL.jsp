<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 错误例子演示之--JSTL连接数据库 -->
	<!-- 使用JSTL设置数据源 -->
	<sql:setDataSource 
		var="databaseSource" 
		driver="com.mysql.jdbc.Driver" 
		url="jdbc:mysql://localhost:3306/JSP"
		user="root"
		password="09043330"
	/>
	<sql:query dataSource="${databaseSource}" var="result">
		select * from books;
	</sql:query>
	
	<ul>
		<c:forEach var="data" items="${result.rows}">
			<li>书名是:${data.name}</li>
		</c:forEach>
	</ul>
</body>
</html>