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
import ado.CommonDatabaseWithSimple;
import javabean.Book;

@WebServlet("/showBookName")
public class Example1ServeletWithShowBookName extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CommonDatabaseWithSimple cdws = new CommonDatabaseWithSimple();
		
		List<Book>list = cdws.getBookList();
		
		// ���Ĳ�,��������ݵ����ݽṹ����requests��
		req.setAttribute("bookList", list);
		
		// ���岽��Ѱ�Ҷ�Ӧ��ͼ���ַ�����
		RequestDispatcher dispatcher = req.getRequestDispatcher("/example/example1ShowBookName.jsp");
		dispatcher.forward(req, resp);
	}
	
}
