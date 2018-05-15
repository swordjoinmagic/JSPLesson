package servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ado.CommonDatabase;
import ado.CommonDatabaseWithConnectionPool;
import ado.CommonDatabaseWithSimple;
import ado.CommonDatabaseWithSingle;
import javabean.Book;

@WebServlet("/showBookName")
public class Example1ServeletWithShowBookName extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 传统数据库连接模式
//		CommonDatabaseWithSimple cdws = new CommonDatabaseWithSimple();
		
		// 单例模式连接数据库
//		CommonDatabaseWithSingle cdws = CommonDatabaseWithSingle.getAdo();
		
		// 数据库连接池模式连接数据库
		CommonDatabaseWithConnectionPool cwcp = new CommonDatabaseWithConnectionPool();
		
		List<Book>list = cwcp.commonQuery("select * from bookes", Book.class);
		
		// 第四步,将存放数据的数据结构存入requests中
		req.setAttribute("bookList", list);
		
		// 第五步，寻找对应视图，分发请求
		RequestDispatcher dispatcher = req.getRequestDispatcher("/example/example1ShowBookName.jsp");
		dispatcher.forward(req, resp);
	}
	
}
