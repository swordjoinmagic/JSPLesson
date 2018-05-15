<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("utf-8");
		String str = request.getParameter("input");
	%>
	<form action="" method="GET">
		用户名:<input type="text" name="user" />
		密码：<input type="password" name="password" />
		<input type="hidden" name="input" value="登录成功" />
		<input type="submit" value="提交"/>
	</form>
	<!-- 在此处使用java代码直接输出参数，此处是XSS攻击处 -->
	<% 
		if(str!=null){
			out.println(str);
			out.flush();
		}
	%>
</body>
</html>